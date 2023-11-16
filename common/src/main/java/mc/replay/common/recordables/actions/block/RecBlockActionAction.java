package mc.replay.common.recordables.actions.block;

import mc.replay.common.recordables.actions.internal.InternalBlockRecordableAction;
import mc.replay.common.recordables.types.block.RecBlockAction;
import mc.replay.common.replay.IReplayBlockProvider;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockActionPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public final class RecBlockActionAction implements InternalBlockRecordableAction<RecBlockAction> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecBlockAction recordable, @UnknownNullability IReplayBlockProvider data) {
        return List.of(
                new ClientboundBlockActionPacket(
                        recordable.blockPosition(),
                        recordable.actionId(),
                        recordable.actionParam(),
                        recordable.blockId()
                )
        );
    }
}