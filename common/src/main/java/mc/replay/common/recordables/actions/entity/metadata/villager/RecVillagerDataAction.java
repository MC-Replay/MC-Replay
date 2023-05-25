package mc.replay.common.recordables.actions.entity.metadata.villager;

import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.metadata.villager.RecVillagerData;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.villager.VillagerMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;

public record RecVillagerDataAction() implements EntityRecordableAction<RecVillagerData> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecVillagerData recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        EntityMetadata entityMetadata = data.entity().getMetadata();
        if (entityMetadata instanceof VillagerMetadata villagerMetadata) {
            Metadata metadata = villagerMetadata.getMetadata();

            metadata.detectChanges(true);

            metadata.setIndex(VillagerMetadata.VILLAGER_DATA_INDEX, Metadata.VillagerData(
                    recordable.data()[0],
                    recordable.data()[1],
                    recordable.data()[2]
            ));

            Map<Integer, Metadata.Entry<?>> changes = metadata.getChanges();
            metadata.detectChanges(false);

            if (changes == null || changes.isEmpty()) return List.of();

            return List.of(
                    new ClientboundEntityMetadataPacket(
                            data.entityId(),
                            changes
                    )
            );
        }

        return List.of();
    }
}