package mc.replay.nms.entity.metadata.animal;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class StriderMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 3;

    public static final int TIME_TO_BOOST_INDEX = OFFSET;
    public static final int SHAKING_INDEX = OFFSET + 1;
    public static final int HAS_SADDLE_INDEX = OFFSET + 2;

    public StriderMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setTimeToBoost(int value) {
        super.metadata.setIndex(TIME_TO_BOOST_INDEX, MetadataTypes.VarInt(value));
    }

    public void setShaking(boolean value) {
        super.metadata.setIndex(SHAKING_INDEX, MetadataTypes.Boolean(value));
    }

    public void setHasSaddle(boolean value) {
        super.metadata.setIndex(HAS_SADDLE_INDEX, MetadataTypes.Boolean(value));
    }

    public int getTimeToBoost() {
        return super.metadata.getIndex(TIME_TO_BOOST_INDEX, 0);
    }

    public boolean isShaking() {
        return super.metadata.getIndex(SHAKING_INDEX, false);
    }

    public boolean hasSaddle() {
        return super.metadata.getIndex(HAS_SADDLE_INDEX, false);
    }
}