package mc.replay.recording.dispatcher.dispatchers.packet.entity.item;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.item.RecCollectItem;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundCollectItemPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.util.List;

public final class CollectItemPacketOutDispatcher extends DispatcherPacketOut<ClientboundCollectItemPacket> {

    private CollectItemPacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundCollectItemPacket packet) {
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
