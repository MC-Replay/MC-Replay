package mc.replay.common.recordables;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface RecordablePlayerAction extends Recordable {

    EntityId entityId();

    void play(Player viewer);

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