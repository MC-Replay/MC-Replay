package mc.replay.recording.dispatcher.dispatchers.packet.entity;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityDestroyPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;

import java.util.List;
import java.util.stream.Collectors;

public final class EntityDestroyPacketOutDispatcher implements DispatcherPacketOut<ClientboundEntityDestroyPacket> {

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundEntityDestroyPacket packet) {
        return List.of(
                new RecEntityDestroy(
                        packet.entityIds().stream()
                                .map(EntityId::of)
                                .collect(Collectors.toList())
                )
        );
    }
}
