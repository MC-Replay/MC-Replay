package mc.replay.common.recordables.sound;

import mc.replay.common.recordables.interfaces.RecordableSound;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundStopSoundPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecStopSound(byte flags, int sourceId, String sound) implements RecordableSound {

    public RecStopSound(@NotNull ReplayByteBuffer reader) {
        this(
                reader.read(BYTE),
                reader.read(INT),
                reader.read(STRING)
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new ClientboundStopSoundPacket(
                this.flags,
                this.sourceId,
                this.sound
        ));
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(BYTE, this.flags);
        writer.write(INT, this.sourceId);
        writer.write(STRING, this.sound);
    }
}