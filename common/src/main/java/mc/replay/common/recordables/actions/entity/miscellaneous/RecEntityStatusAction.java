package mc.replay.common.recordables.actions.entity.miscellaneous;

import mc.replay.api.recording.recordables.action.EntityRecordableAction;
import mc.replay.api.recording.recordables.entity.RecordableEntityData;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityStatus;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityStatusPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.function.Function;

public record RecEntityStatusAction() implements EntityRecordableAction<RecEntityStatus> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityStatus recordable, @UnknownNullability Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(recordable.entityId().entityId());
        if (data == null) return List.of();

        return List.of(
                new ClientboundEntityStatusPacket(
                        data.entityId(),
                        recordable.status()
                )
        );
    }
}