package mc.replay.recording.dispatcher.dispatchers.packet.entity.metadata;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityMetadataChange;
import mc.replay.nms.entity.REntity;
import mc.replay.nms.entity.metadata.other.AreaEffectCloudMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityMetadataChangePacketOutDispatcher extends DispatcherPacketOut<ClientboundEntityMetadataPacket> {

    private EntityMetadataChangePacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundEntityMetadataPacket packet) {
        Map<Integer, Metadata.Entry<?>> entries = new HashMap<>(packet.entries());

        int id = packet.entityId();
        EntityId entityId = EntityId.of(id);

        REntity entityWrapper = session.getEntityTracker().getOrFindEntityWrapper(null, id, true);
        if (entityWrapper == null) return List.of();

        EntityMetadata metadata = entityWrapper.getMetadata();
        if (metadata instanceof AreaEffectCloudMetadata) return List.of();

        Metadata before = new Metadata();
        for (Map.Entry<Integer, Metadata.Entry<?>> entry : new HashMap<>(metadata.getEntries()).entrySet()) {
            before.setIndex(entry.getKey(), entry.getValue());
        }

        entityWrapper.addMetadata(entries);

        try {
            EntityMetadata beforeMetadata = metadata.getClass().getDeclaredConstructor(Metadata.class).newInstance(before);
            List<Recordable> recordables = new ArrayList<>(this.helpers.getMetadataHelper().read(beforeMetadata, metadata, entries, entityId));

            if (entries.isEmpty()) return recordables;

            recordables.add(new RecEntityMetadataChange(
                    entityId,
                    entries
            ));

            return recordables;
        } catch (Exception exception) {
            exception.printStackTrace();
            return List.of();
        }
    }
}