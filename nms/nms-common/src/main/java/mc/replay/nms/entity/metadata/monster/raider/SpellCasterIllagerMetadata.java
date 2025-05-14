package mc.replay.nms.entity.metadata.monster.raider;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class SpellCasterIllagerMetadata extends AbstractIllagerMetadata {

    public static final int OFFSET = AbstractIllagerMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int SPELL_INDEX = OFFSET;

    protected SpellCasterIllagerMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setSpell(@NotNull Spell spell) {
        super.metadata.setIndex(SPELL_INDEX, Metadata.Byte((byte) spell.ordinal()));
    }

    public @NotNull Spell getSpell() {
        return Spell.VALUES[super.metadata.getIndex(SPELL_INDEX, (byte) 0)];
    }

    public enum Spell {

        NONE,
        SUMMON_VEX,
        ATTACK,
        WOLOLO,
        DISAPPEAR,
        BLINDNESS;

        private static final Spell[] VALUES = values();
    }
}