package mc.replay.api.recordables.action;

import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.Recordable;

@FunctionalInterface
public interface EntityRecordableAction<R extends Recordable> extends RecordableAction<R, IEntityProvider> {
}