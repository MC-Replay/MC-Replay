package mc.replay.nms.v1_16_R3.recordable.sound;

import mc.replay.api.recording.RecordingSession;
import mc.replay.common.recordables.RecordableSound;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayOutCustomSoundEffect;
import net.minecraft.server.v1_16_R3.SoundCategory;
import net.minecraft.server.v1_16_R3.Vec3D;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecCustomSoundEffect(NamespacedKey soundKey, String category, int x, int y, int z, float volume,
                                   float pitch) implements RecordableSound {

    public static RecCustomSoundEffect of(NamespacedKey soundKey, String category, int x, int y, int z, float volume, float pitch) {
        return new RecCustomSoundEffect(
                soundKey,
                category,
                x,
                y,
                z,
                volume,
                pitch
        );
    }

    @Override
    public @NotNull Function<@NotNull RecordingSession, @NotNull Boolean> shouldRecord() {
        return (session) -> session.isInsideRecordingRange(this.x, this.y, this.z);
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Void, Void> function) {
        MinecraftKey effectKey = new MinecraftKey(this.soundKey.getNamespace(), this.soundKey.getKey());
        SoundCategory category = SoundCategory.valueOf(this.category);

        return List.of(new PacketPlayOutCustomSoundEffect(
                effectKey,
                category,
                new Vec3D(this.x, this.y, this.z),
                this.volume,
                this.pitch
        ));
    }
}