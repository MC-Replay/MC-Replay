package mc.replay.nms.entity;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.nms.entity.metadata.PlayerMetadata;
import mc.replay.nms.entity.metadata.ambient.BatMetadata;
import mc.replay.nms.entity.metadata.animal.*;
import mc.replay.nms.entity.metadata.animal.tameable.CatMetadata;
import mc.replay.nms.entity.metadata.animal.tameable.ParrotMetadata;
import mc.replay.nms.entity.metadata.animal.tameable.WolfMetadata;
import mc.replay.nms.entity.metadata.arrow.ArrowMetadata;
import mc.replay.nms.entity.metadata.arrow.SpectralArrowMetadata;
import mc.replay.nms.entity.metadata.arrow.ThrownTridentMetadata;
import mc.replay.nms.entity.metadata.flying.GhastMetadata;
import mc.replay.nms.entity.metadata.flying.PhantomMetadata;
import mc.replay.nms.entity.metadata.golem.IronGolemMetadata;
import mc.replay.nms.entity.metadata.golem.ShulkerMetadata;
import mc.replay.nms.entity.metadata.golem.SnowGolemMetadata;
import mc.replay.nms.entity.metadata.item.*;
import mc.replay.nms.entity.metadata.minecart.*;
import mc.replay.nms.entity.metadata.monster.*;
import mc.replay.nms.entity.metadata.monster.raider.*;
import mc.replay.nms.entity.metadata.monster.skeleton.SkeletonMetadata;
import mc.replay.nms.entity.metadata.monster.skeleton.StrayMetadata;
import mc.replay.nms.entity.metadata.monster.skeleton.WitherSkeletonMetadata;
import mc.replay.nms.entity.metadata.monster.zombie.*;
import mc.replay.nms.entity.metadata.other.*;
import mc.replay.nms.entity.metadata.villager.VillagerMetadata;
import mc.replay.nms.entity.metadata.villager.WanderingTraderMetadata;
import mc.replay.nms.entity.metadata.water.AxolotlMetadata;
import mc.replay.nms.entity.metadata.water.DolphinMetadata;
import mc.replay.nms.entity.metadata.water.GlowSquidMetadata;
import mc.replay.nms.entity.metadata.water.SquidMetadata;
import mc.replay.nms.entity.metadata.water.fish.CodMetadata;
import mc.replay.nms.entity.metadata.water.fish.PufferfishMetadata;
import mc.replay.nms.entity.metadata.water.fish.SalmonMetadata;
import mc.replay.nms.entity.metadata.water.fish.TropicalFishMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.entity.EntityType;

import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

public final class EntityTypes {

    static final Map<String, Function<Metadata, EntityMetadata>> ENTITY_META_SUPPLIER = createMetadataMap();

    public static EntityMetadata createMetadata(EntityType entityType, Metadata metadata) {
        return ENTITY_META_SUPPLIER.get(entityType.getKey().toString()).apply(metadata);
    }

