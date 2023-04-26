package mc.replay.common.recordables.actions.internal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.action.RecordableAction;
import mc.replay.common.replay.IReplayEntityProvider;

@FunctionalInterface
public interface InternalEntityRecordableAction<R extends Recordable> extends RecordableAction<R, IReplayEntityProvider> {
}