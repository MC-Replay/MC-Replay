package mc.replay.recordables.entity.connection;

import mc.replay.common.replay.EntityId;
import mc.replay.recordables.entity.EntityRecordable;
import mc.replay.common.replay.ReplayEntity;
import mc.replay.common.replay.ReplayNPC;
import org.bukkit.entity.Player;

public record RecPlayerQuit(EntityId entityId) implements EntityRecordable {

    public static RecPlayerQuit of(EntityId entityId) {
        return new RecPlayerQuit(
                entityId
        );
    }

    @Override
    public ReplayEntity<ReplayNPC> play(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId) {
        replayEntity.destroy();
        return null;
    }

    @Override
    public ReplayEntity<ReplayNPC> jumpInTime(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId, boolean forward) {
        if (forward) {
            return this.play(viewer, replayEntity, entity, entityId);
        } else {
            // TODO create npc
            return null;
        }
    }
}