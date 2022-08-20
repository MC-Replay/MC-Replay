package mc.replay.nms.v1_16_5.dispatcher.tick;

import mc.replay.common.dispatcher.DispatcherTick;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.nms.v1_16_5.recordable.entity.movement.RecEntityHeadRotation;
import mc.replay.nms.v1_16_5.recordable.entity.movement.RecEntityRelMoveLook;
import mc.replay.nms.v1_16_5.recordable.entity.movement.RecEntityTeleport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityLocationTickHandler implements DispatcherTick {

    private final Map<LivingEntity, Location> lastLocations = new HashMap<>();

    @Override
    public List<Recordable> getRecordable(Object tickClass) {
        Integer currentTick = (Integer) tickClass;
        List<Recordable> recordables = new ArrayList<>();

        this.lastLocations.entrySet().removeIf((entry) -> entry.getKey() == null || entry.getKey().isDead());

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof LivingEntity livingEntity) || !world.isChunkLoaded(entity.getLocation().getChunk()))
                    continue;

                Location currentLocation = livingEntity.getLocation();
                Location lastLocation = this.lastLocations.put(livingEntity, currentLocation);
                if (lastLocation == null) lastLocation = currentLocation;

                EntityId entityId = EntityId.of(livingEntity.getUniqueId(), livingEntity.getEntityId());

                if (!lastLocation.equals(currentLocation)) {
                    if (lastLocation.distanceSquared(currentLocation) > 64) {
                        recordables.add(RecEntityTeleport.of(entityId, currentLocation, livingEntity.isOnGround()));
                    } else {
                        recordables.add(RecEntityRelMoveLook.of(entityId, lastLocation, currentLocation, livingEntity.isOnGround()));
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