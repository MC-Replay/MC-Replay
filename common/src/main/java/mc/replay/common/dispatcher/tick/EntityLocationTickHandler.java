package mc.replay.common.dispatcher.tick;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherTick;
import mc.replay.common.recordables.types.entity.movement.RecEntityHeadRotation;
import mc.replay.common.recordables.types.entity.movement.RecEntityPositionAndRotation;
import mc.replay.common.recordables.types.entity.movement.RecEntityTeleport;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityLocationTickHandler implements DispatcherTick {

    private final Map<LivingEntity, Location> lastLocations = new HashMap<>();
    private final Map<LivingEntity, Integer> positionAndRotationAmounts = new HashMap<>();

    @Override
    public void onTickGlobal(Integer currentTick) {
        this.lastLocations.entrySet().removeIf((entry) -> entry.getKey() == null || entry.getKey().isDead());
        this.positionAndRotationAmounts.entrySet().removeIf((entry) -> entry.getKey() == null || entry.getKey().isDead());
    }

    @Override
    public List<Recordable> getRecordables(int currentTick, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return null;

        List<Recordable> recordables = new ArrayList<>();

        Location currentLocation = livingEntity.getLocation();
        Location lastLocation = this.lastLocations.put(livingEntity, currentLocation);
        if (lastLocation == null) lastLocation = currentLocation;

        EntityId entityId = EntityId.of(livingEntity.getUniqueId(), livingEntity.getEntityId());

        if (!lastLocation.equals(currentLocation)) {
            if (lastLocation.distanceSquared(currentLocation) > 64) {
                recordables.add(new RecEntityTeleport(entityId, currentLocation, livingEntity.isOnGround()));
            } else {
                int amount = this.positionAndRotationAmounts.getOrDefault(livingEntity, 0);
                if (amount >= 20) {
                    recordables.add(new RecEntityTeleport(entityId, currentLocation, livingEntity.isOnGround()));
                    this.positionAndRotationAmounts.put(livingEntity, 0);
                } else {
                    recordables.add(new RecEntityPositionAndRotation(entityId, currentLocation, lastLocation, livingEntity.isOnGround()));
                    this.positionAndRotationAmounts.put(livingEntity, amount + 1);
                }
            }
        }

        if (lastLocation.getYaw() != currentLocation.getYaw()) {
            recordables.add(new RecEntityHeadRotation(entityId, currentLocation.getYaw()));
        }

        return recordables;
    }
}