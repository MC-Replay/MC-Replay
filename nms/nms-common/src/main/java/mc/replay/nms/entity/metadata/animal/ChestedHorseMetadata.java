package mc.replay.nms.entity.metadata.animal;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class ChestedHorseMetadata extends AbstractHorseMetadata {

    public static final int OFFSET = AbstractHorseMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int HAS_CHEST_OFFSET = OFFSET;

    protected ChestedHorseMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setHasChest(boolean value) {
        super.metadata.setIndex(HAS_CHEST_OFFSET, Metadata.Boolean(value));
    }

    public boolean hasChest() {
        return super.metadata.getIndex(HAS_CHEST_OFFSET, false);
    }
}