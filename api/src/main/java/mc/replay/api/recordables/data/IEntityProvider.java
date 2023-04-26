package mc.replay.api.recordables.data;

import org.jetbrains.annotations.Nullable;

public interface IEntityProvider {

    @Nullable RecordableEntityData getEntity(int originalEntityId);
}