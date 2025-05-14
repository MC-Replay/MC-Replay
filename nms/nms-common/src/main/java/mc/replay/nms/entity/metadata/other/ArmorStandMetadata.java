package mc.replay.nms.entity.metadata.other;

import mc.replay.nms.entity.metadata.LivingEntityMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class ArmorStandMetadata extends LivingEntityMetadata {

    public static final int OFFSET = LivingEntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 7;

    public static final int MASK_INDEX = OFFSET;
    public static final int HEAD_ROTATION_INDEX = OFFSET + 1;
    public static final int BODY_ROTATION_INDEX = OFFSET + 2;
    public static final int LEFT_ARM_ROTATION_INDEX = OFFSET + 3;
    public static final int RIGHT_ARM_ROTATION_INDEX = OFFSET + 4;
    public static final int LEFT_LEG_ROTATION_INDEX = OFFSET + 5;
    public static final int RIGHT_LEG_ROTATION_INDEX = OFFSET + 6;

    public static final byte IS_SMALL_BIT = 0x01;
    public static final byte HAS_ARMS_BIT = 0x04;
    public static final byte HAS_NO_BASE_PLATE_BIT = 0x08;
    public static final byte IS_MARKER_BIT = 0x10;

    public ArmorStandMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setSmall(boolean value) {
        this.setMaskBit(MASK_INDEX, IS_SMALL_BIT, value);
    }

    public void setHasArms(boolean value) {
        this.setMaskBit(MASK_INDEX, HAS_ARMS_BIT, value);
    }

    public void setHasBasePlate(boolean value) {
        this.setMaskBit(MASK_INDEX, HAS_NO_BASE_PLATE_BIT, !value);
    }

    public void setMarker(boolean value) {
        this.setMaskBit(MASK_INDEX, IS_MARKER_BIT, value);
    }

    public void setHeadRotation(@NotNull Vector value) {
        super.metadata.setIndex(HEAD_ROTATION_INDEX, Metadata.Rotation(value));
    }

    public void setBodyRotation(@NotNull Vector value) {
        super.metadata.setIndex(BODY_ROTATION_INDEX, Metadata.Rotation(value));
    }

    public void setLeftArmRotation(@NotNull Vector value) {
        super.metadata.setIndex(LEFT_ARM_ROTATION_INDEX, Metadata.Rotation(value));
    }

    public void setRightArmRotation(@NotNull Vector value) {
        super.metadata.setIndex(RIGHT_ARM_ROTATION_INDEX, Metadata.Rotation(value));
    }

    public void setLeftLegRotation(@NotNull Vector value) {
        super.metadata.setIndex(LEFT_LEG_ROTATION_INDEX, Metadata.Rotation(value));
    }

    public void setRightLegRotation(@NotNull Vector value) {
        super.metadata.setIndex(RIGHT_LEG_ROTATION_INDEX, Metadata.Rotation(value));
    }

    public boolean isSmall() {
        return this.getMaskBit(MASK_INDEX, IS_SMALL_BIT);
    }

    public boolean hasArms() {
        return this.getMaskBit(MASK_INDEX, HAS_ARMS_BIT);
    }

    public boolean hasBasePlate() {
        return !this.getMaskBit(MASK_INDEX, HAS_NO_BASE_PLATE_BIT);
    }

    public boolean isMarker() {
        return this.getMaskBit(MASK_INDEX, IS_MARKER_BIT);
    }

    public @NotNull Vector getHeadRotation() {
        return super.metadata.getIndex(HEAD_ROTATION_INDEX, new Vector(0, 0, 0));
    }

    public @NotNull Vector getBodyRotation() {
        return super.metadata.getIndex(BODY_ROTATION_INDEX, new Vector(0, 0, 0));
    }

    public @NotNull Vector getLeftArmRotation() {
        return super.metadata.getIndex(LEFT_ARM_ROTATION_INDEX, new Vector(-10D, 0D, -10D));
    }

    public @NotNull Vector getRightArmRotation() {
        return super.metadata.getIndex(RIGHT_ARM_ROTATION_INDEX, new Vector(-15D, 0D, 10D));
    }

    public @NotNull Vector getLeftLegRotation() {
        return super.metadata.getIndex(LEFT_LEG_ROTATION_INDEX, new Vector(-1D, 0D, -1D));
    }

    public @NotNull Vector getRightLegRotation() {
        return super.metadata.getIndex(RIGHT_LEG_ROTATION_INDEX, new Vector(1D, 0D, 1D));
    }
}