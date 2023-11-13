package mc.replay.nms.entity.metadata.animal;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class GoatMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int SCREAMING_INDEX = OFFSET;

    public GoatMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setScreaming(boolean value) {
        super.metadata.setIndex(SCREAMING_INDEX, MetadataTypes.Boolean(value));
    }

    public boolean isScreaming() {
        return super.metadata.getIndex(SCREAMING_INDEX, false);
    }
}