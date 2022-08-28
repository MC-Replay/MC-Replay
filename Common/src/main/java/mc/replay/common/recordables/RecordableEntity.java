package mc.replay.common.recordables;

import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface RecordableEntity extends Recordable<Function<Integer, RecordableEntity.RecordableEntityData>> {

    @NotNull EntityId entityId();

    @Override
    @NotNull
    default Function<@NotNull RecordingSession, @NotNull Boolean> shouldRecord() {
        return (session) -> session.isInsideRecordingRange(this.entityId().entityId());
    }

    record RecordableEntityData(int entityId, Object entityPlayer) {
    }
}