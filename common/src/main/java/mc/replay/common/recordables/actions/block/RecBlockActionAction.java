package mc.replay.common.recordables.actions.block;

import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.block.RecBlockAction;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockActionPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record RecBlockActionAction() implements EmptyRecordableAction<RecBlockAction> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecBlockAction recordable, @NotNull Void data) {
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