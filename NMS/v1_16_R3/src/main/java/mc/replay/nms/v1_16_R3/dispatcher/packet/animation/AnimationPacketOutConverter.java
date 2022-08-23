package mc.replay.nms.v1_16_R3.dispatcher.packet.animation;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.utils.PacketConverter;
import mc.replay.nms.v1_16_R3.recordable.entity.miscellaneous.RecEntityAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;

import java.util.List;
import java.util.function.Function;

public final class AnimationPacketOutConverter implements DispatcherPacketOut<PacketPlayOutAnimation> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(PacketPlayOutAnimation packet) {
        PacketConverter.ConvertedPacket convertedPacket = this.convert(packet);

        int entityId = convertedPacket.get("a", Integer.class);
        int animation = convertedPacket.get("b", Integer.class);

        return List.of(RecEntityAnimation.of(EntityId.of(entityId), animation));
    }
}