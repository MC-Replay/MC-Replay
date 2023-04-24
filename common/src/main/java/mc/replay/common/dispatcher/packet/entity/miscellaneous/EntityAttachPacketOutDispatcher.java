package mc.replay.common.dispatcher.packet.entity.miscellaneous;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityAttach;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityAttachPacket;

import java.util.List;

public final class EntityAttachPacketOutDispatcher implements DispatcherPacketOut<ClientboundEntityAttachPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundEntityAttachPacket packet) {
        return List.of(
                new RecEntityAttach(
                        EntityId.of(packet.attachedEntityId()),
                        EntityId.of(packet.holdingEntityId())
                )
        );
    }
}