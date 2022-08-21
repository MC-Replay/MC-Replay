package mc.replay.nms.v1_16_R3.recordable.sound;

import mc.replay.common.recordables.RecordableSound;
import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayOutCustomSoundEffect;
import net.minecraft.server.v1_16_R3.SoundCategory;
import net.minecraft.server.v1_16_R3.Vec3D;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

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
    public void play(Player viewer) {
        Object packet = this.createPacket();
        MinecraftPlayerNMS.sendPacket(viewer, packet);
    }

    private Object createPacket() {
        MinecraftKey effectKey = new MinecraftKey(this.soundKey.getNamespace(), this.soundKey.getKey());
        SoundCategory category = SoundCategory.valueOf(this.category);

        return new PacketPlayOutCustomSoundEffect(
                effectKey,
                category,
                new Vec3D(this.x, this.y, this.z),
                this.volume,
                this.pitch
        );
    }
}