package mc.replay.nms.v1_16_R3.dispatcher.packet.sound;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.utils.PacketConverter;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_R3.recordable.sound.RecNamedSoundEffect;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_16_R3.SoundCategory;
import net.minecraft.server.v1_16_R3.SoundEffect;
import org.bukkit.NamespacedKey;

import java.util.List;
import java.util.function.Function;

public final class NamedSoundEffectPacketOutConverter implements DispatcherPacketOut<PacketPlayOutNamedSoundEffect> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(PacketPlayOutNamedSoundEffect packet) {
        PacketConverter.ConvertedPacket convertedPacket = this.convert(packet);

        SoundEffect soundEffect = convertedPacket.get("a", SoundEffect.class);
        MinecraftKey minecraftKey = (MinecraftKey) JavaReflections.getField(SoundEffect.class, MinecraftKey.class, "b")
                .get(soundEffect);

        NamespacedKey namespacedKey = new NamespacedKey(minecraftKey.getNamespace(), minecraftKey.getKey());

        SoundCategory soundCategory = convertedPacket.get("b", SoundCategory.class);

        int x = convertedPacket.get("c", Integer.class);
        int y = convertedPacket.get("d", Integer.class);
        int z = convertedPacket.get("e", Integer.class);

        float volume = convertedPacket.get("f", Float.class);
        float pitch = convertedPacket.get("g", Float.class);

        return List.of(RecNamedSoundEffect.of(namespacedKey, soundCategory.name(), x, y, z, volume, pitch));
    }
}