package mc.replay.common.dispatcher.packet.entity.item;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.entity.item.RecCollectItem;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundCollectItemPacket;

import java.util.List;

public final class CollectItemPacketOutDispatcher implements DispatcherPacketOut<ClientboundCollectItemPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundCollectItemPacket packet) {
        EntityId collectedEntityId = EntityId.of(packet.collectedEntityId());
        EntityId collectorEntityid = EntityId.of(packet.collectorEntityId());

        return List.of(
                new RecCollectItem(
                        collectedEntityId,
                        collectorEntityid,
                        packet.pickupItemCount()
                )
        );
    }
}
