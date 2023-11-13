package mc.replay.recording.dispatcher.helpers.metadata.other;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.other.RecArmorStandArms;
import mc.replay.common.recordables.types.entity.metadata.other.RecArmorStandBasePlate;
import mc.replay.common.recordables.types.entity.metadata.other.RecArmorStandRotation;
import mc.replay.common.recordables.types.entity.metadata.other.RecArmorStandSmall;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.other.ArmorStandMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.other.ArmorStandMetadata.*;

public final class ArmorStandMetadataReader implements MetadataReader<ArmorStandMetadata> {

    @Override
    public List<Recordable> read(ArmorStandMetadata before, ArmorStandMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(MASK_INDEX) != null) {
            if (metadata.isSmall() != before.isSmall()) {
                recordables.add(
                        new RecArmorStandSmall(
                                entityId,
                                metadata.isSmall()
                        )
                );
            }

            if (metadata.hasArms() != before.hasArms()) {
                recordables.add(
                        new RecArmorStandArms(
                                entityId,
                                metadata.hasArms()
                        )
                );
            }

            if (metadata.hasBasePlate() != before.hasBasePlate()) {
                recordables.add(
                        new RecArmorStandBasePlate(
                                entityId,
                                metadata.hasBasePlate()
                        )
                );
            }
        }

        if (entries.remove(HEAD_ROTATION_INDEX) != null) {
            if (metadata.getHeadRotation() != before.getHeadRotation()) {
                recordables.add(
                        new RecArmorStandRotation(
                                entityId,
                                RecArmorStandRotation.RotationType.HEAD,
                                metadata.getHeadRotation()
                        )
                );
            }
        }

        if (entries.remove(BODY_ROTATION_INDEX) != null) {
            if (metadata.getBodyRotation() != before.getBodyRotation()) {
                recordables.add(
                        new RecArmorStandRotation(
                                entityId,
                                RecArmorStandRotation.RotationType.BODY,
                                metadata.getBodyRotation()
                        )
                );
            }
        }

        if (entries.remove(LEFT_ARM_ROTATION_INDEX) != null) {
            if (metadata.getLeftArmRotation() != before.getLeftArmRotation()) {
                recordables.add(
                        new RecArmorStandRotation(
                                entityId,
                                RecArmorStandRotation.RotationType.LEFT_ARM,
                                metadata.getLeftArmRotation()
                        )
                );
            }
        }

        if (entries.remove(RIGHT_ARM_ROTATION_INDEX) != null) {
            if (metadata.getRightArmRotation() != before.getRightArmRotation()) {
                recordables.add(
                        new RecArmorStandRotation(
                                entityId,
                                RecArmorStandRotation.RotationType.RIGHT_ARM,
                                metadata.getRightArmRotation()
                        )
                );
            }
        }

        if (entries.remove(LEFT_LEG_ROTATION_INDEX) != null) {
            if (metadata.getLeftLegRotation() != before.getLeftLegRotation()) {
                recordables.add(
                        new RecArmorStandRotation(
                                entityId,
                                RecArmorStandRotation.RotationType.LEFT_LEG,
                                metadata.getLeftLegRotation()
                        )
                );
            }
        }

        if (entries.remove(RIGHT_LEG_ROTATION_INDEX) != null) {
            if (metadata.getRightLegRotation() != before.getRightLegRotation()) {
                recordables.add(
                        new RecArmorStandRotation(
                                entityId,
                                RecArmorStandRotation.RotationType.RIGHT_LEG,
                                metadata.getRightLegRotation()
                        )
                );
            }
        }

        return recordables;
    }
}