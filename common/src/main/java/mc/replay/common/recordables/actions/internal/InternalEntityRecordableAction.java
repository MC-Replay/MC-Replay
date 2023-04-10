package mc.replay.common.recordables.actions.internal;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.action.RecordableAction;
import mc.replay.common.replay.IReplayEntityProcessor;

@FunctionalInterface
public interface InternalEntityRecordableAction<R extends Recordable> extends RecordableAction<R, IReplayEntityProcessor> {
}