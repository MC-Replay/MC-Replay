package mc.replay.common.recordables.actions.block;

import mc.replay.api.recording.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.block.RecBlockBreakStage;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockBreakAnimationPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record RecBlockBreakStageAction() implements EmptyRecordableAction<RecBlockBreakStage> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecBlockBreakStage recordable, @NotNull Void data) {
        return List.of(
                new ClientboundBlockBreakAnimationPacket(
                        -1,
                        recordable.blockPosition(),
                        recordable.stage()
                )
        );
    }
}