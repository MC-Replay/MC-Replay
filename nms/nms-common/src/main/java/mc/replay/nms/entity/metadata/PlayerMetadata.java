package mc.replay.nms.entity.metadata;

import com.github.steveice10.opennbt.tag.builtin.Tag;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerMetadata extends LivingEntityMetadata {

    public static final int OFFSET = LivingEntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int ADDITIONAL_HEARTS_INDEX = OFFSET;
    public static final int SCORE_INDEX = OFFSET + 1;
    public static final int DISPLAYED_SKIN_PARTS_INDEX = OFFSET + 2;
    public static final int MAIN_HAND_INDEX = OFFSET + 3;
    public static final int LEFT_SHOULDER_INDEX = OFFSET + 4;
    public static final int RIGHT_SHOULDER_INDEX = OFFSET + 5;

    public final static byte CAPE_BIT = 0x01;
    public final static byte JACKET_BIT = 0x02;
    public final static byte LEFT_SLEEVE_BIT = 0x04;
    public final static byte RIGHT_SLEEVE_BIT = 0x08;
    public final static byte LEFT_LEG_BIT = 0x10;
    public final static byte RIGHT_LEG_BIT = 0x20;
    public final static byte HAT_BIT = 0x40;

    public PlayerMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setAdditionalHearts(float value) {
        super.metadata.setIndex(ADDITIONAL_HEARTS_INDEX, Metadata.Float(value));
    }

    public void setScore(int value) {
        super.metadata.setIndex(SCORE_INDEX, Metadata.VarInt(value));
    }

    public void setCapeEnabled(boolean value) {
        this.setMaskBit(DISPLAYED_SKIN_PARTS_INDEX, CAPE_BIT, value);
    }

    public void setJacketEnabled(boolean value) {
        this.setMaskBit(DISPLAYED_SKIN_PARTS_INDEX, JACKET_BIT, value);
    }

    public void setLeftSleeveEnabled(boolean value) {
        this.setMaskBit(DISPLAYED_SKIN_PARTS_INDEX, LEFT_SLEEVE_BIT, value);
    }

    public void setRightSleeveEnabled(boolean value) {
        this.setMaskBit(DISPLAYED_SKIN_PARTS_INDEX, RIGHT_SLEEVE_BIT, value);
    }

    public void setLeftLegEnabled(boolean value) {
        this.setMaskBit(DISPLAYED_SKIN_PARTS_INDEX, LEFT_LEG_BIT, value);
    }

    public void setRightLegEnabled(boolean value) {
        this.setMaskBit(DISPLAYED_SKIN_PARTS_INDEX, RIGHT_LEG_BIT, value);
    }

    public void setHatEnabled(boolean value) {
        this.setMaskBit(DISPLAYED_SKIN_PARTS_INDEX, HAT_BIT, value);
    }

    public void setRightMainHand(boolean value) {
        super.metadata.setIndex(MAIN_HAND_INDEX, Metadata.Byte((value) ? (byte) 1 : (byte) 0));
    }

    public void setLeftShouldEntityData(@Nullable Tag value) {
        super.metadata.setIndex(LEFT_SHOULDER_INDEX, Metadata.NBT(value));
    }

    public void setRightShouldEntityData(@Nullable Tag value) {
        super.metadata.setIndex(RIGHT_SHOULDER_INDEX, Metadata.NBT(value));
    }

    public float getAdditionalHearts() {
        return super.metadata.getIndex(ADDITIONAL_HEARTS_INDEX, 0);
    }

    public int getScore() {
        return super.metadata.getIndex(SCORE_INDEX, 0);
    }

    public boolean isCapeEnabled() {
        return this.getMaskBit(DISPLAYED_SKIN_PARTS_INDEX, CAPE_BIT);
    }

    public boolean isJacketEnabled() {
        return this.getMaskBit(DISPLAYED_SKIN_PARTS_INDEX, JACKET_BIT);
    }

    public boolean isLeftSleeveEnabled() {
        return this.getMaskBit(DISPLAYED_SKIN_PARTS_INDEX, LEFT_SLEEVE_BIT);
    }

    public boolean isRightSleeveEnabled() {
        return this.getMaskBit(DISPLAYED_SKIN_PARTS_INDEX, RIGHT_SLEEVE_BIT);
    }

    public boolean isLeftLegEnabled() {
        return this.getMaskBit(DISPLAYED_SKIN_PARTS_INDEX, LEFT_LEG_BIT);
    }

    public boolean isRightLegEnabled() {
        return this.getMaskBit(DISPLAYED_SKIN_PARTS_INDEX, RIGHT_LEG_BIT);
    }

    public boolean isHatEnabled() {
        return this.getMaskBit(DISPLAYED_SKIN_PARTS_INDEX, HAT_BIT);
    }

    public boolean isRightMainHand() {
        return super.metadata.getIndex(MAIN_HAND_INDEX, (byte) 1) == (byte) 1;
    }

    public @Nullable Tag getLeftShoulderEntityData() {
        return super.metadata.getIndex(LEFT_SHOULDER_INDEX, null);
    }

    public @Nullable Tag getRightShoulderEntityData() {
        return super.metadata.getIndex(RIGHT_SHOULDER_INDEX, null);
    }
}