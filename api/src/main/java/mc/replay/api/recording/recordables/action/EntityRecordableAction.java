package mc.replay.api.recording.recordables.action;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;

import java.util.function.Function;

@FunctionalInterface
public interface EntityRecordableAction<R extends Recordable> extends RecordableAction<R, Function<Integer, RecordableEntityData>> {
}