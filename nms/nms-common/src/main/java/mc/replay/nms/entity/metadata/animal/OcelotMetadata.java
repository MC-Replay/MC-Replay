package mc.replay.nms.entity.metadata.animal;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class OcelotMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int TRUSTING_INDEX = OFFSET;

    public OcelotMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setTrusting(boolean value) {
        super.metadata.setIndex(TRUSTING_INDEX, MetadataTypes.Boolean(value));
    }

    public boolean isTrusting() {
        return super.metadata.getIndex(TRUSTING_INDEX, false);
    }
}