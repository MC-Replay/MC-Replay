package mc.replay.nms.entity.metadata.other;

import mc.replay.nms.entity.metadata.EntityMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class BoatMetadata extends EntityMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 7;

    public static final int TIME_SINCE_LAST_HIT_INDEX = OFFSET;
    public static final int FORWARD_DIRECTION_INDEX = OFFSET + 1;
    public static final int DAMAGE_TAKEN_INDEX = OFFSET + 2;
    public static final int TYPE_INDEX = OFFSET + 3;
    public static final int LEFT_PADDLE_TURNING_INDEX = OFFSET + 4;
    public static final int RIGHT_PADDLE_TURNING_INDEX = OFFSET + 5;
    public static final int SPLASH_TIMER_INDEX = OFFSET + 6;

    public BoatMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setTimeSinceLastHit(int value) {
        super.metadata.setIndex(TIME_SINCE_LAST_HIT_INDEX, Metadata.VarInt(value));
    }

    public void setForwardDirection(int value) {
        super.metadata.setIndex(FORWARD_DIRECTION_INDEX, Metadata.VarInt(value));
    }

    public void setDamageTaken(float value) {
        super.metadata.setIndex(DAMAGE_TAKEN_INDEX, Metadata.Float(value));
    }

    public void setType(@NotNull Type value) {
        super.metadata.setIndex(TYPE_INDEX, Metadata.VarInt(value.ordinal()));
    }

    public void setLeftPaddleTurning(boolean value) {
        super.metadata.setIndex(LEFT_PADDLE_TURNING_INDEX, Metadata.Boolean(value));
    }

    public void setRightPaddleTurning(boolean value) {
        super.metadata.setIndex(RIGHT_PADDLE_TURNING_INDEX, Metadata.Boolean(value));
    }

    public void setSplashTimer(int value) {
        super.metadata.setIndex(SPLASH_TIMER_INDEX, Metadata.VarInt(value));
    }

    public int getTimeSinceLastHit() {
        return super.metadata.getIndex(TIME_SINCE_LAST_HIT_INDEX, 0);
    }

    public int getForwardDirection() {
        return super.metadata.getIndex(FORWARD_DIRECTION_INDEX, 1);
    }

    public float getDamageTaken() {
        return super.metadata.getIndex(DAMAGE_TAKEN_INDEX, 0);
    }

    public @NotNull Type getType() {
        return Type.VALUES[super.metadata.getIndex(TYPE_INDEX, 0)];
    }

    public boolean isLeftPaddleTurning() {
        return super.metadata.getIndex(LEFT_PADDLE_TURNING_INDEX, false);
    }

    public boolean isRightPaddleTurning() {
        return super.metadata.getIndex(RIGHT_PADDLE_TURNING_INDEX, false);
    }

    public int getSplashTimer() {
        return super.metadata.getIndex(SPLASH_TIMER_INDEX, 0);
    }

    public enum Type {

        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK;

        private static final Type[] VALUES = values();
    }
}