package mc.replay.common.recordables.entity.miscellaneous;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.packetlib.data.entity.PlayerHand;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityAnimationPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntitySwingHandAnimation(EntityId entityId, PlayerHand hand) implements RecordableEntity {

    public static RecEntitySwingHandAnimation of(EntityId entityId, PlayerHand hand) {
        return new RecEntitySwingHandAnimation(
                entityId,
                hand
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        return List.of(new ClientboundEntityAnimationPacket(
                data.entityId(),
                this.hand.toAnimation()
        ));
    }
}