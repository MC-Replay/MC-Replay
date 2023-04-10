package mc.replay.common.recordables.actions.sound;

import mc.replay.api.recording.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.sound.RecStopSound;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundStopSoundPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record RecStopSoundAction() implements EmptyRecordableAction<RecStopSound> {

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
}