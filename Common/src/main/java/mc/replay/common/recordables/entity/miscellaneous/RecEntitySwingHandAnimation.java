package mc.replay.common.recordables.entity.miscellaneous;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.ClientboundEntityAnimationPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntitySwingHandAnimation(EntityId entityId, int handId) implements RecordableEntity {

    public static RecEntitySwingHandAnimation of(EntityId entityId, int handId) {
        return new RecEntitySwingHandAnimation(
                entityId,
                handId
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        return List.of(new ClientboundEntityAnimationPacket(
                data.entityId(),
                this.handId // TODO
        ));
    }
}