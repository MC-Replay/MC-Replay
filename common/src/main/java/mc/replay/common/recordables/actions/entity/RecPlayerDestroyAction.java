package mc.replay.common.recordables.actions.entity;

import mc.replay.common.recordables.actions.internal.InternalEntityRecordableAction;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.common.replay.IReplayEntityProvider;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public final class RecPlayerDestroyAction implements InternalEntityRecordableAction<RecPlayerDestroy> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecPlayerDestroy recordable, @UnknownNullability IReplayEntityProvider provider) {
        provider.destroyPlayer(recordable);
        return List.of();
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPacketsForwards(@NotNull RecPlayerDestroy recordable, @UnknownNullability IReplayEntityProvider provider) {
        return List.of();
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPacketsBackwards(@NotNull RecPlayerDestroy recordable, @UnknownNullability IReplayEntityProvider provider) {
        return List.of();
    }
}