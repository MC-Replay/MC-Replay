package mc.replay.dispatcher.packet.converters.out.sound;

import mc.replay.dispatcher.packet.converters.ReplayPacketOutConverter;
import mc.replay.nms.v1_16_5.recordable.sound.RecEntitySound;
import mc.replay.common.utils.reflection.JavaReflections;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class EntitySoundPacketOutConverter implements ReplayPacketOutConverter<RecEntitySound> {

    @Override
    public @NotNull String packetClassName() {
        return "PacketPlayOutEntitySound";
    }

    @Override
    public @Nullable RecEntitySound recordableFromPacket(Object packet) {
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

            return RecEntitySound.of(namespacedKey, categoryName, entityId, volume, pitch);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}