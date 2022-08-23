package mc.replay.common.recordables;

import mc.replay.api.recording.recordables.Recordable;

import java.util.function.Function;

public interface RecordableEntity extends Recordable<Function<Integer, RecordableEntity.RecordableEntityData>> {

    record RecordableEntityData(int entityId, Object entityPlayer) {
    }
}