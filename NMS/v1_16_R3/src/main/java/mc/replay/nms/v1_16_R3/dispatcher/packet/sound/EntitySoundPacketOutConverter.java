package mc.replay.nms.v1_16_R3.dispatcher.packet.sound;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_R3.recordable.sound.RecEntitySound;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntitySound;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Field;
import java.util.List;

public final class EntitySoundPacketOutConverter implements DispatcherPacketOut<PacketPlayOutEntitySound> {

    @Override
    public List<Recordable> getRecordables(PacketPlayOutEntitySound packet) {
        try {
            Field effectField = packet.getClass().getDeclaredField("a");
            effectField.setAccessible(true);

            Object soundEffect = effectField.get(packet);

            Field minecraftKeyField = soundEffect.getClass().getDeclaredField("b");
            minecraftKeyField.setAccessible(true);

            Object minecraftKey = minecraftKeyField.get(soundEffect);
            String namespace = (String) minecraftKey.getClass().getMethod("getNamespace").invoke(minecraftKey);
            String key = (String) minecraftKey.getClass().getMethod("getKey").invoke(minecraftKey);
            NamespacedKey namespacedKey = new NamespacedKey(namespace, key);

            Field categoryField = packet.getClass().getDeclaredField("b");
            categoryField.setAccessible(true);

            Object category = categoryField.get(packet);
            String categoryName = (String) category.getClass().getMethod("name").invoke(category);

            int entityId = JavaReflections.getField(packet.getClass(), "c", int.class).get(packet);

            float volume = JavaReflections.getField(packet.getClass(), "d", float.class).get(packet);
            float pitch = JavaReflections.getField(packet.getClass(), "e", float.class).get(packet);

            return List.of(RecEntitySound.of(namespacedKey, categoryName, entityId, volume, pitch));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}