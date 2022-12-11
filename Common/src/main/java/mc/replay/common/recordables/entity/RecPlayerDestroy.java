package mc.replay.common.recordables.entity;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableOther;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecPlayerDestroy(EntityId entityId) implements RecordableOther {

    public static RecPlayerDestroy of(EntityId entityId) {
        return new RecPlayerDestroy(
                entityId
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of();
    }
}