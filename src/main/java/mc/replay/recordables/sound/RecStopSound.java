package mc.replay.recordables.sound;

import mc.replay.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayOutStopSound;
import net.minecraft.server.v1_16_R3.SoundCategory;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public record RecStopSound(NamespacedKey soundKey, String category) implements SoundRecordable {

    public static RecStopSound of(NamespacedKey soundKey, String category) {
        return new RecStopSound(
                soundKey,
                category
        );
    }

    @Override
    public void play(Player viewer) {
        MinecraftPlayerNMS.sendPacket(viewer, this.createPacket());
    }

    private Object createPacket() {
        MinecraftKey minecraftKey = new MinecraftKey(this.soundKey.getNamespace(), this.soundKey.getKey());
        SoundCategory category = SoundCategory.valueOf(this.category);
        return new PacketPlayOutStopSound(minecraftKey, category);
    }
}