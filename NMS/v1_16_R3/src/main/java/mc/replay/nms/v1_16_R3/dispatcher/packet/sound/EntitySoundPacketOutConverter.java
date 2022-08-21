package mc.replay.nms.v1_16_R3.dispatcher.packet.sound;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.PacketConverter;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_R3.recordable.sound.RecEntitySound;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntitySound;
import net.minecraft.server.v1_16_R3.SoundCategory;
import net.minecraft.server.v1_16_R3.SoundEffect;
import org.bukkit.NamespacedKey;

import java.util.List;

public final class EntitySoundPacketOutConverter implements DispatcherPacketOut<PacketPlayOutEntitySound> {

    @Override
    public List<Recordable> getRecordables(PacketPlayOutEntitySound packet) {
        PacketConverter.ConvertedPacket convertedPacket = this.convert(packet);

        SoundEffect soundEffect = convertedPacket.get("a", SoundEffect.class);
        MinecraftKey minecraftKey = (MinecraftKey) JavaReflections.getField(SoundEffect.class, MinecraftKey.class, "b")
                .get(soundEffect);

        NamespacedKey namespacedKey = new NamespacedKey(minecraftKey.getNamespace(), minecraftKey.getKey());

        SoundCategory soundCategory = convertedPacket.get("b", SoundCategory.class);

        int entityId = convertedPacket.get("c", Integer.class);

        float volume = convertedPacket.get("d", Float.class);
        float pitch = convertedPacket.get("e", Float.class);

        return List.of(RecEntitySound.of(namespacedKey, soundCategory.name(), entityId, volume, pitch));
    }
}