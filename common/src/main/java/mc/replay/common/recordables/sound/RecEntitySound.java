package mc.replay.common.recordables.sound;

import mc.replay.common.recordables.interfaces.RecordableSound;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntitySoundEffectPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.FLOAT;
import static mc.replay.packetlib.network.ReplayByteBuffer.INT;

public record RecEntitySound(int soundId, int sourceId, int entityId, float volume,
                             float pitch) implements RecordableSound {

    public RecEntitySound(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(INT),
                reader.read(INT),
                reader.read(INT),
                reader.read(FLOAT),
                reader.read(FLOAT)
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new ClientboundEntitySoundEffectPacket(
                this.soundId,
                this.sourceId,
                this.entityId,
                this.volume,
                this.pitch
        ));
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(INT, this.soundId);
        writer.write(INT, this.sourceId);
        writer.write(INT, this.entityId);
        writer.write(FLOAT, this.volume);
        writer.write(FLOAT, this.pitch);
    }
}