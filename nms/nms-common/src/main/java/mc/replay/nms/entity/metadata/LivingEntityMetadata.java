package mc.replay.nms.entity.metadata;

import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.data.entity.PlayerHand;
import mc.replay.packetlib.utils.ProtocolVersion;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LivingEntityMetadata extends EntityMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 7;

    public static final int HAND_STATES_MASK_INDEX = OFFSET;
    public static final int HEALTH_INDEX = OFFSET + 1;
    public static final int POTION_EFFECT_COLOR_INDEX = OFFSET + 2;
    public static final int POTION_EFFECT_AMBIENT_INDEX = OFFSET + 3;
    public static final int ARROW_COUNT_INDEX = OFFSET + 4;
    public static final int ABSORPTION_HEARTS_INDEX = OFFSET + 5;
    public static final int BEE_STINGER_COUNT = OFFSET + 5;
    public static final int BED_POSITION_INDEX = OFFSET + 6;

    public static final byte IS_HAND_ACTIVE_BIT = 0x01;
    public static final byte ACTIVE_HAND_BIT = 0x02;
    public static final byte IS_IN_SPIN_ATTACK_BIT = 0x04;

    public LivingEntityMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setHandActive(boolean value) {
        this.setMaskBit(HAND_STATES_MASK_INDEX, IS_HAND_ACTIVE_BIT, value);
    }

    public void setActiveHand(@NotNull PlayerHand hand) {
        this.setMaskBit(HAND_STATES_MASK_INDEX, ACTIVE_HAND_BIT, hand == PlayerHand.OFF_HAND);
    }

    public void setInRiptideSpinAttack(boolean value) {
        this.setMaskBit(HAND_STATES_MASK_INDEX, IS_IN_SPIN_ATTACK_BIT, value);
    }

    public void setHealth(float value) {
        super.metadata.setIndex(HEALTH_INDEX, MetadataTypes.Float(value));
    }

    public void setPotionEffectColor(int value) {
        super.metadata.setIndex(POTION_EFFECT_COLOR_INDEX, MetadataTypes.VarInt(value));
    }

    public void setPotionEffectAmbient(boolean value) {
        super.metadata.setIndex(POTION_EFFECT_AMBIENT_INDEX, MetadataTypes.Boolean(value));
    }

    public void setArrowCount(int value) {
        super.metadata.setIndex(ARROW_COUNT_INDEX, MetadataTypes.VarInt(value));
    }

    public void setAbsorptionHearts1165(int value) {
        if (!ProtocolVersion.getServerVersion().isEqual(ProtocolVersion.MINECRAFT_1_16_5)) return;
        super.metadata.setIndex(ABSORPTION_HEARTS_INDEX, MetadataTypes.VarInt(value));
    }

    public void setBeeStingerCount(int value) {
        if (ProtocolVersion.getServerVersion().isEqual(ProtocolVersion.MINECRAFT_1_16_5)) return;
        super.metadata.setIndex(BEE_STINGER_COUNT, MetadataTypes.VarInt(value));
    }

    public void setBedInWhichSleepingPosition(@Nullable Vector value) {
        super.metadata.setIndex(BED_POSITION_INDEX, MetadataTypes.OptPosition(value));
    }

    public boolean isHandActive() {
        return this.getMaskBit(HAND_STATES_MASK_INDEX, IS_HAND_ACTIVE_BIT);
    }

    public @NotNull PlayerHand getActiveHand() {
        return this.getMaskBit(HAND_STATES_MASK_INDEX, ACTIVE_HAND_BIT) ? PlayerHand.OFF_HAND : PlayerHand.MAIN_HAND;
    }

    public boolean isInRiptideSpinAttack() {
        return this.getMaskBit(HAND_STATES_MASK_INDEX, IS_IN_SPIN_ATTACK_BIT);
    }

    public float getHealth() {
        return super.metadata.getIndex(HEALTH_INDEX, 1f);
    }

    public int getPotionEffectColor() {
        return super.metadata.getIndex(POTION_EFFECT_COLOR_INDEX, 0);
    }

    public boolean isPotionEffectAmbient() {
        return super.metadata.getIndex(POTION_EFFECT_AMBIENT_INDEX, false);
    }

    public int getArrowCount() {
        return super.metadata.getIndex(ARROW_COUNT_INDEX, 0);
    }

    public int getAbsorptionHearts1165() {
        if (!ProtocolVersion.getServerVersion().isEqual(ProtocolVersion.MINECRAFT_1_16_5)) return 0;
        return super.metadata.getIndex(ABSORPTION_HEARTS_INDEX, 0);
    }

    public int getBeeStingerCount() {
        if (ProtocolVersion.getServerVersion().isEqual(ProtocolVersion.MINECRAFT_1_16_5)) return 0;
        return super.metadata.getIndex(BEE_STINGER_COUNT, 0);
    }

    public @Nullable Vector getBedInWhichSleepingPosition() {
        return super.metadata.getIndex(BED_POSITION_INDEX, null);
    }
}