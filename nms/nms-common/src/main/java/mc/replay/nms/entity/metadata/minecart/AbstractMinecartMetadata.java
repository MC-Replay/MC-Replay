package mc.replay.nms.entity.metadata.minecart;

import mc.replay.nms.entity.metadata.EntityMetadata;
import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.nms.entity.metadata.ObjectDataProvider;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMinecartMetadata extends EntityMetadata implements ObjectDataProvider {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 6;

    public static final int SHAKING_POWER_INDEX = OFFSET;
    public static final int SHAKING_DIRECTION_INDEX = OFFSET + 1;
    public static final int SHAKING_MULTIPLIER_INDEX = OFFSET + 2;
    public static final int CUSTOM_BLOCK_ID_AND_DAMAGE_INDEX = OFFSET + 3;
    public static final int CUSTOM_BLOCK_Y_POSITION_INDEX = OFFSET + 4;

    protected AbstractMinecartMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setShakingPower(int value) {
        super.metadata.setIndex(SHAKING_POWER_INDEX, MetadataTypes.VarInt(value));
    }

    public void setShakingDirection(int value) {
        super.metadata.setIndex(SHAKING_DIRECTION_INDEX, MetadataTypes.VarInt(value));
    }

    public void setShakingMultiplier(float value) {
        super.metadata.setIndex(SHAKING_MULTIPLIER_INDEX, MetadataTypes.Float(value));
    }

    public void setCustomBlockIdAndDamage(int value) {
        super.metadata.setIndex(CUSTOM_BLOCK_ID_AND_DAMAGE_INDEX, MetadataTypes.VarInt(value));
    }

    public void setCustomBlockYPosition(int value) {
        super.metadata.setIndex(CUSTOM_BLOCK_Y_POSITION_INDEX, MetadataTypes.VarInt(value));
    }

    public int getShakingPower() {
        return super.metadata.getIndex(SHAKING_POWER_INDEX, 0);
    }

    public int getShakingDirection() {
        return super.metadata.getIndex(SHAKING_DIRECTION_INDEX, 1);
    }

    public float getShakingMultiplier() {
        return super.metadata.getIndex(SHAKING_MULTIPLIER_INDEX, 0F);
    }

    public int getCustomBlockIdAndDamage() {
        return super.metadata.getIndex(CUSTOM_BLOCK_ID_AND_DAMAGE_INDEX, 0);
    }

    public int getCustomBlockYPosition() {
        return super.metadata.getIndex(CUSTOM_BLOCK_Y_POSITION_INDEX, 6);
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }
}