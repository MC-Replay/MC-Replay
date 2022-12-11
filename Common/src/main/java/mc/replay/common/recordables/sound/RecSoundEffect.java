package mc.replay.common.recordables.sound;

import mc.replay.common.recordables.RecordableSound;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.ClientboundSoundEffectPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecSoundEffect(int soundId, int sourceId, int x, int y, int z, float volume,
                             float pitch, long seed) implements RecordableSound {

    public static RecSoundEffect of(int soundId, int sourceId, int x, int y, int z, float volume, float pitch, long seed) {
        return new RecSoundEffect(
                soundId,
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
        return List.of(new ClientboundSoundEffectPacket(
                this.soundId,
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