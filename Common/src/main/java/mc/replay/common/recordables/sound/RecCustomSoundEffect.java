package mc.replay.common.recordables.sound;

import mc.replay.common.recordables.RecordableSound;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.ClientboundCustomSoundEffectPacket;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecCustomSoundEffect(String soundName, int sourceId, int x, int y, int z, float volume,
                                   float pitch, long seed) implements RecordableSound {

    public static RecCustomSoundEffect of(String soundName, int sourceId, int x, int y, int z, float volume,
                                          float pitch, long seed) {
        return new RecCustomSoundEffect(
                soundName,
                sourceId,
                x,
                y,
                z,
                volume,
                pitch,
                seed
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new ClientboundCustomSoundEffectPacket(
                this.soundName,
                this.sourceId,
                this.x,
                this.y,
                this.z,
                this.volume,
                this.pitch,
                this.seed
        ));
    }
}