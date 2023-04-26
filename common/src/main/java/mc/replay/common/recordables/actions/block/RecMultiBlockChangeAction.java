package mc.replay.common.recordables.actions.block;

import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.block.RecMultiBlockChange;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundMultiBlockChangePacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record RecMultiBlockChangeAction() implements EmptyRecordableAction<RecMultiBlockChange> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecMultiBlockChange recordable, @NotNull Void data) {
        return List.of(
                new ClientboundMultiBlockChangePacket(
                        recordable.chunkSectorPosition(),
                        recordable.suppressLightUpdates(),
                        recordable.blocks()
                )
        );
    }
}