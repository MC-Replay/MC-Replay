package mc.replay.common.dispatcher.tick;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherTick;
import mc.replay.common.recordables.entity.movement.RecEntityHeadRotation;
import mc.replay.common.recordables.entity.movement.RecEntityPositionAndRotation;
import mc.replay.common.recordables.entity.movement.RecEntityTeleport;
import mc.replay.common.utils.FakePlayerUUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class EntityLocationTickHandler implements DispatcherTick {

    private final Map<LivingEntity, Location> lastLocations = new HashMap<>();

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(Integer currentTick) {
        List<Recordable<? extends Function<?, ?>>> recordables = new ArrayList<>();

        this.lastLocations.entrySet().removeIf((entry) -> entry.getKey() == null || entry.getKey().isDead());

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof LivingEntity livingEntity) || !world.isChunkLoaded(entity.getLocation().getChunk()))
                    continue;

                if (FakePlayerUUID.UUIDS.contains(entity.getUniqueId())) continue;

                Location currentLocation = livingEntity.getLocation();
                Location lastLocation = this.lastLocations.put(livingEntity, currentLocation);
                if (lastLocation == null) lastLocation = currentLocation;

                EntityId entityId = EntityId.of(livingEntity.getUniqueId(), livingEntity.getEntityId());

                if (!lastLocation.equals(currentLocation)) {
                    if (lastLocation.distanceSquared(currentLocation) > 64) {
                        recordables.add(RecEntityTeleport.of(entityId, currentLocation, livingEntity.isOnGround()));
                    } else {
                        recordables.add(RecEntityPositionAndRotation.of(entityId, lastLocation, currentLocation, livingEntity.isOnGround()));
                    }
                }

                if (lastLocation.getYaw() != currentLocation.getYaw()) {
                    recordables.add(RecEntityHeadRotation.of(entityId, currentLocation.getYaw()));
                }
            }
        }

        return recordables;
    }
}