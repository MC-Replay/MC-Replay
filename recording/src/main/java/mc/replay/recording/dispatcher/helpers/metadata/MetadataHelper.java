package mc.replay.recording.dispatcher.helpers.metadata;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.ambient.BatMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.animal.*;
import mc.replay.recording.dispatcher.helpers.metadata.animal.tameable.CatMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.animal.tameable.ParrotMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.animal.tameable.TameableAnimalMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.animal.tameable.WolfMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.flying.PhantomMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.golem.ShulkerMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.golem.SnowGolemMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.minecart.FurnaceMinecartMetadataReader;
import mc.replay.recording.dispatcher.helpers.metadata.monster.*;
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
import mc.replay.wrapper.entity.metadata.ambient.BatMetadata;
import mc.replay.wrapper.entity.metadata.animal.*;
import mc.replay.wrapper.entity.metadata.animal.tameable.CatMetadata;
import mc.replay.wrapper.entity.metadata.animal.tameable.ParrotMetadata;
import mc.replay.wrapper.entity.metadata.animal.tameable.TameableAnimalMetadata;
import mc.replay.wrapper.entity.metadata.animal.tameable.WolfMetadata;
import mc.replay.wrapper.entity.metadata.flying.PhantomMetadata;
import mc.replay.wrapper.entity.metadata.golem.IronGolemMetadata;
import mc.replay.wrapper.entity.metadata.golem.ShulkerMetadata;
import mc.replay.wrapper.entity.metadata.golem.SnowGolemMetadata;
import mc.replay.wrapper.entity.metadata.minecart.CommandBlockMinecartMetadata;
import mc.replay.wrapper.entity.metadata.minecart.FurnaceMinecartMetadata;
import mc.replay.wrapper.entity.metadata.monster.*;
import mc.replay.wrapper.entity.metadata.monster.zombie.ZombieMetadata;
import mc.replay.wrapper.entity.metadata.monster.zombie.ZombieVillagerMetadata;
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
        this.registerReader(BatMetadata.class, new BatMetadataReader());

        this.registerReader(CatMetadata.class, new CatMetadataReader());
        this.registerReader(ParrotMetadata.class, new ParrotMetadataReader());
        this.registerReader(TameableAnimalMetadata.class, new TameableAnimalMetadataReader());
        this.registerReader(WolfMetadata.class, new WolfMetadataReader());

        this.registerReader(AbstractHorseMetadata.class, new AbstractHorseMetadataReader());
        this.registerReader(BeeMetadata.class, new BeeMetadataReader());
        this.registerReader(FoxMetadata.class, new FoxMetadataReader());
        this.registerReader(GoatMetadata.class, new GoatMetadataReader());
        this.registerReader(HorseMetadata.class, new HorseMetadataReader());
        this.registerReader(LlamaMetadata.class, new LlamaMetadataReader());
        this.registerReader(MooshroomMetadata.class, new MooshroomMetadataReader());
        this.registerReader(PandaMetadata.class, new PandaMetadataReader());
        this.registerReader(PigMetadata.class, new PigMetadataReader());
        this.registerReader(PolarBearMetadata.class, new PolarBearMetadataReader());
        this.registerReader(RabbitMetadata.class, new RabbitMetadataReader());
        this.registerReader(SheepMetadata.class, new SheepMetadataReader());
        this.registerReader(StriderMetadata.class, new StriderMetadataReader());
        this.registerReader(TurtleMetadata.class, new TurtleMetadataReader());

        this.registerReader(PhantomMetadata.class, new PhantomMetadataReader());

        this.registerReader(ShulkerMetadata.class, new ShulkerMetadataReader());
        this.registerReader(SnowGolemMetadata.class, new SnowGolemMetadataReader());

        this.registerReader(FurnaceMinecartMetadata.class, new FurnaceMinecartMetadataReader());

        this.registerReader(BlazeMetadata.class, new BlazeMetadataReader());
        this.registerReader(CreeperMetadata.class, new CreeperMetadataReader());
        this.registerReader(EndermanMetadata.class, new EndermanMetadataReader());
        this.registerReader(PiglinMetadata.class, new PiglinMetadataReader());
        this.registerReader(ZoglinMetadata.class, new ZoglinMetadataReader());
        this.registerReader(ZombieMetadata.class, new ZombieMetadataReader());
        this.registerReader(ZombieVillagerMetadata.class, new ZombieVillagerMetadataReader());

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
        this.registerSkippedIndexes(CommandBlockMinecartMetadata.class, CommandBlockMinecartMetadata.COMMAND_INDEX, CommandBlockMinecartMetadata.LAST_OUTPUT_INDEX);
        this.registerSkippedIndexes(IronGolemMetadata.class, IronGolemMetadata.OFFSET);
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