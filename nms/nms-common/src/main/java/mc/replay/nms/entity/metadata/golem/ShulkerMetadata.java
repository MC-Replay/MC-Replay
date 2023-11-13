package mc.replay.nms.entity.metadata.golem;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShulkerMetadata extends AbstractGolemMetadata {

    public static final int OFFSET = AbstractGolemMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 4;

    public static final int ATTACH_FACE_INDEX = OFFSET;
    public static final int ATTACHMENT_POSITION_INDEX = OFFSET + 1;
    public static final int SHIELD_HEIGHT_INDEX = OFFSET + 2;
    public static final int COLOR_INDEX = OFFSET + 3;

    public ShulkerMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setAttachFace(@NotNull BlockFace value) {
        super.metadata.setIndex(ATTACH_FACE_INDEX, MetadataTypes.Direction(value));
    }

    public void setAttachmentPosition(Vector value) {
        super.metadata.setIndex(ATTACHMENT_POSITION_INDEX, MetadataTypes.OptPosition(value));
    }

    public void setShieldHeight(byte value) {
        super.metadata.setIndex(SHIELD_HEIGHT_INDEX, MetadataTypes.Byte(value));
    }

    public void setColor(byte value) {
        super.metadata.setIndex(COLOR_INDEX, MetadataTypes.Byte(value));
    }

    public @NotNull BlockFace getAttachFace() {
        return super.metadata.getIndex(ATTACH_FACE_INDEX, BlockFace.DOWN);
    }

    public @Nullable Vector getAttachmentPosition() {
        return super.metadata.getIndex(ATTACHMENT_POSITION_INDEX, null);
    }

    public byte getShieldHeight() {
        return super.metadata.getIndex(SHIELD_HEIGHT_INDEX, (byte) 0);
    }

    public byte getColor() {
        return super.metadata.getIndex(COLOR_INDEX, (byte) 10);
    }
}