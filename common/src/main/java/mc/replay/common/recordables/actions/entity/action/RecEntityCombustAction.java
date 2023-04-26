package mc.replay.common.recordables.actions.entity.action;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.action.RecEntityCombust;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.wrapper.entity.EntityWrapper;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public record RecEntityCombustAction() implements EntityRecordableAction<RecEntityCombust> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityCombust recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        EntityWrapper entity = data.entity();
        EntityMetadata metadata = entity.getMetadata();
        metadata.setOnFire(recordable.combust());

        return List.of(
                new ClientboundEntityMetadataPacket(
                        data.entityId(),
                        metadata.getEntries()
                )
        );
    }
}