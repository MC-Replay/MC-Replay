package mc.replay.api.recordables.action;

import mc.replay.api.recordables.Recordable;

public interface EmptyRecordableAction<R extends Recordable> extends RecordableAction<R, Void> {
}