package mc.replay.common.recordables;

import mc.replay.api.recordables.IRecordableRegistry;
import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.RecordableDefinition;
import mc.replay.api.recordables.action.RecordableAction;
import mc.replay.common.recordables.actions.block.*;
import mc.replay.common.recordables.actions.chat.RecPlayerChatAction;
import mc.replay.common.recordables.actions.chat.RecPlayerCommandAction;
import mc.replay.common.recordables.actions.entity.RecEntityDestroyAction;
import mc.replay.common.recordables.actions.entity.RecEntitySpawnAction;
import mc.replay.common.recordables.actions.entity.RecPlayerDestroyAction;
import mc.replay.common.recordables.actions.entity.RecPlayerSpawnAction;
import mc.replay.common.recordables.actions.entity.equipment.*;
import mc.replay.common.recordables.actions.entity.item.RecCollectItemAction;
import mc.replay.common.recordables.actions.entity.metadata.*;
import mc.replay.common.recordables.actions.entity.metadata.ambient.RecBatHangingAction;
import mc.replay.common.recordables.actions.entity.metadata.animal.*;
import mc.replay.common.recordables.actions.entity.metadata.animal.tameable.*;
import mc.replay.common.recordables.actions.entity.metadata.flying.RecPhantomSizeAction;
import mc.replay.common.recordables.actions.entity.metadata.golem.RecShulkerShieldHeightAction;
import mc.replay.common.recordables.actions.entity.metadata.golem.RecSnowGolemPumpkinHatAction;
import mc.replay.common.recordables.actions.entity.metadata.living.RecLivingEntityArrowCountAction;
import mc.replay.common.recordables.actions.entity.metadata.living.RecLivingEntityBeeStingerCountAction;
import mc.replay.common.recordables.actions.entity.metadata.living.RecLivingEntityHandStateAction;
import mc.replay.common.recordables.actions.entity.metadata.living.RecLivingEntityHealthAction;
import mc.replay.common.recordables.actions.entity.metadata.minecart.RecFurnaceMinecartFuelAction;
import mc.replay.common.recordables.actions.entity.metadata.mob.RecMobBabyAction;
import mc.replay.common.recordables.actions.entity.metadata.mob.RecMobLeftHandedAction;
import mc.replay.common.recordables.actions.entity.metadata.monster.*;
import mc.replay.common.recordables.actions.entity.metadata.other.*;
import mc.replay.common.recordables.actions.entity.metadata.player.RecPlayerDisplayedSkinPartsAction;
import mc.replay.common.recordables.actions.entity.metadata.player.RecPlayerMainHandAction;
import mc.replay.common.recordables.actions.entity.metadata.player.RecPlayerShoulderDataAction;
import mc.replay.common.recordables.actions.entity.metadata.villager.RecVillagerDataAction;
import mc.replay.common.recordables.actions.entity.metadata.villager.RecVillagerHeadShakeTimerAction;
import mc.replay.common.recordables.actions.entity.metadata.water.RecPufferfishStateAction;
import mc.replay.common.recordables.actions.entity.miscellaneous.*;
import mc.replay.common.recordables.actions.entity.movement.RecEntityHeadRotationAction;
import mc.replay.common.recordables.actions.entity.movement.RecEntityPositionAction;
import mc.replay.common.recordables.actions.particle.RecParticleAction;
import mc.replay.common.recordables.actions.sound.RecCustomSoundEffectAction;
import mc.replay.common.recordables.actions.sound.RecEntitySoundAction;
import mc.replay.common.recordables.actions.sound.RecSoundEffectAction;
import mc.replay.common.recordables.actions.sound.RecStopSoundAction;
import mc.replay.common.recordables.actions.world.RecWorldEventAction;
import mc.replay.common.recordables.types.block.*;
import mc.replay.common.recordables.types.chat.RecPlayerChat;
import mc.replay.common.recordables.types.chat.RecPlayerCommand;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import mc.replay.common.recordables.types.entity.equipment.*;
import mc.replay.common.recordables.types.entity.item.RecCollectItem;
import mc.replay.common.recordables.types.entity.metadata.*;
import mc.replay.common.recordables.types.entity.metadata.ambient.RecBatHanging;
import mc.replay.common.recordables.types.entity.metadata.animal.*;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.*;
import mc.replay.common.recordables.types.entity.metadata.flying.RecPhantomSize;
import mc.replay.common.recordables.types.entity.metadata.golem.RecShulkerShieldHeight;
import mc.replay.common.recordables.types.entity.metadata.golem.RecSnowGolemPumpkinHat;
import mc.replay.common.recordables.types.entity.metadata.living.RecLivingEntityArrowCount;
import mc.replay.common.recordables.types.entity.metadata.living.RecLivingEntityBeeStingerCount;
import mc.replay.common.recordables.types.entity.metadata.living.RecLivingEntityHandState;
import mc.replay.common.recordables.types.entity.metadata.living.RecLivingEntityHealth;
import mc.replay.common.recordables.types.entity.metadata.minecart.RecFurnaceMinecartFuel;
import mc.replay.common.recordables.types.entity.metadata.mob.RecMobBaby;
import mc.replay.common.recordables.types.entity.metadata.mob.RecMobLeftHanded;
import mc.replay.common.recordables.types.entity.metadata.monster.*;
import mc.replay.common.recordables.types.entity.metadata.other.*;
import mc.replay.common.recordables.types.entity.metadata.player.RecPlayerDisplayedSkinParts;
import mc.replay.common.recordables.types.entity.metadata.player.RecPlayerMainHand;
import mc.replay.common.recordables.types.entity.metadata.player.RecPlayerShoulderData;
import mc.replay.common.recordables.types.entity.metadata.villager.RecVillagerData;
import mc.replay.common.recordables.types.entity.metadata.villager.RecVillagerHeadShakeTimer;
import mc.replay.common.recordables.types.entity.metadata.water.RecPufferfishState;
import mc.replay.common.recordables.types.entity.miscellaneous.*;
import mc.replay.common.recordables.types.entity.movement.RecEntityHeadRotation;
import mc.replay.common.recordables.types.entity.movement.RecEntityPosition;
import mc.replay.common.recordables.types.particle.RecParticle;
import mc.replay.common.recordables.types.sound.RecCustomSoundEffect;
import mc.replay.common.recordables.types.sound.RecEntitySound;
import mc.replay.common.recordables.types.sound.RecSoundEffect;
import mc.replay.common.recordables.types.sound.RecStopSound;
import mc.replay.common.recordables.types.world.RecWorldEvent;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class RecordableRegistry implements IRecordableRegistry {

    private final Map<Integer, RecordableDefinition<?>> recordableRegistry = new HashMap<>();

    private int lastId = 0;

    public RecordableRegistry() {
        this.registerRecordable(RecAcknowledgePlayerDigging.class, RecAcknowledgePlayerDigging::new, new RecAcknowledgePlayerDiggingAction());
        this.registerRecordable(RecBlockAction.class, RecBlockAction::new, new RecBlockActionAction());
        this.registerRecordable(RecBlockBreakStage.class, RecBlockBreakStage::new, new RecBlockBreakStageAction());
        this.registerRecordable(RecBlockChange.class, RecBlockChange::new, new RecBlockChangeAction());
        this.registerRecordable(RecBlockEntityData.class, RecBlockEntityData::new, new RecBlockEntityDataAction());
        this.registerRecordable(RecMultiBlockChange.class, RecMultiBlockChange::new, new RecMultiBlockChangeAction());

        this.registerRecordable(RecPlayerChat.class, RecPlayerChat::new, new RecPlayerChatAction());
        this.registerRecordable(RecPlayerCommand.class, RecPlayerCommand::new, new RecPlayerCommandAction());

        this.registerRecordable(RecEntityBoots.class, RecEntityBoots::new, new RecEntityBootsAction());
        this.registerRecordable(RecEntityChestplate.class, RecEntityChestplate::new, new RecEntityChestplateAction());
        this.registerRecordable(RecEntityHand.class, RecEntityHand::new, new RecEntityHandAction());
        this.registerRecordable(RecEntityHelmet.class, RecEntityHelmet::new, new RecEntityHelmetAction());
        this.registerRecordable(RecEntityLeggings.class, RecEntityLeggings::new, new RecEntityLeggingsAction());
        this.registerRecordable(RecEntityOffHand.class, RecEntityOffHand::new, new RecEntityOffHandAction());

        this.registerRecordable(RecCollectItem.class, RecCollectItem::new, new RecCollectItemAction());

        this.registerRecordable(RecBatHanging.class, RecBatHanging::new, new RecBatHangingAction());

        this.registerRecordable(RecCatCollarColor.class, RecCatCollarColor::new, new RecCatCollarColorAction());
        this.registerRecordable(RecCatLying.class, RecCatLying::new, new RecCatLyingAction());
        this.registerRecordable(RecCatRelaxed.class, RecCatRelaxed::new, new RecCatRelaxedAction());
        this.registerRecordable(RecTameableAnimalSitting.class, RecTameableAnimalSitting::new, new RecTameableAnimalSittingAction());
        this.registerRecordable(RecWolfAngry.class, RecWolfAngry::new, new RecWolfAngryAction());
        this.registerRecordable(RecWolfBegging.class, RecWolfBegging::new, new RecWolfBeggingAction());
        this.registerRecordable(RecWolfCollarColor.class, RecWolfCollarColor::new, new RecWolfCollarColorAction());

        this.registerRecordable(RecAbstractHorseEating.class, RecAbstractHorseEating::new, new RecAbstractHorseEatingAction());
        this.registerRecordable(RecAbstractHorseMouthOpen.class, RecAbstractHorseMouthOpen::new, new RecAbstractHorseMouthOpenAction());
        this.registerRecordable(RecAbstractHorseRearing.class, RecAbstractHorseRearing::new, new RecAbstractHorseRearingAction());
        this.registerRecordable(RecAbstractHorseSaddled.class, RecAbstractHorseSaddled::new, new RecAbstractHorseSaddledAction());
        this.registerRecordable(RecBeeNectar.class, RecBeeNectar::new, new RecBeeNectarAction());
        this.registerRecordable(RecFoxFacePlanted.class, RecFoxFacePlanted::new, new RecFoxFacePlantedAction());
        this.registerRecordable(RecFoxPouncing.class, RecFoxPouncing::new, new RecFoxPouncingAction());
        this.registerRecordable(RecFoxSitting.class, RecFoxSitting::new, new RecFoxSittingAction());
        this.registerRecordable(RecFoxSleeping.class, RecFoxSleeping::new, new RecFoxSleepingAction());
        this.registerRecordable(RecFoxSneaking.class, RecFoxSneaking::new, new RecFoxSneakingAction());
        this.registerRecordable(RecGoatScreaming.class, RecGoatScreaming::new, new RecGoatScreamingAction());
        this.registerRecordable(RecLlamaCarpetColor.class, RecLlamaCarpetColor::new, new RecLlamaCarpetColorAction());
        this.registerRecordable(RecPandaEating.class, RecPandaEating::new, new RecPandaEatingAction());
        this.registerRecordable(RecPandaHiddenGene.class, RecPandaHiddenGene::new, new RecPandaHiddenGeneAction());
        this.registerRecordable(RecPandaMainGene.class, RecPandaMainGene::new, new RecPandaMainGeneAction());
        this.registerRecordable(RecPandaOnBack.class, RecPandaOnBack::new, new RecPandaOnBackAction());
        this.registerRecordable(RecPandaRolling.class, RecPandaRolling::new, new RecPandaRollingAction());
        this.registerRecordable(RecPandaSitting.class, RecPandaSitting::new, new RecPandaSittingAction());
        this.registerRecordable(RecPandaSneezing.class, RecPandaSneezing::new, new RecPandaSneezingAction());
        this.registerRecordable(RecPigSaddle.class, RecPigSaddle::new, new RecPigSaddleAction());
        this.registerRecordable(RecPolarBearStandingUp.class, RecPolarBearStandingUp::new, new RecPolarBearStandingUpAction());
        this.registerRecordable(RecSheepColor.class, RecSheepColor::new, new RecSheepColorAction());
        this.registerRecordable(RecSheepSheared.class, RecSheepSheared::new, new RecSheepShearedAction());
        this.registerRecordable(RecStriderSaddle.class, RecStriderSaddle::new, new RecStriderSaddleAction());
        this.registerRecordable(RecTurtleEgg.class, RecTurtleEgg::new, new RecTurtleEggAction());
        this.registerRecordable(RecTurtleLayingEgg.class, RecTurtleLayingEgg::new, new RecTurtleLayingEggAction());

        this.registerRecordable(RecPhantomSize.class, RecPhantomSize::new, new RecPhantomSizeAction());

        this.registerRecordable(RecShulkerShieldHeight.class, RecShulkerShieldHeight::new, new RecShulkerShieldHeightAction());
        this.registerRecordable(RecSnowGolemPumpkinHat.class, RecSnowGolemPumpkinHat::new, new RecSnowGolemPumpkinHatAction());

        this.registerRecordable(RecLivingEntityArrowCount.class, RecLivingEntityArrowCount::new, new RecLivingEntityArrowCountAction());
        this.registerRecordable(RecLivingEntityBeeStingerCount.class, RecLivingEntityBeeStingerCount::new, new RecLivingEntityBeeStingerCountAction());
        this.registerRecordable(RecLivingEntityHandState.class, RecLivingEntityHandState::new, new RecLivingEntityHandStateAction());
        this.registerRecordable(RecLivingEntityHealth.class, RecLivingEntityHealth::new, new RecLivingEntityHealthAction());

        this.registerRecordable(RecFurnaceMinecartFuel.class, RecFurnaceMinecartFuel::new, new RecFurnaceMinecartFuelAction());

        this.registerRecordable(RecMobBaby.class, RecMobBaby::new, new RecMobBabyAction());
        this.registerRecordable(RecMobLeftHanded.class, RecMobLeftHanded::new, new RecMobLeftHandedAction());

        this.registerRecordable(RecCreeperCharged.class, RecCreeperCharged::new, new RecCreeperChargedAction());
        this.registerRecordable(RecCreeperState.class, RecCreeperState::new, new RecCreeperStateAction());
        this.registerRecordable(RecEndermanCarriedBlock.class, RecEndermanCarriedBlock::new, new RecEndermanCarriedBlockAction());
        this.registerRecordable(RecEndermanScreaming.class, RecEndermanScreaming::new, new RecEndermanScreamingAction());
        this.registerRecordable(RecEndermanStaring.class, RecEndermanStaring::new, new RecEndermanStaringAction());
        this.registerRecordable(RecPiglinChargingCrossbow.class, RecPiglinChargingCrossbow::new, new RecPiglinChargingCrossbowAction());
        this.registerRecordable(RecPiglinDancing.class, RecPiglinDancing::new, new RecPiglinDancingAction());
        this.registerRecordable(RecZombieBecomingDrowned.class, RecZombieBecomingDrowned::new, new RecZombieBecomingDrownedAction());
        this.registerRecordable(RecZombieVillagerConverting.class, RecZombieVillagerConverting::new, new RecZombieVillagerConvertingAction());

        this.registerRecordable(RecArmorStandArms.class, RecArmorStandArms::new, new RecArmorStandArmsAction());
        this.registerRecordable(RecArmorStandBasePlate.class, RecArmorStandBasePlate::new, new RecArmorStandBasePlateAction());
        this.registerRecordable(RecArmorStandRotation.class, RecArmorStandRotation::new, new RecArmorStandRotationAction());
        this.registerRecordable(RecArmorStandSmall.class, RecArmorStandSmall::new, new RecArmorStandSmallAction());
        this.registerRecordable(RecItemFrameItem.class, RecItemFrameItem::new, new RecItemFrameItemAction());
        this.registerRecordable(RecItemFrameRotation.class, RecItemFrameRotation::new, new RecItemFrameRotationAction());
        this.registerRecordable(RecSlimeSize.class, RecSlimeSize::new, new RecSlimeSizeAction());

        this.registerRecordable(RecPlayerDisplayedSkinParts.class, RecPlayerDisplayedSkinParts::new, new RecPlayerDisplayedSkinPartsAction());
        this.registerRecordable(RecPlayerMainHand.class, RecPlayerMainHand::new, new RecPlayerMainHandAction());
        this.registerRecordable(RecPlayerShoulderData.class, RecPlayerShoulderData::new, new RecPlayerShoulderDataAction());

        this.registerRecordable(RecVillagerData.class, RecVillagerData::new, new RecVillagerDataAction());
        this.registerRecordable(RecVillagerHeadShakeTimer.class, RecVillagerHeadShakeTimer::new, new RecVillagerHeadShakeTimerAction());

        this.registerRecordable(RecPufferfishState.class, RecPufferfishState::new, new RecPufferfishStateAction());

        this.registerRecordable(RecEntityCombust.class, RecEntityCombust::new, new RecEntityCombustAction());
        this.registerRecordable(RecEntityCustomName.class, RecEntityCustomName::new, new RecEntityCustomNameAction());
        this.registerRecordable(RecEntityCustomNameVisible.class, RecEntityCustomNameVisible::new, new RecEntityCustomNameVisibleAction());
        this.registerRecordable(RecEntityGliding.class, RecEntityGliding::new, new RecEntityGlidingAction());
        this.registerRecordable(RecEntityGlowing.class, RecEntityGlowing::new, new RecEntityGlowingAction());
        this.registerRecordable(RecEntityInvisible.class, RecEntityInvisible::new, new RecEntityInvisibleAction());
        this.registerRecordable(RecEntityMetadataChange.class, RecEntityMetadataChange::new, new RecEntityMetadataChangeAction());
        this.registerRecordable(RecEntityPose.class, RecEntityPose::new, new RecEntityPoseAction());
        this.registerRecordable(RecEntitySneaking.class, RecEntitySneaking::new, new RecEntitySneakingAction());
        this.registerRecordable(RecEntitySprinting.class, RecEntitySprinting::new, new RecEntitySprintingAction());
        this.registerRecordable(RecEntitySwimming.class, RecEntitySwimming::new, new RecEntitySwimmingAction());
        this.registerRecordable(RecEntityVariant.class, RecEntityVariant::new, new RecEntityVariantAction());

        this.registerRecordable(RecEntityAnimation.class, RecEntityAnimation::new, new RecEntityAnimationAction());
        this.registerRecordable(RecEntityAttach.class, RecEntityAttach::new, new RecEntityAttachAction());
        this.registerRecordable(RecEntitySetPassengers.class, RecEntitySetPassengers::new, new RecEntitySetPassengersAction());
        this.registerRecordable(RecEntityStatus.class, RecEntityStatus::new, new RecEntityStatusAction());
        this.registerRecordable(RecEntitySwingHandAnimation.class, RecEntitySwingHandAnimation::new, new RecEntitySwingHandAnimationAction());

        this.registerRecordable(RecEntityHeadRotation.class, RecEntityHeadRotation::new, new RecEntityHeadRotationAction());
        this.registerRecordable(RecEntityPosition.class, RecEntityPosition::new, new RecEntityPositionAction());

        this.registerRecordable(RecEntityDestroy.class, RecEntityDestroy::new, new RecEntityDestroyAction());
        this.registerRecordable(RecEntitySpawn.class, RecEntitySpawn::new, new RecEntitySpawnAction());
        this.registerRecordable(RecPlayerDestroy.class, RecPlayerDestroy::new, new RecPlayerDestroyAction());
        this.registerRecordable(RecPlayerSpawn.class, RecPlayerSpawn::new, new RecPlayerSpawnAction());

        this.registerRecordable(RecParticle.class, RecParticle::new, new RecParticleAction());

        this.registerRecordable(RecCustomSoundEffect.class, RecCustomSoundEffect::new, new RecCustomSoundEffectAction());
        this.registerRecordable(RecEntitySound.class, RecEntitySound::new, new RecEntitySoundAction());
        this.registerRecordable(RecSoundEffect.class, RecSoundEffect::new, new RecSoundEffectAction());
        this.registerRecordable(RecStopSound.class, RecStopSound::new, new RecStopSoundAction());

        this.registerRecordable(RecWorldEvent.class, RecWorldEvent::new, new RecWorldEventAction());
    }

    @Override
    public <R extends Recordable> void registerRecordable(int id,
                                                          @NotNull Class<R> recordableClass,
                                                          @NotNull Function<ReplayByteBuffer, R> recordableConstructor,
                                                          @NotNull RecordableAction<R, ?> action) {
        RecordableDefinition<?> definition = this.recordableRegistry.putIfAbsent(id, new RecordableDefinitionImpl<>(id, recordableClass, recordableConstructor, action));
        if (definition != null) {
            throw new IllegalArgumentException("Recordable id '" + id + "' is already registered");
        }
    }

    @Override
    public <R extends Recordable> void registerRecordable(@NotNull Class<R> recordableClass, @NotNull Function<ReplayByteBuffer, R> recordableConstructor, @NotNull RecordableAction<R, ?> action) {
        this.registerRecordable(this.lastId++, recordableClass, recordableConstructor, action);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Recordable> R getRecordable(int id, @NotNull ReplayByteBuffer buffer) {
        RecordableDefinitionImpl<R> recordableDefinition = (RecordableDefinitionImpl<R>) this.recordableRegistry.get(id);
        if (recordableDefinition == null) {
            throw new IllegalArgumentException("Recordable id '" + id + "' is not registered");
        }

        return recordableDefinition.construct(buffer);
    }

    @Override
    public <R extends Recordable> int getRecordableId(@NotNull Class<R> recordableClass) {
        for (Map.Entry<Integer, RecordableDefinition<?>> entry : this.recordableRegistry.entrySet()) {
            if (entry.getValue().recordableClass().equals(recordableClass)) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("Recordable class '" + recordableClass.getName() + "' is not registered");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Recordable> RecordableDefinition<R> getRecordableDefinition(@NotNull Class<R> recordableClass) {
        for (Map.Entry<Integer, RecordableDefinition<?>> entry : this.recordableRegistry.entrySet()) {
            if (entry.getValue().recordableClass().equals(recordableClass)) {
                return (RecordableDefinition<R>) entry.getValue();
            }
        }

        throw new IllegalArgumentException("Recordable class '" + recordableClass.getName() + "' is not registered");
    }

    @Override
    public Map<Integer, RecordableDefinition<?>> getRecordableRegistry() {
        return this.recordableRegistry;
    }
}