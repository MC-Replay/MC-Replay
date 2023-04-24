package mc.replay.common.recordables;

import mc.replay.api.recording.recordables.IRecordableRegistry;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.RecordableDefinition;
import mc.replay.api.recording.recordables.action.RecordableAction;
import mc.replay.common.recordables.actions.block.*;
import mc.replay.common.recordables.actions.chat.RecPlayerChatAction;
import mc.replay.common.recordables.actions.chat.RecPlayerCommandAction;
import mc.replay.common.recordables.actions.entity.RecEntityDestroyAction;
import mc.replay.common.recordables.actions.entity.RecEntitySpawnAction;
import mc.replay.common.recordables.actions.entity.RecPlayerDestroyAction;
import mc.replay.common.recordables.actions.entity.RecPlayerSpawnAction;
import mc.replay.common.recordables.actions.entity.action.*;
import mc.replay.common.recordables.actions.entity.item.RecCollectItemAction;
import mc.replay.common.recordables.actions.entity.miscellaneous.*;
import mc.replay.common.recordables.actions.entity.movement.RecEntityPositionAction;
import mc.replay.common.recordables.actions.entity.movement.RecEntityRotationAction;
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
import mc.replay.common.recordables.types.entity.action.*;
import mc.replay.common.recordables.types.entity.item.RecCollectItem;
import mc.replay.common.recordables.types.entity.miscellaneous.*;
import mc.replay.common.recordables.types.entity.movement.RecEntityPosition;
import mc.replay.common.recordables.types.entity.movement.RecEntityHeadRotation;
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

    private final Map<Byte, RecordableDefinitionImpl<?>> recordableRegistry = new HashMap<>();

    public RecordableRegistry() {
        this.registerRecordable((byte) 0, RecAcknowledgePlayerDigging.class, RecAcknowledgePlayerDigging::new, new RecAcknowledgePlayerDiggingAction());
        this.registerRecordable((byte) 1, RecBlockAction.class, RecBlockAction::new, new RecBlockActionAction());
        this.registerRecordable((byte) 2, RecBlockBreakStage.class, RecBlockBreakStage::new, new RecBlockBreakStageAction());
        this.registerRecordable((byte) 3, RecBlockChange.class, RecBlockChange::new, new RecBlockChangeAction());
        this.registerRecordable((byte) 4, RecBlockEntityData.class, RecBlockEntityData::new, new RecBlockEntityDataAction());
        this.registerRecordable((byte) 5, RecMultiBlockChange.class, RecMultiBlockChange::new, new RecMultiBlockChangeAction());

        this.registerRecordable((byte) 6, RecPlayerChat.class, RecPlayerChat::new, new RecPlayerChatAction());
        this.registerRecordable((byte) 7, RecPlayerCommand.class, RecPlayerCommand::new, new RecPlayerCommandAction());

        this.registerRecordable((byte) 8, RecEntityCombust.class, RecEntityCombust::new, new RecEntityCombustAction());
        this.registerRecordable((byte) 9, RecEntityGliding.class, RecEntityGliding::new, new RecEntityGlidingAction());
        this.registerRecordable((byte) 10, RecEntitySneaking.class, RecEntitySneaking::new, new RecEntitySneakingAction());
        this.registerRecordable((byte) 11, RecEntitySprinting.class, RecEntitySprinting::new, new RecEntitySprintingAction());
        this.registerRecordable((byte) 12, RecEntitySwimming.class, RecEntitySwimming::new, new RecEntitySwimmingAction());
        this.registerRecordable((byte) 13, RecCollectItem.class, RecCollectItem::new, new RecCollectItemAction());
        this.registerRecordable((byte) 14, RecEntityAnimation.class, RecEntityAnimation::new, new RecEntityAnimationAction());
        this.registerRecordable((byte) 15, RecEntityAttach.class, RecEntityAttach::new, new RecEntityAttachAction());
        this.registerRecordable((byte) 16, RecEntityEquipment.class, RecEntityEquipment::new, new RecEntityEquipmentAction());
        this.registerRecordable((byte) 17, RecEntityMetadataChange.class, RecEntityMetadataChange::new, new RecEntityMetadataChangeAction());
        this.registerRecordable((byte) 18, RecEntitySetPassengers.class, RecEntitySetPassengers::new, new RecEntitySetPassengersAction());
        this.registerRecordable((byte) 19, RecEntityStatus.class, RecEntityStatus::new, new RecEntityStatusAction());
        this.registerRecordable((byte) 20, RecEntitySwingHandAnimation.class, RecEntitySwingHandAnimation::new, new RecEntitySwingHandAnimationAction());
        this.registerRecordable((byte) 21, RecEntityPosition.class, RecEntityPosition::new, new RecEntityPositionAction());
        this.registerRecordable((byte) 22, RecEntityHeadRotation.class, RecEntityHeadRotation::new, new RecEntityRotationAction());
        this.registerRecordable((byte) 23, RecEntityDestroy.class, RecEntityDestroy::new, new RecEntityDestroyAction());
        this.registerRecordable((byte) 24, RecEntitySpawn.class, RecEntitySpawn::new, new RecEntitySpawnAction());
        this.registerRecordable((byte) 25, RecPlayerDestroy.class, RecPlayerDestroy::new, new RecPlayerDestroyAction());
        this.registerRecordable((byte) 26, RecPlayerSpawn.class, RecPlayerSpawn::new, new RecPlayerSpawnAction());

        this.registerRecordable((byte) 27, RecParticle.class, RecParticle::new, new RecParticleAction());

        this.registerRecordable((byte) 28, RecCustomSoundEffect.class, RecCustomSoundEffect::new, new RecCustomSoundEffectAction());
        this.registerRecordable((byte) 29, RecEntitySound.class, RecEntitySound::new, new RecEntitySoundAction());
        this.registerRecordable((byte) 30, RecSoundEffect.class, RecSoundEffect::new, new RecSoundEffectAction());
        this.registerRecordable((byte) 31, RecStopSound.class, RecStopSound::new, new RecStopSoundAction());

        this.registerRecordable((byte) 32, RecWorldEvent.class, RecWorldEvent::new, new RecWorldEventAction());
    }

    @Override
    public <R extends Recordable> void registerRecordable(byte id,
                                                          @NotNull Class<R> recordableClass,
                                                          @NotNull Function<ReplayByteBuffer, R> recordableConstructor,
                                                          @NotNull RecordableAction<R, ?> action) {
        this.recordableRegistry.putIfAbsent(id, new RecordableDefinitionImpl<>(id, recordableClass, recordableConstructor, action));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Recordable> R getRecordable(byte id, @NotNull ReplayByteBuffer buffer) {
        RecordableDefinitionImpl<R> recordableDefinition = (RecordableDefinitionImpl<R>) this.recordableRegistry.get(id);
        if (recordableDefinition == null) {
            throw new IllegalArgumentException("Recordable id '" + id + "' is not registered");
        }

        return recordableDefinition.construct(buffer);
    }

    @Override
    public <R extends Recordable> byte getRecordableId(@NotNull Class<R> recordableClass) {
        for (Map.Entry<Byte, RecordableDefinitionImpl<?>> entry : this.recordableRegistry.entrySet()) {
            if (entry.getValue().recordableClass().equals(recordableClass)) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("Recordable class '" + recordableClass.getName() + "' is not registered");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Recordable> RecordableDefinition<R> getRecordableDefinition(@NotNull Class<R> recordableClass) {
        for (Map.Entry<Byte, RecordableDefinitionImpl<?>> entry : this.recordableRegistry.entrySet()) {
            if (entry.getValue().recordableClass().equals(recordableClass)) {
                return (RecordableDefinition<R>) entry.getValue();
            }
        }

        throw new IllegalArgumentException("Recordable class '" + recordableClass.getName() + "' is not registered");
    }

    @Override
    public Map<Byte, RecordableDefinition<?>> getPacketRegistry() {
        return null;
    }
}