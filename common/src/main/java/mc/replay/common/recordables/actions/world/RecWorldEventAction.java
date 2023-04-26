package mc.replay.common.recordables.actions.world;

import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.world.RecWorldEvent;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundWorldEventPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record RecWorldEventAction() implements EmptyRecordableAction<RecWorldEvent> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecWorldEvent recordable, @NotNull Void data) {
        return List.of(
                new ClientboundWorldEventPacket(
                        recordable.effectId(),
                        recordable.position(),
                        recordable.data(),
                        recordable.disableRelativeVolume()
                )
        );
    }
}