package mc.replay.recording.dispatcher.dispatchers.packet.entity;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityStatus;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityStatusPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;

import java.util.List;

public class EntityStatusPacketOutDispatcher implements DispatcherPacketOut<ClientboundEntityStatusPacket> {

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundEntityStatusPacket packet) {
        return List.of(
                new RecEntityStatus(
                        EntityId.of(packet.entityId()),
                        packet.status()
                )
        );
    }
}