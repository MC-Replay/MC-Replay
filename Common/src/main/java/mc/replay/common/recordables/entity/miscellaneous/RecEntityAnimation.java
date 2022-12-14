package mc.replay.common.recordables.entity.miscellaneous;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.packetlib.data.entity.EntityAnimation;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityAnimationPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntityAnimation(EntityId entityId, EntityAnimation animation) implements RecordableEntity {

    public static RecEntityAnimation of(EntityId entityId, EntityAnimation animation) {
        return new RecEntityAnimation(
                entityId,
                animation
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());
        if (data == null) return List.of();

        return List.of(new ClientboundEntityAnimationPacket(
                data.entityId(),
                this.animation
        ));
    }
}