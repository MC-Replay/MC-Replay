package mc.replay.common.recordables.entity.action;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.wrapper.entity.EntityWrapper;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import org.bukkit.entity.Pose;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntitySneaking(EntityId entityId, boolean sneaking) implements RecordableEntity {

    public static RecEntitySneaking of(EntityId entityId, boolean sneaking) {
        return new RecEntitySneaking(entityId, sneaking);
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        EntityWrapper entity = data.entity();
        EntityMetadata metadata = entity.getMetadata();
        metadata.setSneaking(this.sneaking);
        metadata.setPose((this.sneaking) ? Pose.SNEAKING : Pose.STANDING);

        return List.of(new ClientboundEntityMetadataPacket(
                data.entityId(),
                metadata.getEntries()
        ));
    }
}