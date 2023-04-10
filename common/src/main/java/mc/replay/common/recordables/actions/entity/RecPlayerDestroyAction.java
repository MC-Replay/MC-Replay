package mc.replay.common.recordables.actions.entity;

import mc.replay.common.recordables.actions.internal.InternalEntityRecordableAction;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.common.replay.IReplayEntityProcessor;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public record RecPlayerDestroyAction() implements InternalEntityRecordableAction<RecPlayerDestroy> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecPlayerDestroy recordable, @UnknownNullability IReplayEntityProcessor data) {
        data.destroyPlayer(recordable);
        return List.of();
    }
}