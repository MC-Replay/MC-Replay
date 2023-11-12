package mc.replay.nms.entity.metadata.other;

import mc.replay.nms.entity.metadata.EntityMetadata;
import mc.replay.nms.entity.metadata.ObjectDataProvider;
import mc.replay.nms.inventory.RItem;
import mc.replay.packetlib.data.Item;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.Rotation;
import org.jetbrains.annotations.NotNull;

public class ItemFrameMetadata extends EntityMetadata implements ObjectDataProvider {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 2;

    public static final int ITEM_INDEX = OFFSET;
    public static final int ROTATION_INDEX = OFFSET + 1;

    private Orientation orientation = Orientation.DOWN;

    public ItemFrameMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setItem(@NotNull RItem value) {
        super.metadata.setIndex(ITEM_INDEX, Metadata.Slot(value));
    }

    public void setRotation(@NotNull Rotation value) {
        super.metadata.setIndex(ROTATION_INDEX, Metadata.VarInt(value.ordinal()));
    }

    public void setOrientation(@NotNull Orientation value) {
        this.orientation = value;
    }

    public @NotNull RItem getItem() {
        return super.metadata.getIndex(ITEM_INDEX, new RItem(Item.AIR));
    }

    public @NotNull Rotation getRotation() {
        return Rotation.values()[super.metadata.getIndex(ROTATION_INDEX, 0)];
    }

    public @NotNull Orientation getOrientation() {
        return this.orientation;
    }

    @Override
    public int getObjectData() {
        return this.orientation.ordinal();
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return false;
    }

    public enum Orientation {

        DOWN,
        UP,
        NORTH,
        SOUTH,
        WEST,
        EAST
    }
}