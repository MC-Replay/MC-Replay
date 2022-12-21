package mc.replay.common.recordables.entity.action;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntityGliding(EntityId entityId, boolean gliding) implements RecordableEntity {

    public static RecEntityGliding of(EntityId entityId, boolean gliding) {
        return new RecEntityGliding(entityId, gliding);
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        EntityMetadata entityMetadata = new EntityMetadata(null, new Metadata());
        entityMetadata.setIsFlyingWithElytra(this.gliding);

        return List.of(new ClientboundEntityMetadataPacket(
                data.entityId(),
                entityMetadata.getEntries()
        ));
    }
}