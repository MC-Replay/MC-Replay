package mc.replay.common.recordables.actions.entity.miscellaneous;

import mc.replay.api.recording.recordables.action.EntityRecordableAction;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityMetadataChange;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.function.Function;

public record RecEntityMetadataChangeAction() implements EntityRecordableAction<RecEntityMetadataChange> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityMetadataChange recordable, @UnknownNullability Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(recordable.entityId().entityId());
        if (data == null) return List.of();

        data.entity().addMetadata(recordable.metadata());

        return List.of(
                new ClientboundEntityMetadataPacket(
                        data.entityId(),
                        recordable.metadata()
                )
        );
    }
}