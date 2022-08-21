package mc.replay.nms.v1_16_R3.dispatcher.packet.sound;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_R3.recordable.sound.RecNamedSoundEffect;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_16_R3.SoundCategory;
import net.minecraft.server.v1_16_R3.SoundEffect;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

public final class NamedSoundEffectPacketOutConverter implements DispatcherPacketOut<PacketPlayOutNamedSoundEffect> {

    @Override
    public @Nullable List<Recordable> getRecordables(PacketPlayOutNamedSoundEffect packet) {
        try {
            Field effectField = packet.getClass().getDeclaredField("a");
            effectField.setAccessible(true);

            SoundEffect soundEffect = (SoundEffect) effectField.get(packet);

            Field minecraftKeyField = soundEffect.getClass().getDeclaredField("b");
            minecraftKeyField.setAccessible(true);

            MinecraftKey effectKey = (MinecraftKey) minecraftKeyField.get(soundEffect);
            NamespacedKey namespacedKey = new NamespacedKey(effectKey.getNamespace(), effectKey.getKey());

            Field categoryField = packet.getClass().getDeclaredField("b");
            categoryField.setAccessible(true);

            SoundCategory category = (SoundCategory) categoryField.get(packet);

            int x = JavaReflections.getField(packet.getClass(), "c", int.class).get(packet);
            int y = JavaReflections.getField(packet.getClass(), "d", int.class).get(packet);
            int z = JavaReflections.getField(packet.getClass(), "e", int.class).get(packet);
            float volume = JavaReflections.getField(packet.getClass(), "f", float.class).get(packet);
            float pitch = JavaReflections.getField(packet.getClass(), "g", float.class).get(packet);

            return List.of(RecNamedSoundEffect.of(namespacedKey, category.name(), x, y, z, volume, pitch));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}