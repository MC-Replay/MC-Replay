package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.metadata.RecEntityMetadataChange;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.wrapper.entity.metadata.ShooterProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;

public record RecEntityMetadataChangeAction() implements EntityRecordableAction<RecEntityMetadataChange> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityMetadataChange recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        data.entity().addMetadata(recordable.metadata());

        if (data.entity().getMetadata() instanceof ShooterProvider shooterProvider) {
            Integer shooterId = shooterProvider.getShooterId();
            RecordableEntityData shooterData = provider.getEntity(shooterId == null ? -1 : shooterId);
            if (shooterData == null) return List.of();

            shooterProvider.setShooterId(shooterData.entityId());

            for (Map.Entry<Integer, Metadata.Entry<?>> entry : data.entity().getMetadata().getEntries().entrySet()) {
                recordable.metadata().replace(entry.getKey(), entry.getValue());
            }
        }

        return List.of(
                new ClientboundEntityMetadataPacket(
                        data.entityId(),
                        recordable.metadata()
                )
        );
    }
}