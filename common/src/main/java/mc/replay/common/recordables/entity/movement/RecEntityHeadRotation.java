package mc.replay.common.recordables.entity.movement;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableBufferTypes;
import mc.replay.common.recordables.interfaces.RecordableEntity;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityHeadRotationPacket;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public record RecEntityHeadRotation(EntityId entityId, float yaw) implements RecordableEntity {

    public RecEntityHeadRotation(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(RecordableBufferTypes.SINGLE_ENTITY_ROTATION)
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());
        if (data == null) return new ArrayList<>();

        return List.of(new ClientboundEntityHeadRotationPacket(
                data.entityId(),
                this.yaw
        ));
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(RecordableBufferTypes.SINGLE_ENTITY_ROTATION, this.yaw);
    }
}