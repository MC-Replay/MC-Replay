package mc.replay.nms.entity.metadata.monster.zombie;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.nms.entity.metadata.villager.VillagerMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class ZombieVillagerMetadata extends ZombieMetadata {

    public static final int OFFSET = ZombieMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 2;

    public static final int CONVERTING_INDEX = OFFSET;
    public static final int VILLAGER_DATA_INDEX = OFFSET + 1;

    public ZombieVillagerMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setConverting(boolean value) {
        super.metadata.setIndex(CONVERTING_INDEX, MetadataTypes.Boolean(value));
    }

    public VillagerMetadata.VillagerData getVillagerData() {
        int[] data = super.metadata.getIndex(VILLAGER_DATA_INDEX, null);
        if (data == null) {
            return new VillagerMetadata.VillagerData(VillagerMetadata.Type.PLAINS, VillagerMetadata.Profession.NONE, VillagerMetadata.Level.NOVICE);
        }
        return new VillagerMetadata.VillagerData(VillagerMetadata.Type.VALUES[data[0]], VillagerMetadata.Profession.VALUES[data[1]], VillagerMetadata.Level.VALUES[data[2] - 1]);
    }

    public boolean isConverting() {
        return super.metadata.getIndex(CONVERTING_INDEX, false);
    }

    public void setVillagerData(VillagerMetadata.VillagerData value) {
        super.metadata.setIndex(VILLAGER_DATA_INDEX, MetadataTypes.VillagerData(
                value.getType().ordinal(),
                value.getProfession().ordinal(),
                value.getLevel().ordinal() + 1
        ));
    }
}