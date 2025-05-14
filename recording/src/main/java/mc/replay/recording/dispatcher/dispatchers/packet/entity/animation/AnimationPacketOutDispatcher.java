package mc.replay.recording.dispatcher.dispatchers.packet.entity.animation;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityAnimation;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityAnimationPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.util.List;

public final class AnimationPacketOutDispatcher extends DispatcherPacketOut<ClientboundEntityAnimationPacket> {

    private AnimationPacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundEntityAnimationPacket packet) {
        return List.of(
                new RecEntityAnimation(
                        EntityId.of(packet.entityId()),
                        packet.animation()
                )
        );
    }
}