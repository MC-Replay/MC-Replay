package mc.replay.nms.entity.metadata.animal;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FoxMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 4;

    public static final int TYPE_INDEX = OFFSET;
    public static final int MASK_INDEX = OFFSET + 1;
    public static final int FIRST_UUID_INDEX = OFFSET + 2;
    public static final int SECOND_UUID_INDEX = OFFSET + 3;

    public static final byte SITTING_BIT = 0x01;
    public static final byte CROUCHING_BIT = 0x04;
    public static final byte INTERESTED_BIT = 0x08;
    public static final byte POUNCING_BIT = 0x10;
    public static final byte SLEEPING_BIT = 0x20;
    public static final byte FACEPLANTED_BIT = 0x40;
    public static final byte DEFENDING_BIT = (byte) 0x80;

    public FoxMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setType(@NotNull Type type) {
        super.metadata.setIndex(TYPE_INDEX, Metadata.VarInt(type.ordinal()));
    }

    public void setSitting(boolean value) {
        this.setMaskBit(MASK_INDEX, SITTING_BIT, value);
    }

    public void setFoxSneaking(boolean value) {
        this.setMaskBit(MASK_INDEX, CROUCHING_BIT, value);
    }

    public void setInterested(boolean value) {
        this.setMaskBit(MASK_INDEX, INTERESTED_BIT, value);
    }

    public void setPouncing(boolean value) {
        this.setMaskBit(MASK_INDEX, POUNCING_BIT, value);
    }

    public void setSleeping(boolean value) {
        this.setMaskBit(MASK_INDEX, SLEEPING_BIT, value);
    }

    public void setFaceplanted(boolean value) {
        this.setMaskBit(MASK_INDEX, FACEPLANTED_BIT, value);
    }

    public void setDefending(boolean value) {
        this.setMaskBit(MASK_INDEX, DEFENDING_BIT, value);
    }

    public void setFirstUuid(@Nullable UUID value) {
        super.metadata.setIndex(FIRST_UUID_INDEX, Metadata.OptUUID(value));
    }

    public void setSecondUuid(@Nullable UUID value) {
        super.metadata.setIndex(SECOND_UUID_INDEX, Metadata.OptUUID(value));
    }

    public @NotNull Type getType() {
        return Type.VALUES[super.metadata.getIndex(TYPE_INDEX, 0)];
    }

    public boolean isSitting() {
        return this.getMaskBit(MASK_INDEX, SITTING_BIT);
    }

    public boolean isFoxSneaking() {
        return this.getMaskBit(MASK_INDEX, CROUCHING_BIT);
    }

    public boolean isInterested() {
        return this.getMaskBit(MASK_INDEX, INTERESTED_BIT);
    }

    public boolean isPouncing() {
        return this.getMaskBit(MASK_INDEX, POUNCING_BIT);
    }

    public boolean isSleeping() {
        return this.getMaskBit(MASK_INDEX, SLEEPING_BIT);
    }

    public boolean isFaceplanted() {
        return this.getMaskBit(MASK_INDEX, FACEPLANTED_BIT);
    }

    public boolean isDefending() {
        return this.getMaskBit(MASK_INDEX, DEFENDING_BIT);
    }

    public @Nullable UUID getFirstUuid() {
        return super.metadata.getIndex(FIRST_UUID_INDEX, null);
    }

    public @Nullable UUID getSecondUuid() {
        return super.metadata.getIndex(SECOND_UUID_INDEX, null);
    }

    public enum Type {

        RED,
        SNOW;

        private final static Type[] VALUES = values();
    }
}