package mc.replay.common.recordables.actions.sound;

import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.sound.RecSoundEffect;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundSoundEffectPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public record RecSoundEffectAction() implements EmptyRecordableAction<RecSoundEffect> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecSoundEffect recordable, @NotNull Void data) {
        return List.of(
                new ClientboundSoundEffectPacket(
                        recordable.soundId(),
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

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPacketsForwards(@NotNull RecSoundEffect recordable, @UnknownNullability Void data) {
        return List.of();
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPacketsBackwards(@NotNull RecSoundEffect recordable, @UnknownNullability Void data) {
        return List.of();
    }
}