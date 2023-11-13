package mc.replay.nms.entity.metadata.animal;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class PigMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 2;

    public static final int HAS_SADDLE_INDEX = OFFSET;
    public static final int TIME_TO_BOOST_INDEX = OFFSET + 1;

    public PigMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setHasSaddle(boolean value) {
        super.metadata.setIndex(HAS_SADDLE_INDEX, MetadataTypes.Boolean(value));
    }

    public void setTimeToBoost(int value) {
        super.metadata.setIndex(TIME_TO_BOOST_INDEX, MetadataTypes.VarInt(value));
    }

    public boolean hasSaddle() {
        return super.metadata.getIndex(HAS_SADDLE_INDEX, false);
    }

    public int getTimeToBoost() {
        return super.metadata.getIndex(TIME_TO_BOOST_INDEX, 0);
    }
}