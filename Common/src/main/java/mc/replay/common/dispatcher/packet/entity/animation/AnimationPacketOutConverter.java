package mc.replay.common.dispatcher.packet.entity.animation;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.entity.miscellaneous.RecEntityAnimation;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityAnimationPacket;

import java.util.List;
import java.util.function.Function;

public final class AnimationPacketOutConverter implements DispatcherPacketOut<ClientboundEntityAnimationPacket> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(ClientboundEntityAnimationPacket packet) {
        return List.of(RecEntityAnimation.of(
                EntityId.of(packet.entityId()),
                packet.animation()
        ));
    }
}