package mc.replay.recording.dispatcher.helpers.metadata;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.*;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.utils.ProtocolVersion;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import org.bukkit.entity.Pose;

import java.util.*;

import static mc.replay.wrapper.entity.metadata.EntityMetadata.*;

public final class EntityMetadataReader implements MetadataReader<EntityMetadata> {

    @Override
    public List<Recordable> read(EntityMetadata before, EntityMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(MASK_INDEX) != null) {
            if (metadata.isOnFire() != before.isOnFire()) {
                recordables.add(
                        new RecEntityCombust(
                                entityId,
                                metadata.isOnFire()
                        )
                );
            }

            if (metadata.isInvisible() != before.isInvisible()) {
                recordables.add(
                        new RecEntityInvisible(
                                entityId,
                                metadata.isInvisible()
                        )
                );
            }

            if (metadata.isHasGlowingEffect() != before.isHasGlowingEffect()) {
                recordables.add(
                        new RecEntityGlowing(
                                entityId,
                                metadata.isHasGlowingEffect()
                        )
                );
            }
        }

        if (entries.remove(CUSTOM_NAME_INDEX) != null) {
            if (!Objects.equals(metadata.getCustomName(), before.getCustomName())) {
                recordables.add(
                        new RecEntityCustomName(
                                entityId,
                                metadata.getCustomName()
                        )
                );
            }
        }

        if (entries.remove(CUSTOM_NAME_VISIBLE_INDEX) != null) {
            if (metadata.isCustomNameVisible() != before.isCustomNameVisible()) {
                recordables.add(
                        new RecEntityCustomNameVisible(
                                entityId,
                                metadata.isCustomNameVisible()
                        )
                );
            }
        }

        Metadata.Entry<?> entry;
        if ((entry = entries.remove(POSE_INDEX)) != null) {
            Pose pose = (Pose) entry.value();

            if (pose != Pose.SNEAKING && pose != Pose.SWIMMING && metadata.getPose() != before.getPose()) {
                recordables.add(
                        new RecEntityPose(
                                entityId,
                                pose
                        )
                );
            }
        }

        return recordables;
    }

    @Override
    public Collection<Integer> skippedIndexes() {
        Collection<Integer> indexes = new HashSet<>(Set.of(AIR_TICKS_INDEX, SILENT_INDEX, NO_GRAVITY_INDEX));

        if (ProtocolVersion.getServerVersion().isHigher(ProtocolVersion.MINECRAFT_1_16_5)) {
            indexes.add(TICKS_FROZEN_INDEX);
        }

        return indexes;
    }
}