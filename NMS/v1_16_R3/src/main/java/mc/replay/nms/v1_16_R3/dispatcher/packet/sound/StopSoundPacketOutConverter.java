package mc.replay.nms.v1_16_R3.dispatcher.packet.sound;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.nms.v1_16_R3.recordable.sound.RecStopSound;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayOutStopSound;
import org.bukkit.NamespacedKey;
import org.bukkit.SoundCategory;

import java.lang.reflect.Field;
import java.util.List;

public final class StopSoundPacketOutConverter implements DispatcherPacketOut<PacketPlayOutStopSound> {

    @Override
    public List<Recordable> getRecordables(PacketPlayOutStopSound packet) {
        try {
            Field effectField = packet.getClass().getDeclaredField("a");
            effectField.setAccessible(true);

            MinecraftKey effectKey = (MinecraftKey) effectField.get(packet);
            NamespacedKey namespacedKey = new NamespacedKey(effectKey.getNamespace(), effectKey.getKey());

            Field categoryField = packet.getClass().getDeclaredField("b");
            categoryField.setAccessible(true);

            SoundCategory category = (SoundCategory) categoryField.get(packet);

            return List.of(RecStopSound.of(namespacedKey, category.name()));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}