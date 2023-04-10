package mc.replay.common.recordables.actions.sound;

import mc.replay.api.recording.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.sound.RecCustomSoundEffect;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundCustomSoundEffectPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record RecCustomSoundEffectAction() implements EmptyRecordableAction<RecCustomSoundEffect> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecCustomSoundEffect recordable, @NotNull Void data) {
        return List.of(
                new ClientboundCustomSoundEffectPacket(
                        recordable.soundName(),
                        recordable.sourceId(),
                        recordable.x(),
                        recordable.y(),
                        recordable.z(),
                        recordable.volume(),
                        recordable.pitch(),
                        recordable.seed()
                )
        );
    }
}