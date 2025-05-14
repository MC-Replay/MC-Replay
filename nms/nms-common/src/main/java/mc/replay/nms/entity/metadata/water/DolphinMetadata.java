package mc.replay.nms.entity.metadata.water;

import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class DolphinMetadata extends WaterAnimalMetadata {

    public static final int OFFSET = WaterAnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 3;

    public static final int TREASURE_POSITION_INDEX = OFFSET;
    public static final int CAN_FIND_TREASURE_INDEX = OFFSET + 1;
    public static final int HAS_FISH_INDEX = OFFSET + 2;

    public DolphinMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setTreasurePosition(@NotNull Vector value) {
        super.metadata.setIndex(TREASURE_POSITION_INDEX, Metadata.Position(value));
    }

    public void setCanFindTreasure(boolean value) {
        super.metadata.setIndex(CAN_FIND_TREASURE_INDEX, Metadata.Boolean(value));
    }

    public void setHasFish(boolean value) {
        super.metadata.setIndex(HAS_FISH_INDEX, Metadata.Boolean(value));
    }

    public @NotNull Vector getTreasurePosition() {
        return super.metadata.getIndex(TREASURE_POSITION_INDEX, new Vector(0, 0, 0));
    }

    public boolean canFindTreasure() {
        return super.metadata.getIndex(CAN_FIND_TREASURE_INDEX, false);
    }

    public boolean hasFish() {
        return super.metadata.getIndex(HAS_FISH_INDEX, false);
    }
}