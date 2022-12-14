package mc.replay.dispatcher.tick.handlers;

import mc.replay.MCReplay;
import mc.replay.dispatcher.tick.ReplayTickHandler;
import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.movement.RecEntityHeadRotation;
import mc.replay.recordables.entity.movement.RecEntityRelMoveLook;
import mc.replay.recordables.entity.movement.RecEntityTeleport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public final class EntityLocationTickHandler implements ReplayTickHandler {

    private final Map<LivingEntity, Location> lastLocations = new HashMap<>();

    @Override
    public void handle(int currentTick) {
        this.lastLocations.entrySet().removeIf((entry) -> entry.getKey() == null || entry.getKey().isDead());

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof LivingEntity livingEntity) || !world.isChunkLoaded(entity.getLocation().getChunk())) continue;

                Location currentLocation = livingEntity.getLocation();
                Location lastLocation = this.lastLocations.put(livingEntity, currentLocation);
                if (lastLocation == null) lastLocation = currentLocation;

                EntityId entityId = EntityId.of(livingEntity.getUniqueId(), livingEntity.getEntityId());

                if (!lastLocation.equals(currentLocation)) {
                    if (lastLocation.distanceSquared(currentLocation) > 64) {
                        RecEntityTeleport recEntityTeleport = RecEntityTeleport.of(entityId, currentLocation, livingEntity.isOnGround());
                        MCReplay.getInstance().getReplayStorage().addRecordable(currentTick, recEntityTeleport);
                    } else {
                        RecEntityRelMoveLook recEntityRelMoveLook = RecEntityRelMoveLook.of(entityId, lastLocation, currentLocation, livingEntity.isOnGround());
                        MCReplay.getInstance().getReplayStorage().addRecordable(currentTick, recEntityRelMoveLook);
                    }
                }

                if (lastLocation.getYaw() != currentLocation.getYaw()) {
                    RecEntityHeadRotation recEntityHeadRotation = RecEntityHeadRotation.of(entityId, currentLocation.getYaw());
                    MCReplay.getInstance().getReplayStorage().addRecordable(currentTick, recEntityHeadRotation);
                }
            }
        }
    }
}