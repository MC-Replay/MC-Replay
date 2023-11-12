package mc.replay.nms.entity.metadata;

import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.utils.ProtocolVersion;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Pose;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class EntityMetadata {

    public static final int OFFSET = 0;
    public static final int MAX_OFFSET = OFFSET + getMaxOffset();

    private static int getMaxOffset() {
        return (ProtocolVersion.getServerVersion().isEqual(ProtocolVersion.MINECRAFT_1_16_5)) ? 7 : 8;
    }

    public static final int MASK_INDEX = OFFSET;
    public static final int AIR_TICKS_INDEX = OFFSET + 1;
    public static final int CUSTOM_NAME_INDEX = OFFSET + 2;
    public static final int CUSTOM_NAME_VISIBLE_INDEX = OFFSET + 3;
    public static final int SILENT_INDEX = OFFSET + 4;
    public static final int NO_GRAVITY_INDEX = OFFSET + 5;
    public static final int POSE_INDEX = OFFSET + 6;
    public static final int TICKS_FROZEN_INDEX = OFFSET + 7;

    public static final byte ON_FIRE_BIT = 0x01;
    public static final byte CROUCHING_BIT = 0x02;
    public static final byte SPRINTING_BIT = 0x08;
    public static final byte SWIMMING_BIT = 0x10;
    public static final byte INVISIBLE_BIT = 0x20;
    public static final byte HAS_GLOWING_EFFECT_BIT = 0x40;
    public static final byte FLYING_WITH_ELYTRA_BIT = (byte) 0x80;

    protected final Metadata metadata;

    public EntityMetadata(@NotNull Metadata metadata) {
        this.metadata = metadata;
    }

    public @NotNull Metadata getMetadata() {
        return this.metadata;
    }

    public @NotNull Map<Integer, Metadata.Entry<?>> getEntries() {
        return this.metadata.getEntries();
    }

    public void setOnFire(boolean value) {
        this.setMaskBit(MASK_INDEX, ON_FIRE_BIT, value);
    }

    public void setSneaking(boolean value) {
        this.setMaskBit(MASK_INDEX, CROUCHING_BIT, value);
    }

    public void setSprinting(boolean value) {
        this.setMaskBit(MASK_INDEX, SPRINTING_BIT, value);
    }

    public void setSwimming(boolean value) {
        this.setMaskBit(MASK_INDEX, SWIMMING_BIT, value);
    }

    public void setInvisible(boolean value) {
        this.setMaskBit(MASK_INDEX, INVISIBLE_BIT, value);
    }

    public void setHasGlowingEffect(boolean value) {
        this.setMaskBit(MASK_INDEX, HAS_GLOWING_EFFECT_BIT, value);
    }

    public void setIsFlyingWithElytra(boolean value) {
        this.setMaskBit(MASK_INDEX, FLYING_WITH_ELYTRA_BIT, value);
    }

    public void setAirTicks(int value) {
        this.metadata.setIndex(AIR_TICKS_INDEX, Metadata.VarInt(value));
    }

    public void setCustomName(@NotNull Component value) {
        this.metadata.setIndex(CUSTOM_NAME_INDEX, Metadata.Chat(value));
    }

    public void setCustomNameVisible(boolean value) {
        this.metadata.setIndex(CUSTOM_NAME_VISIBLE_INDEX, Metadata.Boolean(value));
    }

    public void setSilent(boolean value) {
        this.metadata.setIndex(SILENT_INDEX, Metadata.Boolean(value));
    }

    public void setHasGravity(boolean value) {
        this.metadata.setIndex(NO_GRAVITY_INDEX, Metadata.Boolean(!value));
    }

    public void setPose(@NotNull Pose value) {
        this.metadata.setIndex(POSE_INDEX, Metadata.Pose(value));
    }

    public void setTickFrozen(int value) {
        if (ProtocolVersion.getServerVersion().isEqual(ProtocolVersion.MINECRAFT_1_16_5)) return;
        this.metadata.setIndex(TICKS_FROZEN_INDEX, Metadata.VarInt(value));
    }

    public boolean isOnFire() {
        return this.getMaskBit(MASK_INDEX, ON_FIRE_BIT);
    }

    public boolean isSneaking() {
        return this.getMaskBit(MASK_INDEX, CROUCHING_BIT);
    }

    public boolean isSprinting() {
        return this.getMaskBit(MASK_INDEX, SPRINTING_BIT);
    }

    public boolean isSwimming() {
        return this.getMaskBit(MASK_INDEX, SWIMMING_BIT);
    }

    public boolean isInvisible() {
        return this.getMaskBit(MASK_INDEX, INVISIBLE_BIT);
    }

    public boolean isHasGlowingEffect() {
        return this.getMaskBit(MASK_INDEX, HAS_GLOWING_EFFECT_BIT);
    }

    public boolean isFlyingWithElytra() {
        return this.getMaskBit(MASK_INDEX, FLYING_WITH_ELYTRA_BIT);
    }

    public int getAirTicks() {
        return this.metadata.getIndex(AIR_TICKS_INDEX, 300);
    }

    public @NotNull Component getCustomName() {
        return this.metadata.getIndex(CUSTOM_NAME_INDEX, Component.empty());
    }

    public boolean isCustomNameVisible() {
        return this.metadata.getIndex(CUSTOM_NAME_VISIBLE_INDEX, false);
    }

    public boolean isSilent() {
        return this.metadata.getIndex(SILENT_INDEX, false);
    }

    public boolean hasGravity() {
        return !this.metadata.getIndex(NO_GRAVITY_INDEX, false);
    }

    public @NotNull Pose getPose() {
        return this.metadata.getIndex(POSE_INDEX, Pose.STANDING);
    }

    public int getTickFrozen() {
        if (ProtocolVersion.getServerVersion().isEqual(ProtocolVersion.MINECRAFT_1_16_5)) return 0;
        return this.metadata.getIndex(TICKS_FROZEN_INDEX, 0);
    }

    protected byte getMask(int index) {
        return this.metadata.getIndex(index, (byte) 0);
    }

    protected void setMask(int index, byte mask) {
        this.metadata.setIndex(index, Metadata.Byte(mask));
    }

    protected boolean getMaskBit(int index, byte bit) {
        return (this.getMask(index) & bit) == bit;
    }

    protected void setMaskBit(int index, byte bit, boolean value) {
        byte mask = this.getMask(index);
        boolean currentValue = (mask & bit) == bit;
        if (currentValue == value) return;

        if (value) {
            mask |= bit;
        } else {
            mask &= ~bit;
        }

        this.setMask(index, mask);
    }
}