package mc.replay.recordables.entity;

import mc.replay.api.recordable.Recordable;
import mc.replay.replay.entity.ReplayEntity;
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