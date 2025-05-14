package mc.replay.recording.dispatcher.dispatchers.packet.entity.miscellaneous;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityAttach;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityAttachPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.util.List;

public final class EntityAttachPacketOutDispatcher extends DispatcherPacketOut<ClientboundEntityAttachPacket> {

    private EntityAttachPacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundEntityAttachPacket packet) {
        return List.of(
                new RecEntityAttach(
                        EntityId.of(packet.attachedEntityId()),
                        EntityId.of(packet.holdingEntityId())
                )
        );
    }
}