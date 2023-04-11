package mc.replay.common.dispatcher.packet.entity;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityMetadataChange;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityMetadataChangePacketOutConverter implements DispatcherPacketOut<ClientboundEntityMetadataPacket> {

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