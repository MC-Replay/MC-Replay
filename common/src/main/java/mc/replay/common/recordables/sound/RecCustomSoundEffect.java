package mc.replay.common.recordables.sound;

import mc.replay.common.recordables.interfaces.RecordableSound;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundCustomSoundEffectPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecCustomSoundEffect(String soundName, int sourceId, int x, int y, int z, float volume,
                                   float pitch, long seed) implements RecordableSound {

    public RecCustomSoundEffect(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(STRING),
                reader.read(INT),
                reader.read(INT),
                reader.read(INT),
                reader.read(INT),
                reader.read(FLOAT),
                reader.read(FLOAT),
                reader.read(LONG)
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

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(STRING, this.soundName);
        writer.write(INT, this.sourceId);
        writer.write(INT, this.x);
        writer.write(INT, this.y);
        writer.write(INT, this.z);
        writer.write(FLOAT, this.volume);
        writer.write(FLOAT, this.pitch);
        writer.write(LONG, this.seed);
    }
}