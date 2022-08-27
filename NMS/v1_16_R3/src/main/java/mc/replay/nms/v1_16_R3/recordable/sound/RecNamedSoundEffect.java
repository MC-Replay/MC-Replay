package mc.replay.nms.v1_16_R3.recordable.sound;

import mc.replay.common.recordables.RecordableSound;
import mc.replay.common.utils.reflection.JavaReflections;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecNamedSoundEffect(NamespacedKey soundKey, String category, int x, int y, int z, float volume,
                                  float pitch) implements RecordableSound {

    public static RecNamedSoundEffect of(NamespacedKey soundKey, String category, int x, int y, int z, float volume, float pitch) {
        return new RecNamedSoundEffect(
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
    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Void, Void> function) {
        SoundEffect effect = IRegistry.SOUND_EVENT.get(new MinecraftKey(this.soundKey.getNamespace(), this.soundKey.getKey()));
        if (effect == null) return List.of();

        SoundCategory category = SoundCategory.valueOf(this.category);

        PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect(
                effect,
                category,
                this.x,
                this.y,
                this.z,
                this.volume,
                this.pitch
        );

        JavaReflections.getField(packet.getClass(), "c", int.class).set(packet, this.x);
        JavaReflections.getField(packet.getClass(), "d", int.class).set(packet, this.y);
        JavaReflections.getField(packet.getClass(), "e", int.class).set(packet, this.z);

        return List.of(packet);
    }
}