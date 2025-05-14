package mc.replay.nms.entity.metadata.monster.raider;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class IllusionerMetadata extends SpellCasterIllagerMetadata {

    public static final int OFFSET = SpellCasterIllagerMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    public IllusionerMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }
}