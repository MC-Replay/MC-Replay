package mc.replay.common.recordables.actions.internal;

import mc.replay.api.recordables.action.RecordableAction;
import mc.replay.common.recordables.types.internal.BlockRelatedRecordable;
import mc.replay.common.replay.IReplayBlockProvider;

@FunctionalInterface
public interface InternalBlockRecordableAction<R extends BlockRelatedRecordable> extends RecordableAction<R, IReplayBlockProvider> {
}