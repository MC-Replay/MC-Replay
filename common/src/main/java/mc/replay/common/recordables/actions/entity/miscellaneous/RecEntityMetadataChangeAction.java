package mc.replay.common.recordables.actions.entity.miscellaneous;

import mc.replay.api.recording.recordables.action.EntityRecordableAction;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityMetadataChange;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.wrapper.entity.metadata.ShooterProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public record RecEntityMetadataChangeAction() implements EntityRecordableAction<RecEntityMetadataChange> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityMetadataChange recordable, @UnknownNullability Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(recordable.entityId().entityId());
        if (data == null) return List.of();

        data.entity().addMetadata(recordable.metadata());

        if (data.entity().getMetadata() instanceof ShooterProvider provider) {
            Integer shooterId = provider.getShooterId();
            RecordableEntityData shooterData = function.apply(shooterId == null ? -1 : shooterId);
            if (shooterData == null) return List.of();

            provider.setShooterId(shooterData.entityId());

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