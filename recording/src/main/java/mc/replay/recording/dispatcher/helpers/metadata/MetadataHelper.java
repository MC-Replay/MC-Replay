package mc.replay.recording.dispatcher.helpers.metadata;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.monster.BlazeMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.monster.CreeperMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.monster.EndermanMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.other.ArmorStandMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.other.BoatMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.other.ItemFrameMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.other.SlimeMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.villager.AbstractVillagerMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.villager.VillagerMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.water.AxolotlMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.water.PufferfishMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.water.TropicalFishMetadataReader;
import mc.replay.wrapper.entity.metadata.*;
import mc.replay.wrapper.entity.metadata.monster.BlazeMetadata;
import mc.replay.wrapper.entity.metadata.monster.CreeperMetadata;
import mc.replay.wrapper.entity.metadata.monster.EndermanMetadata;
import mc.replay.wrapper.entity.metadata.other.ArmorStandMetadata;
import mc.replay.wrapper.entity.metadata.other.BoatMetadata;
import mc.replay.wrapper.entity.metadata.other.ItemFrameMetadata;
import mc.replay.wrapper.entity.metadata.other.SlimeMetadata;
import mc.replay.wrapper.entity.metadata.villager.AbstractVillagerMetadata;
import mc.replay.wrapper.entity.metadata.villager.VillagerMetadata;
import mc.replay.wrapper.entity.metadata.water.AxolotlMetadata;
import mc.replay.wrapper.entity.metadata.water.DolphinMetadata;
import mc.replay.wrapper.entity.metadata.water.fish.PufferfishMetadata;
import mc.replay.wrapper.entity.metadata.water.fish.TropicalFishMetadata;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class MetadataHelper {

    private final Map<Class<?>, Collection<Integer>> skippedIndexes = new HashMap<>();
    private final Map<Class<?>, MetadataReader<?>> readers = new HashMap<>();

    public MetadataHelper() {
        this.registerReader(BlazeMetadata.class, new BlazeMetadataReader());
        this.registerReader(CreeperMetadata.class, new CreeperMetadataReader());
        this.registerReader(EndermanMetadata.class, new EndermanMetadataReader());

        this.registerReader(ArmorStandMetadata.class, new ArmorStandMetadataReader());
        this.registerReader(BoatMetadata.class, new BoatMetadataReader());
        this.registerReader(ItemFrameMetadata.class, new ItemFrameMetadataReader());
        this.registerReader(SlimeMetadata.class, new SlimeMetadataReader());

        this.registerReader(AbstractVillagerMetadata.class, new AbstractVillagerMetadataReader());
        this.registerReader(VillagerMetadata.class, new VillagerMetadataReader());

        this.registerReader(AxolotlMetadata.class, new AxolotlMetadataReader());
        this.registerReader(PufferfishMetadata.class, new PufferfishMetadataReader());
        this.registerReader(TropicalFishMetadata.class, new TropicalFishMetadataReader());

        this.registerReader(AgeableMobMetadata.class, new AgeableMobMetadataReader());
        this.registerReader(EntityMetadata.class, new EntityMetadataReader());
        this.registerReader(LivingEntityMetadata.class, new LivingEntityMetadataReader());
        this.registerReader(MobMetadata.class, new MobMetadataReader());
        this.registerReader(PlayerMetadata.class, new PlayerMetadataReader());

        this.registerSkippedIndexes(DolphinMetadata.class, DolphinMetadata.TREASURE_POSITION_INDEX, DolphinMetadata.CAN_FIND_TREASURE_INDEX, DolphinMetadata.HAS_FISH_INDEX);
    }

    @SuppressWarnings("unchecked, rawtypes")
    public List<Recordable> read(@NotNull EntityMetadata before, @NotNull EntityMetadata metadata, @NotNull Map<Integer, Metadata.Entry<?>> entries, @NotNull EntityId entityId) {
        for (Map.Entry<Class<?>, Collection<Integer>> entry : this.skippedIndexes.entrySet()) {
            if (entry.getKey().isAssignableFrom(metadata.getClass())) {
                entries.keySet().removeAll(entry.getValue());
            }
        }

        List<Recordable> recordables = new ArrayList<>();

        for (Map.Entry<Class<?>, MetadataReader<?>> entry : this.readers.entrySet()) {
            if (entry.getKey().isAssignableFrom(metadata.getClass())) {
                MetadataReader reader = entry.getValue();
                recordables.addAll(reader.read(before, metadata, entries, entityId));
            }
        }

        return recordables;
    }

    private <M extends EntityMetadata> void registerReader(@NotNull Class<M> clazz, @NotNull MetadataReader<M> reader) {
        this.readers.put(clazz, reader);
        this.skippedIndexes.put(clazz, reader.skippedIndexes());
    }

    private <M extends EntityMetadata> void registerSkippedIndexes(@NotNull Class<M> clazz, int... indexes) {
        this.skippedIndexes.put(clazz, Arrays.stream(indexes).boxed().toList());
    }
}