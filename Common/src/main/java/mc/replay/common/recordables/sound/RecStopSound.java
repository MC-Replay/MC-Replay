package mc.replay.common.recordables.sound;

import mc.replay.common.recordables.RecordableSound;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.ClientboundStopSoundPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecStopSound(byte flags, int sourceId, String sound) implements RecordableSound {

    public static RecStopSound of(byte flags, int sourceId, String sound) {
        return new RecStopSound(
                flags,
                sourceId,
                sound
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
}