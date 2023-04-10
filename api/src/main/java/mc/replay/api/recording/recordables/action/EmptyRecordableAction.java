package mc.replay.api.recording.recordables.action;

import mc.replay.api.recording.recordables.Recordable;

public interface EmptyRecordableAction<R extends Recordable> extends RecordableAction<R, Void> {
}