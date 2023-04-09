package mc.replay.common.recordables.entity.miscellaneous;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.interfaces.RecordableEntity;
import mc.replay.packetlib.data.entity.EntityAnimation;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityAnimationPacket;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public record RecEntityAnimation(EntityId entityId, EntityAnimation animation) implements RecordableEntity {

    public RecEntityAnimation(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.readEnum(EntityAnimation.class)
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());
        if (data == null) return new ArrayList<>();

        return List.of(new ClientboundEntityAnimationPacket(
                data.entityId(),
                this.animation
        ));
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.writeEnum(EntityAnimation.class, this.animation);
    }
}