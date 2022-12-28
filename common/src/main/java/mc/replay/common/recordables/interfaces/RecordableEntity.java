package mc.replay.common.recordables.interfaces;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.wrapper.entity.EntityWrapper;

import java.util.function.Function;

public interface RecordableEntity extends Recordable<Function<Integer, RecordableEntity.RecordableEntityData>> {

    record RecordableEntityData(int entityId, EntityWrapper entity) {
    }
}