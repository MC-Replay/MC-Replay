package mc.replay.common.recordables.actions.block;

import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.block.RecBlockChange;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockChangePacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class RecBlockChangeAction implements EmptyRecordableAction<RecBlockChange> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecBlockChange recordable, @NotNull Void data) {
        return List.of(
                new ClientboundBlockChangePacket(
                        recordable.blockPosition(),
                        recordable.blockStateId()
                )
        );
    }
}