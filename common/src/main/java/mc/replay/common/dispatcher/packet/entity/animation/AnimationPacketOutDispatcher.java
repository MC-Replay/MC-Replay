package mc.replay.common.dispatcher.packet.entity.animation;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityAnimation;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityAnimationPacket;

import java.util.List;

public final class AnimationPacketOutDispatcher implements DispatcherPacketOut<ClientboundEntityAnimationPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundEntityAnimationPacket packet) {
        return List.of(new RecEntityAnimation(
                EntityId.of(packet.entityId()),
                packet.animation()
        ));
    }
}