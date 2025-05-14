package mc.replay.nms.entity.metadata.other;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class GlowItemFrameMetadata extends ItemFrameMetadata {

    public static final int OFFSET = ItemFrameMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public GlowItemFrameMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}