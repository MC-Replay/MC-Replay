package mc.replay.common.dispatcher.tick;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.api.utils.FakePlayerUUID;
import mc.replay.common.dispatcher.DispatcherTick;
import mc.replay.common.recordables.types.entity.movement.RecEntityHeadRotation;
import mc.replay.common.recordables.types.entity.movement.RecEntityPositionAndRotation;
import mc.replay.common.recordables.types.entity.movement.RecEntityTeleport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityLocationTickHandler implements DispatcherTick {

    private final Map<LivingEntity, Location> lastLocations = new HashMap<>();

    @Override
    public List<Recordable> getRecordables(Integer currentTick) {
        List<Recordable> recordables = new ArrayList<>();

        this.lastLocations.entrySet().removeIf((entry) -> entry.getKey() == null || entry.getKey().isDead());

        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity livingEntity : world.getLivingEntities()) {
                if (!world.isChunkLoaded(livingEntity.getLocation().getChunk()))
                    continue;

                if (FakePlayerUUID.UUIDS.contains(livingEntity.getUniqueId())) continue;

                Location currentLocation = livingEntity.getLocation();
                Location lastLocation = this.lastLocations.put(livingEntity, currentLocation);
                if (lastLocation == null) lastLocation = currentLocation;

                EntityId entityId = EntityId.of(livingEntity.getUniqueId(), livingEntity.getEntityId());

                if (!lastLocation.equals(currentLocation)) {
                    if (lastLocation.distanceSquared(currentLocation) > 64) {
                        recordables.add(new RecEntityTeleport(entityId, currentLocation, livingEntity.isOnGround()));
                    } else {
                        recordables.add(new RecEntityPositionAndRotation(entityId, currentLocation, lastLocation, livingEntity.isOnGround()));
                    }
                }

                if (lastLocation.getYaw() != currentLocation.getYaw()) {
                    recordables.add(new RecEntityHeadRotation(entityId, currentLocation.getYaw()));
                }
            }
        }

        return recordables;
    }
}