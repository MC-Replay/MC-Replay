package mc.replay.common.recordables.actions.block;

import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.block.RecAcknowledgePlayerDigging;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.legacy.ClientboundAcknowledgePlayerDigging754_758Packet;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class RecAcknowledgePlayerDiggingAction implements EmptyRecordableAction<RecAcknowledgePlayerDigging> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecAcknowledgePlayerDigging recordable, @NotNull Void data) {
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