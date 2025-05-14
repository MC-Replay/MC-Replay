package mc.replay.nms.entity.metadata.arrow;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class ThrownTridentMetadata extends AbstractArrowMetadata {

    public static final int OFFSET = AbstractArrowMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 2;

    public static final int LOYALTY_LEVEL_INDEX = OFFSET;
    public static final int HAS_ENCHANTMENT_GLINT_INDEX = OFFSET + 1;

    public ThrownTridentMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setLoyaltyLevel(int value) {
        super.metadata.setIndex(LOYALTY_LEVEL_INDEX, Metadata.VarInt(value));
    }

    public void setHasEnchantmentGlint(boolean value) {
        super.metadata.setIndex(HAS_ENCHANTMENT_GLINT_INDEX, Metadata.Boolean(value));
    }

    public int getLoyaltyLevel() {
        return super.metadata.getIndex(LOYALTY_LEVEL_INDEX, 0);
    }

    public boolean isHasEnchantmentGlint() {
        return super.metadata.getIndex(HAS_ENCHANTMENT_GLINT_INDEX, false);
    }
}