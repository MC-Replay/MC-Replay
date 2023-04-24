package mc.replay.api.recording.recordables.action;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.data.IEntityProvider;

@FunctionalInterface
public interface EntityRecordableAction<R extends Recordable> extends RecordableAction<R, IEntityProvider> {
}