package mc.replay.common.recordables.types.internal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import org.jetbrains.annotations.NotNull;

public interface EntityStateRecordable extends Recordable {

    @NotNull EntityId entityId();
}