package mc.replay.common.recordables.actions.block;

import mc.replay.common.recordables.actions.internal.InternalBlockRecordableAction;
import mc.replay.common.recordables.types.block.RecAcknowledgePlayerDigging;
import mc.replay.common.replay.IReplayBlockProvider;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.legacy.ClientboundAcknowledgePlayerDigging754_758Packet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public final class RecAcknowledgePlayerDiggingAction implements InternalBlockRecordableAction<RecAcknowledgePlayerDigging> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecAcknowledgePlayerDigging recordable, @UnknownNullability IReplayBlockProvider data) {
        return List.of(
                new ClientboundAcknowledgePlayerDigging754_758Packet(
                        recordable.blockPosition(),
                        recordable.blockStateId(),
                        recordable.stateId(),
                        recordable.successful()
                )
        );
    }
}