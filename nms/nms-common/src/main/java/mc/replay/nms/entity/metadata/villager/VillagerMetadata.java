package mc.replay.nms.entity.metadata.villager;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class VillagerMetadata extends AbstractVillagerMetadata {

    public static final int OFFSET = AbstractVillagerMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int VILLAGER_DATA_INDEX = OFFSET;

    public VillagerMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setVillagerData(@NotNull VillagerData value) {
        super.metadata.setIndex(VILLAGER_DATA_INDEX, MetadataTypes.VillagerData(
                value.type.ordinal(),
                value.profession.ordinal(),
                value.level.ordinal()
        ));
    }

    public @NotNull VillagerData getVillagerData() {
        int[] data = super.metadata.getIndex(VILLAGER_DATA_INDEX, null);
        if (data == null) {
            return new VillagerData(Type.PLAINS, Profession.NONE, Level.NOVICE);
        }
        return new VillagerData(Type.VALUES[data[0]], Profession.VALUES[data[1]], Level.VALUES[data[2] - 1]);
    }

    public static class VillagerData {

        private Type type;
        private Profession profession;
        private Level level;

        public VillagerData(@NotNull Type type, @NotNull Profession profession, @NotNull Level level) {
            this.type = type;
            this.profession = profession;
            this.level = level;
        }

        public @NotNull Type getType() {
            return type;
        }

        public @NotNull Profession getProfession() {
            return profession;
        }

        public @NotNull Level getLevel() {
            return level;
        }

        public void setType(@NotNull Type type) {
            this.type = type;
        }

        public void setProfession(@NotNull Profession profession) {
            this.profession = profession;
        }

        public void setLevel(@NotNull Level level) {
            this.level = level;
        }
    }

    public enum Type {

        DESERT,
        JUNGLE,
        PLAINS,
        SAVANNA,
        SNOW,
        SWAMP,
        TAIGA;

        public static final Type[] VALUES = values();
    }

    public enum Profession {

        NONE,
        ARMORER,
        BUTCHER,
        CARTOGRAPHER,
        CLERIC,
        FARMER,
        FISHERMAN,
        FLETCHER,
        LEATHERWORKER,
        LIBRARIAN,
        NITWIT,
        UNEMPLOYED,
        MASON,
        SHEPHERD,
        TOOLSMITH,
        WEAPONSMITH;

        public final static Profession[] VALUES = values();
    }

    public enum Level {

        NOVICE,
        APPRENTICE,
        JOURNEYMAN,
        EXPERT,
        MASTER;

        public final static Level[] VALUES = values();
    }
}