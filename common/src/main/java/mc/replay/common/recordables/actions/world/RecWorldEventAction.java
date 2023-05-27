package mc.replay.common.recordables.actions.world;

import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.world.RecWorldEvent;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundWorldEventPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public final class RecWorldEventAction implements EmptyRecordableAction<RecWorldEvent> {

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

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPacketsForwards(@NotNull RecWorldEvent recordable, @UnknownNullability Void data) {
        return List.of();
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPacketsBackwards(@NotNull RecWorldEvent recordable, @UnknownNullability Void data) {
        return List.of();
    }
}