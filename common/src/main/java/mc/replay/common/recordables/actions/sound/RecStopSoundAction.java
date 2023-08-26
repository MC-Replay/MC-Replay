package mc.replay.common.recordables.actions.sound;

import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.sound.RecStopSound;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundStopSoundPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public final class RecStopSoundAction implements EmptyRecordableAction<RecStopSound> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecStopSound recordable, @NotNull Void data) {
        return List.of(
                new ClientboundStopSoundPacket(
                        recordable.flags(),
                        recordable.sourceId(),
                        recordable.sound()
                )
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPacketsTimeJump(@NotNull RecStopSound recordable, @UnknownNullability Void data) {
        return List.of();
    }
}