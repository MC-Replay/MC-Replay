package mc.replay.recordables.entity.spawn;

import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.EntityRecordable;
import mc.replay.common.replay.ReplayEntity;
import mc.replay.common.replay.ReplayMob;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public record RecEntitySpawn(EntityId entityId, EntityType entityType, Location location) implements EntityRecordable {

    public static RecEntitySpawn of(EntityId entityId, EntityType entityType, Location location) {
        return new RecEntitySpawn(
                entityId,
                entityType,
                location
        );
    }

    @Override
    public ReplayEntity<ReplayMob> play(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId) {
        return new ReplayMob(
                this.entityId.entityId(),
                new ArrayList<>(),
                this.entityType,
                this.location
        );
    }

    @Override
    public ReplayEntity<ReplayMob> jumpInTime(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId, boolean forward) {
        if (forward) {
            return this.play(viewer, replayEntity, entity, entityId);
        } else {
            replayEntity.destroy();
            return null;
        }
    }
}