    private static Map<String, Function<Metadata, EntityMetadata>> createMetadataMap() {
        return Map.<String, Function<Metadata, EntityMetadata>>ofEntries(
                entry("minecraft:allay", EntityMetadata::new), // TODO dedicated metadata
                entry("minecraft:area_effect_cloud", AreaEffectCloudMetadata::new),
                entry("minecraft:armor_stand", ArmorStandMetadata::new),
                entry("minecraft:arrow", ArrowMetadata::new),
                entry("minecraft:axolotl", AxolotlMetadata::new),
                entry("minecraft:bat", BatMetadata::new),
                entry("minecraft:bee", BeeMetadata::new),
                entry("minecraft:blaze", BlazeMetadata::new),
                entry("minecraft:boat", BoatMetadata::new),
                entry("minecraft:chest_boat", EntityMetadata::new), // TODO dedicated metadata
                entry("minecraft:cat", CatMetadata::new),
                entry("minecraft:cave_spider", CaveSpiderMetadata::new),
                entry("minecraft:chicken", ChickenMetadata::new),
                entry("minecraft:cod", CodMetadata::new),
                entry("minecraft:cow", CowMetadata::new),
                entry("minecraft:creeper", CreeperMetadata::new),
                entry("minecraft:dolphin", DolphinMetadata::new),
                entry("minecraft:donkey", DonkeyMetadata::new),
                entry("minecraft:dragon_fireball", DragonFireballMetadata::new),
                entry("minecraft:drowned", DrownedMetadata::new),
                entry("minecraft:elder_guardian", ElderGuardianMetadata::new),
                entry("minecraft:end_crystal", EndCrystalMetadata::new),
                entry("minecraft:ender_dragon", EnderDragonMetadata::new),
                entry("minecraft:enderman", EndermanMetadata::new),
                entry("minecraft:endermite", EndermiteMetadata::new),
                entry("minecraft:evoker", EvokerMetadata::new),
                entry("minecraft:evoker_fangs", EvokerFangsMetadata::new),
                entry("minecraft:experience_orb", ExperienceOrbMetadata::new),
                entry("minecraft:eye_of_ender", EyeOfEnderMetadata::new),
                entry("minecraft:falling_block", FallingBlockMetadata::new),
                entry("minecraft:firework_rocket", FireworkRocketMetadata::new),
                entry("minecraft:fox", FoxMetadata::new),
                entry("minecraft:frog", EntityMetadata::new), // TODO dedicated metadata
                entry("minecraft:ghast", GhastMetadata::new),
                entry("minecraft:giant", GiantMetadata::new),
                entry("minecraft:glow_item_frame", GlowItemFrameMetadata::new),
                entry("minecraft:glow_squid", GlowSquidMetadata::new),
                entry("minecraft:goat", GoatMetadata::new),
                entry("minecraft:guardian", GuardianMetadata::new),
                entry("minecraft:hoglin", HoglinMetadata::new),
                entry("minecraft:horse", HorseMetadata::new),
                entry("minecraft:husk", HuskMetadata::new),
                entry("minecraft:illusioner", IllusionerMetadata::new),
                entry("minecraft:iron_golem", IronGolemMetadata::new),
                entry("minecraft:item", ItemEntityMetadata::new),
                entry("minecraft:item_frame", ItemFrameMetadata::new),
                entry("minecraft:fireball", FireballMetadata::new),
                entry("minecraft:leash_knot", LeashKnotMetadata::new),
                entry("minecraft:lightning_bolt", LightningBoltMetadata::new),
                entry("minecraft:llama", LlamaMetadata::new),
                entry("minecraft:llama_spit", LlamaSpitMetadata::new),
                entry("minecraft:magma_cube", MagmaCubeMetadata::new),
                entry("minecraft:marker", MarkerMetadata::new),
                entry("minecraft:minecart", MinecartMetadata::new),
                entry("minecraft:chest_minecart", ChestMinecartMetadata::new),
                entry("minecraft:command_block_minecart", CommandBlockMinecartMetadata::new),
                entry("minecraft:furnace_minecart", FurnaceMinecartMetadata::new),
                entry("minecraft:hopper_minecart", HopperMinecartMetadata::new),
                entry("minecraft:spawner_minecart", SpawnerMinecartMetadata::new),
                entry("minecraft:tnt_minecart", TntMinecartMetadata::new),
                entry("minecraft:mule", MuleMetadata::new),
                entry("minecraft:mooshroom", MooshroomMetadata::new),
                entry("minecraft:ocelot", OcelotMetadata::new),
                entry("minecraft:painting", PaintingMetadata::new),
                entry("minecraft:panda", PandaMetadata::new),
                entry("minecraft:parrot", ParrotMetadata::new),
                entry("minecraft:phantom", PhantomMetadata::new),
                entry("minecraft:pig", PigMetadata::new),
                entry("minecraft:piglin", PiglinMetadata::new),
                entry("minecraft:piglin_brute", PiglinBruteMetadata::new),
                entry("minecraft:pillager", PillagerMetadata::new),
                entry("minecraft:polar_bear", PolarBearMetadata::new),
                entry("minecraft:tnt", PrimedTntMetadata::new),
                entry("minecraft:pufferfish", PufferfishMetadata::new),
                entry("minecraft:rabbit", RabbitMetadata::new),
                entry("minecraft:ravager", RavagerMetadata::new),
                entry("minecraft:salmon", SalmonMetadata::new),
                entry("minecraft:sheep", SheepMetadata::new),
                entry("minecraft:shulker", ShulkerMetadata::new),
                entry("minecraft:shulker_bullet", ShulkerBulletMetadata::new),
                entry("minecraft:silverfish", SilverfishMetadata::new),
                entry("minecraft:skeleton", SkeletonMetadata::new),
                entry("minecraft:skeleton_horse", SkeletonHorseMetadata::new),
                entry("minecraft:slime", SlimeMetadata::new),
                entry("minecraft:small_fireball", SmallFireballMetadata::new),
                entry("minecraft:snow_golem", SnowGolemMetadata::new),
                entry("minecraft:snowball", SnowballMetadata::new),
                entry("minecraft:spectral_arrow", SpectralArrowMetadata::new),
                entry("minecraft:spider", SpiderMetadata::new),
                entry("minecraft:squid", SquidMetadata::new),
                entry("minecraft:stray", StrayMetadata::new),
                entry("minecraft:strider", StriderMetadata::new),
                entry("minecraft:tadpole", EntityMetadata::new), // TODO dedicated metadata
                entry("minecraft:egg", ThrownEggMetadata::new),
                entry("minecraft:ender_pearl", ThrownEnderPearlMetadata::new),
                entry("minecraft:experience_bottle", ThrownExpierenceBottleMetadata::new),
                entry("minecraft:potion", ThrownPotionMetadata::new),
                entry("minecraft:trident", ThrownTridentMetadata::new),
                entry("minecraft:trader_llama", TraderLlamaMetadata::new),
                entry("minecraft:tropical_fish", TropicalFishMetadata::new),
                entry("minecraft:turtle", TurtleMetadata::new),
                entry("minecraft:vex", VexMetadata::new),
                entry("minecraft:villager", VillagerMetadata::new),
                entry("minecraft:vindicator", VindicatorMetadata::new),
                entry("minecraft:wandering_trader", WanderingTraderMetadata::new),
                entry("minecraft:warden", EntityMetadata::new), // TODO dedicated metadata
                entry("minecraft:witch", WitchMetadata::new),
                entry("minecraft:wither", WitherMetadata::new),
                entry("minecraft:wither_skeleton", WitherSkeletonMetadata::new),
                entry("minecraft:wither_skull", WitherSkullMetadata::new),
                entry("minecraft:wolf", WolfMetadata::new),
                entry("minecraft:zoglin", ZoglinMetadata::new),
                entry("minecraft:zombie", ZombieMetadata::new),
                entry("minecraft:zombie_horse", ZombieHorseMetadata::new),
                entry("minecraft:zombie_villager", ZombieVillagerMetadata::new),
                entry("minecraft:zombified_piglin", ZombifiedPiglinMetadata::new),
                entry("minecraft:player", PlayerMetadata::new),
                entry("minecraft:fishing_bobber", FishingHookMetadata::new)
        );
    }
}