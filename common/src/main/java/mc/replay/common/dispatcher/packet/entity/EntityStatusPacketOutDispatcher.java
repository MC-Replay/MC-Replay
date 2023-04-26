package mc.replay.common.dispatcher.packet.entity;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityStatus;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityStatusPacket;

import java.util.List;

public class EntityStatusPacketOutDispatcher implements DispatcherPacketOut<ClientboundEntityStatusPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundEntityStatusPacket packet) {
        return List.of(
                new RecEntityStatus(
                        EntityId.of(packet.entityId()),
                        packet.status()
                )
        );
    }
}