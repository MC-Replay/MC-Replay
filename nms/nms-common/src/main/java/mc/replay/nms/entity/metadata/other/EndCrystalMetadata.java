package mc.replay.nms.entity.metadata.other;

import mc.replay.nms.entity.metadata.EntityMetadata;
import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EndCrystalMetadata extends EntityMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 2;

    public static final int BEAM_TARGET_INDEX = OFFSET;
    public static final int SHOWING_BOTTOM_INDEX = OFFSET + 1;

    public EndCrystalMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setBeamTarget(@Nullable Vector value) {
        super.metadata.setIndex(BEAM_TARGET_INDEX, MetadataTypes.OptPosition(value));
    }

    public void setShowingBottom(boolean value) {
        super.metadata.setIndex(SHOWING_BOTTOM_INDEX, MetadataTypes.Boolean(value));
    }

    public @Nullable Vector getBeamTarget() {
        return super.metadata.getIndex(BEAM_TARGET_INDEX, null);
    }

    public boolean isShowingBottom() {
        return super.metadata.getIndex(SHOWING_BOTTOM_INDEX, true);
    }
}