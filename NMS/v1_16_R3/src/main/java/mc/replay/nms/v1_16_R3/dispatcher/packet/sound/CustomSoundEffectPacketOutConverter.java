package mc.replay.nms.v1_16_R3.dispatcher.packet.sound;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.PacketConverter;
import mc.replay.nms.v1_16_R3.recordable.sound.RecCustomSoundEffect;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayOutCustomSoundEffect;
import net.minecraft.server.v1_16_R3.SoundCategory;
import org.bukkit.NamespacedKey;

import java.util.List;

public final class CustomSoundEffectPacketOutConverter implements DispatcherPacketOut<PacketPlayOutCustomSoundEffect> {

    @Override
    public List<Recordable> getRecordables(PacketPlayOutCustomSoundEffect packet) {
        PacketConverter.ConvertedPacket convertedPacket = this.convert(packet);

        MinecraftKey minecraftKey = convertedPacket.get("a", MinecraftKey.class);
        NamespacedKey namespacedKey = new NamespacedKey(minecraftKey.getNamespace(), minecraftKey.getKey());
        SoundCategory category = convertedPacket.get("b", SoundCategory.class);
        int x = convertedPacket.get("c", int.class);
        int y = convertedPacket.get("d", int.class);
        int z = convertedPacket.get("e", int.class);
        float volume = convertedPacket.get("f", float.class);
        float pitch = convertedPacket.get("g", float.class);

        return List.of(RecCustomSoundEffect.of(
                namespacedKey,
                category.name(),
                x,
                y,
                z,
                volume,
                pitch
        ));
    }
}