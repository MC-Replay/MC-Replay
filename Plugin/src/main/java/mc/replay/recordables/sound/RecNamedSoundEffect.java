package mc.replay.recordables.sound;

import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public record RecNamedSoundEffect(NamespacedKey soundKey, String category, int x, int y, int z, float volume,
                                  float pitch) implements SoundRecordable {

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
    public void play(Player viewer) {
        Object packet = this.createPacket();
        if (packet == null) return;

        MinecraftPlayerNMS.sendPacket(viewer, packet);
    }

    private Object createPacket() {
        SoundEffect effect = IRegistry.SOUND_EVENT.get(new MinecraftKey(this.soundKey.getNamespace(), this.soundKey.getKey()));
        if (effect == null) return null;

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

        return packet;
    }
}