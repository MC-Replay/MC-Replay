package mc.replay.api.recording.recordables.data;

import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.packetlib.data.Pos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IEntityProvider {

    @Nullable RecordableEntityData getEntity(int originalEntityId);

    void moveEntity(int originalEntityId, @NotNull Pos deltaPosition);

    void teleportEntity(int originalEntityId, @NotNull Pos position);

    void rotateEntity(int originalEntityId, float yaw, float pitch);
}