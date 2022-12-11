package mc.replay.common.recordables.entity.miscellaneous;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.ClientboundEntityAnimationPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntityAnimation(EntityId entityId, int animationId) implements RecordableEntity {

    public static RecEntityAnimation of(EntityId entityId, int animationId) {
        return new RecEntityAnimation(
                entityId,
                animationId
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());
        if (data == null) return List.of();

        return List.of(new ClientboundEntityAnimationPacket(
                data.entityId(),
                this.animationId
        ));
    }
}