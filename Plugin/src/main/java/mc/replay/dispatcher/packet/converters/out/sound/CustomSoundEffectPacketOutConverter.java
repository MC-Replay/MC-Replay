package mc.replay.dispatcher.packet.converters.out.sound;

import mc.replay.dispatcher.packet.converters.ReplayPacketOutConverter;
import mc.replay.nms.v1_16_5.recordable.sound.RecCustomSoundEffect;
import mc.replay.common.utils.reflection.JavaReflections;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class CustomSoundEffectPacketOutConverter implements ReplayPacketOutConverter<RecCustomSoundEffect> {

    @Override
    public @NotNull String packetClassName() {
        return "PacketPlayOutCustomSoundEffect";
    }

    @Override
    public @Nullable RecCustomSoundEffect recordableFromPacket(Object packet) {
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

            int x = JavaReflections.getField(packet.getClass(), "c", int.class).get(packet);
            int y = JavaReflections.getField(packet.getClass(), "d", int.class).get(packet);
            int z = JavaReflections.getField(packet.getClass(), "e", int.class).get(packet);
            float volume = JavaReflections.getField(packet.getClass(), "f", float.class).get(packet);
            float pitch = JavaReflections.getField(packet.getClass(), "g", float.class).get(packet);

            return RecCustomSoundEffect.of(namespacedKey, categoryName, x, y, z, volume, pitch);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}