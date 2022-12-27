package mc.replay.common.recordables.sound;

import mc.replay.common.recordables.RecordableSound;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntitySoundEffectPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntitySound(int soundId, int sourceId, int entityId, float volume,
                             float pitch) implements RecordableSound {

    public static RecEntitySound of(int soundId, int sourceId, int entityId, float volume, float pitch) {
        return new RecEntitySound(
                soundId,
                sourceId,
                entityId,
                volume,
                pitch
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
}