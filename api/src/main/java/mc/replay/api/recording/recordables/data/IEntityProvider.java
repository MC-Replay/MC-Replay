package mc.replay.api.recording.recordables.data;

import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import org.jetbrains.annotations.Nullable;

public interface IEntityProvider {

    @Nullable RecordableEntityData getEntity(int originalEntityId);
}