package mc.replay.nms.entity.metadata.animal;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class TurtleMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 6;

    public static final int HOME_POSITION_INDEX = OFFSET;
    public static final int HAS_EGG_INDEX = OFFSET + 1;
    public static final int LAYING_EGG_INDEX = OFFSET + 2;
    public static final int TRAVEL_POSITION_INDEX = OFFSET + 3;
    public static final int GOING_HOME_INDEX = OFFSET + 4;
    public static final int TRAVELLING_INDEX = OFFSET + 5;

    public TurtleMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setHomePosition(@NotNull Vector value) {
        super.metadata.setIndex(HOME_POSITION_INDEX, MetadataTypes.Position(value));
    }

    public void setHasEgg(boolean value) {
        super.metadata.setIndex(HAS_EGG_INDEX, MetadataTypes.Boolean(value));
    }

    public void setLayingEgg(boolean value) {
        super.metadata.setIndex(LAYING_EGG_INDEX, MetadataTypes.Boolean(value));
    }

    public void setTravelPosition(@NotNull Vector value) {
        super.metadata.setIndex(TRAVEL_POSITION_INDEX, MetadataTypes.Position(value));
    }

    public void setGoingHome(boolean value) {
        super.metadata.setIndex(GOING_HOME_INDEX, MetadataTypes.Boolean(value));
    }

    public void setTravelling(boolean value) {
        super.metadata.setIndex(TRAVELLING_INDEX, MetadataTypes.Boolean(value));
    }

    public @NotNull Vector getHomePosition() {
        return super.metadata.getIndex(HOME_POSITION_INDEX, new Vector(0, 0, 0));
    }

    public boolean isHasEgg() {
        return super.metadata.getIndex(HAS_EGG_INDEX, false);
    }

    public boolean isLayingEgg() {
        return super.metadata.getIndex(LAYING_EGG_INDEX, false);
    }

    public @NotNull Vector getTravelPosition() {
        return super.metadata.getIndex(TRAVEL_POSITION_INDEX, new Vector(0, 0, 0));
    }

    public boolean isGoingHome() {
        return super.metadata.getIndex(GOING_HOME_INDEX, false);
    }

    public boolean isTravelling() {
        return super.metadata.getIndex(TRAVELLING_INDEX, false);
    }
}