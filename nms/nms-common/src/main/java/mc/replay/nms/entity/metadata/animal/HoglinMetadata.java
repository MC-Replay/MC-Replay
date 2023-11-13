package mc.replay.nms.entity.metadata.animal;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class HoglinMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int IMMUNE_TO_ZOMBIFICATION_INDEX = OFFSET;

    public HoglinMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setImmuneToZombification(boolean value) {
        super.metadata.setIndex(IMMUNE_TO_ZOMBIFICATION_INDEX, MetadataTypes.Boolean(value));
    }

    public boolean isImmuneToZombification() {
        return super.metadata.getIndex(IMMUNE_TO_ZOMBIFICATION_INDEX, false);
    }
}