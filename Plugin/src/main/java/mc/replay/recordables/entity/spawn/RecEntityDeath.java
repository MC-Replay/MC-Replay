package mc.replay.recordables.entity.spawn;

import mc.replay.common.replay.EntityId;
import mc.replay.recordables.entity.EntityRecordable;
import mc.replay.common.replay.ReplayEntity;
import mc.replay.common.replay.ReplayMob;
import org.bukkit.entity.Player;

public record RecEntityDeath(EntityId entityId) implements EntityRecordable {

    public static RecEntityDeath of(EntityId entityId) {
        return new RecEntityDeath(
                entityId
        );
    }

    @Override
    public ReplayEntity<ReplayMob> play(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId) {
        replayEntity.destroy();
        return null;
    }

    @Override
    public ReplayEntity<ReplayMob> jumpInTime(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId, boolean forward) {
        if (forward) {
            return this.play(viewer, replayEntity, entity, entityId);
        } else {
            // TODO create entity
            return null;
        }
    }
}