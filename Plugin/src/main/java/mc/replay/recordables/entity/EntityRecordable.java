package mc.replay.recordables.entity;

import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.common.replay.ReplayEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface EntityRecordable extends Recordable {

    EntityId entityId();

    ReplayEntity<?> play(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId);

    ReplayEntity<?> jumpInTime(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId, boolean forward);

    @Override
    default boolean match(@NotNull Object object) {
        if (object instanceof Integer entityId) {
            return this.entityId().entityId() == entityId;
        } else if (object instanceof UUID entityUuid) {
            return entityUuid.equals(this.entityId().entityUuid());
        }

        return false;
    }
}