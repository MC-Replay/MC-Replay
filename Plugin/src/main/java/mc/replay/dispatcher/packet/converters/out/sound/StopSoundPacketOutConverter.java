package mc.replay.dispatcher.packet.converters.out.sound;

import mc.replay.dispatcher.packet.converters.ReplayPacketOutConverter;
import mc.replay.nms.v1_16_5.recordable.sound.RecStopSound;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class StopSoundPacketOutConverter implements ReplayPacketOutConverter<RecStopSound> {

    @Override
    public @NotNull String packetClassName() {
        return "PacketPlayOutStopSound";
    }

    @Override
    public @Nullable RecStopSound recordableFromPacket(Object packet) {
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

            return RecStopSound.of(namespacedKey, categoryName);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}