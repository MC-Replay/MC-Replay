package mc.replay.recordables.sound;

import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public record RecCustomSoundEffect(NamespacedKey soundKey, String category, int x, int y, int z, float volume,
                                   float pitch) implements SoundRecordable {

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
        if (packet == null) return;

        MinecraftPlayerNMS.sendPacket(viewer, packet);
    }

    private Object createPacket() {
        SoundEffect effect = IRegistry.SOUND_EVENT.get(new MinecraftKey(this.soundKey.getNamespace(), this.soundKey.getKey()));
        if (effect == null) return null;

        SoundCategory category = SoundCategory.valueOf(this.category);

        PacketPlayOutCustomSoundEffect packet = new PacketPlayOutCustomSoundEffect();

        JavaReflections.getField(packet.getClass(), "a", SoundEffect.class).set(packet, effect);
        JavaReflections.getField(packet.getClass(), "b", SoundCategory.class).set(packet, category);

        JavaReflections.getField(packet.getClass(), "c", int.class).set(packet, this.x);
        JavaReflections.getField(packet.getClass(), "d", int.class).set(packet, this.y);
        JavaReflections.getField(packet.getClass(), "e", int.class).set(packet, this.z);

        JavaReflections.getField(packet.getClass(), "f", float.class).set(packet, this.volume);
        JavaReflections.getField(packet.getClass(), "g", float.class).set(packet, this.pitch);

        return packet;
    }
}