package mc.replay.nms.entity.metadata.animal;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class CowMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public CowMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}