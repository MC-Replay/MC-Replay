package mc.replay.nms.global.recordable;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableOther;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntityDestroy(EntityId entityId) implements RecordableOther {

    public static RecEntityDestroy of(EntityId entityId) {
        return new RecEntityDestroy(
                entityId
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(Function<Void, Void> function) {
        return List.of();
    }
}