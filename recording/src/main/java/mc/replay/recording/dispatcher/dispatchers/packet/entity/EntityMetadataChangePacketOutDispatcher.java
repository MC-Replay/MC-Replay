package mc.replay.recording.dispatcher.dispatchers.packet.entity;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityMetadataChange;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityMetadataChangePacketOutDispatcher implements DispatcherPacketOut<ClientboundEntityMetadataPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundEntityMetadataPacket packet) {
        Map<Integer, Metadata.Entry<?>> entries = new HashMap<>(packet.entries());
        entries.remove(0); // TODO should remove all bit mask entries

        if (entries.isEmpty()) return List.of();

        return List.of(
                new RecEntityMetadataChange(
                        EntityId.of(packet.entityId()),
                        entries
                )
        );
    }
}