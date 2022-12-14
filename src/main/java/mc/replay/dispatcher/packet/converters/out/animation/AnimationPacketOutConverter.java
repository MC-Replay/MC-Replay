package mc.replay.dispatcher.packet.converters.out.animation;

import mc.replay.dispatcher.packet.converters.ReplayPacketOutConverter;
import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.miscellaneous.RecEntityAnimation;
import mc.replay.utils.reflection.JavaReflections;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnimationPacketOutConverter implements ReplayPacketOutConverter<RecEntityAnimation> {

    @Override
    public @NotNull String packetClassName() {
        return "PacketPlayOutAnimation";
    }

    @Override
    public @Nullable RecEntityAnimation recordableFromPacket(Object packet) {
        try {
            int entityId = JavaReflections.getField(packet.getClass(), "a", int.class).get(packet);
            int animation = JavaReflections.getField(packet.getClass(), "b", int.class).get(packet);

            return RecEntityAnimation.of(EntityId.of(entityId), animation);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}