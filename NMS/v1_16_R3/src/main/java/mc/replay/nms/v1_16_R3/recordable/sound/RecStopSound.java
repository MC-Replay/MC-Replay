package mc.replay.nms.v1_16_R3.recordable.sound;

import mc.replay.common.recordables.RecordableSound;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayOutStopSound;
import net.minecraft.server.v1_16_R3.SoundCategory;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecStopSound(NamespacedKey soundKey, String category) implements RecordableSound {

    public static RecStopSound of(NamespacedKey soundKey, String category) {
        return new RecStopSound(
                soundKey,
                category
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Void, Void> function) {
        MinecraftKey minecraftKey = new MinecraftKey(this.soundKey.getNamespace(), this.soundKey.getKey());
        SoundCategory category = SoundCategory.valueOf(this.category);
        return List.of(new PacketPlayOutStopSound(minecraftKey, category));
    }
}