package mc.replay.common.recordables.actions.block;

import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.block.RecBlockEntityData;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockEntityDataPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record RecBlockEntityDataAction() implements EmptyRecordableAction<RecBlockEntityData> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecBlockEntityData recordable, @NotNull Void data) {
        return List.of(
                new ClientboundBlockEntityDataPacket(
                        recordable.blockPosition(),
                        recordable.action(),
                        recordable.data()
                )
        );
    }
}