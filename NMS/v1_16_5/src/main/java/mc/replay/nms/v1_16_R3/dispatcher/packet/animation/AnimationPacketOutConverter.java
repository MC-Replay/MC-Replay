package mc.replay.nms.v1_16_R3.dispatcher.packet.animation;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_R3.recordable.entity.miscellaneous.RecEntityAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;

import java.util.List;

public class AnimationPacketOutConverter implements DispatcherPacketOut<PacketPlayOutAnimation> {

    @Override
    public List<Recordable> getRecordable(Object packetClass) {
        PacketPlayOutAnimation packet = (PacketPlayOutAnimation) packetClass;

        try {
            int entityId = JavaReflections.getField(packet.getClass(), "a", int.class).get(packet);
            int animation = JavaReflections.getField(packet.getClass(), "b", int.class).get(packet);

            return List.of(RecEntityAnimation.of(EntityId.of(entityId), animation));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}