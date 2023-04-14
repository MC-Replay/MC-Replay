package mc.replay.common.dispatcher.packet.entity;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityDestroyPacket;

import java.util.List;
import java.util.stream.Collectors;

public final class EntityDestroyPacketOutDispatcher implements DispatcherPacketOut<ClientboundEntityDestroyPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundEntityDestroyPacket packet) {
        return List.of(
                new RecEntityDestroy(
                        packet.entityIds().stream()
                                .map(EntityId::of)
                                .collect(Collectors.toList())
                )
        );
    }
}
