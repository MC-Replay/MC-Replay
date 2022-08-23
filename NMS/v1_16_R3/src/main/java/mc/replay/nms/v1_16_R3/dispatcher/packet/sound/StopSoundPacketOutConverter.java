package mc.replay.nms.v1_16_R3.dispatcher.packet.sound;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.utils.PacketConverter;
import mc.replay.nms.v1_16_R3.recordable.sound.RecStopSound;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayOutStopSound;
import net.minecraft.server.v1_16_R3.SoundCategory;
import org.bukkit.NamespacedKey;

import java.util.List;
import java.util.function.Function;

public final class StopSoundPacketOutConverter implements DispatcherPacketOut<PacketPlayOutStopSound> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(PacketPlayOutStopSound packet) {
        PacketConverter.ConvertedPacket convertedPacket = this.convert(packet);

        MinecraftKey minecraftKey = convertedPacket.get("a", MinecraftKey.class);
        NamespacedKey namespacedKey = new NamespacedKey(minecraftKey.getNamespace(), minecraftKey.getKey());

        SoundCategory soundCategory = convertedPacket.get("b", SoundCategory.class);

        return List.of(RecStopSound.of(namespacedKey, soundCategory.name()));
    }
}