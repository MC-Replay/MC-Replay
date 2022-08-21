package mc.replay.nms.v1_16_R3.recordable.sound;

import mc.replay.common.recordables.RecordableSound;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public record RecEntitySound(NamespacedKey soundKey, String category, int entityId, float volume,
                             float pitch) implements RecordableSound {

    public static RecEntitySound of(NamespacedKey soundKey, String category, int entityId, float volume, float pitch) {
        return new RecEntitySound(
                soundKey,
                category,
                entityId,
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

        PacketPlayOutEntitySound packet = new PacketPlayOutEntitySound();

        JavaReflections.getField(packet.getClass(), "a", SoundEffect.class).set(packet, effect);
        JavaReflections.getField(packet.getClass(), "b", SoundCategory.class).set(packet, category);

        JavaReflections.getField(packet.getClass(), "c", int.class).set(packet, this.entityId);

        JavaReflections.getField(packet.getClass(), "d", float.class).set(packet, this.volume);
        JavaReflections.getField(packet.getClass(), "e", float.class).set(packet, this.pitch);

        return packet;
    }
}