package mc.replay.common.recordables;

import mc.replay.api.recording.recordables.IRecordableRegistry;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.RecordableDefinition;
import mc.replay.common.recordables.block.*;
import mc.replay.common.recordables.entity.RecEntityDestroy;
import mc.replay.common.recordables.entity.RecEntitySpawn;
import mc.replay.common.recordables.entity.RecPlayerDestroy;
import mc.replay.common.recordables.entity.RecPlayerSpawn;
import mc.replay.common.recordables.entity.action.RecEntityGliding;
import mc.replay.common.recordables.entity.action.RecEntitySneaking;
import mc.replay.common.recordables.entity.action.RecEntitySprinting;
import mc.replay.common.recordables.entity.action.RecEntitySwimming;
import mc.replay.common.recordables.entity.miscellaneous.RecEntityAnimation;
import mc.replay.common.recordables.entity.miscellaneous.RecEntityEquipment;
import mc.replay.common.recordables.entity.miscellaneous.RecEntitySwingHandAnimation;
import mc.replay.common.recordables.entity.movement.RecEntityHeadRotation;
import mc.replay.common.recordables.entity.movement.RecEntityPositionAndRotation;
import mc.replay.common.recordables.entity.movement.RecEntityTeleport;
import mc.replay.common.recordables.particle.RecParticle;
import mc.replay.common.recordables.sound.RecCustomSoundEffect;
import mc.replay.common.recordables.sound.RecEntitySound;
import mc.replay.common.recordables.sound.RecSoundEffect;
import mc.replay.common.recordables.sound.RecStopSound;
import mc.replay.common.recordables.world.RecWorldEvent;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class RecordableRegistry implements IRecordableRegistry {

    private final Map<Integer, RecordableDefinitionImpl<?>> recordableRegistry = new HashMap<>();

    public RecordableRegistry() {
        this.registerRecordable(0, RecAcknowledgePlayerDigging.class, RecAcknowledgePlayerDigging::new);
        this.registerRecordable(1, RecBlockAction.class, RecBlockAction::new);
        this.registerRecordable(2, RecBlockChange.class, RecBlockChange::new);
        this.registerRecordable(3, RecBlockEntityData.class, RecBlockEntityData::new);
        this.registerRecordable(4, RecMultiBlockChange.class, RecMultiBlockChange::new);

        //        this.registerRecordable(5, RecPlayerChat.class, RecPlayerChat::new); // chat
        //        this.registerRecordable(6, RecPlayerCommand.class, RecPlayerCommand::new); // command

        this.registerRecordable(7, RecEntityGliding.class, RecEntityGliding::new);
        this.registerRecordable(8, RecEntitySneaking.class, RecEntitySneaking::new);
        this.registerRecordable(9, RecEntitySprinting.class, RecEntitySprinting::new);
        this.registerRecordable(10, RecEntitySwimming.class, RecEntitySwimming::new);
        this.registerRecordable(11, RecEntityAnimation.class, RecEntityAnimation::new);
        this.registerRecordable(12, RecEntityEquipment.class, RecEntityEquipment::new);
        this.registerRecordable(13, RecEntitySwingHandAnimation.class, RecEntitySwingHandAnimation::new);
        this.registerRecordable(14, RecEntityHeadRotation.class, RecEntityHeadRotation::new);
        this.registerRecordable(15, RecEntityPositionAndRotation.class, RecEntityPositionAndRotation::new);
        this.registerRecordable(16, RecEntityTeleport.class, RecEntityTeleport::new);
        this.registerRecordable(17, RecEntityDestroy.class, RecEntityDestroy::new);
        this.registerRecordable(18, RecEntitySpawn.class, RecEntitySpawn::new);
        this.registerRecordable(19, RecPlayerDestroy.class, RecPlayerDestroy::new);
        this.registerRecordable(20, RecPlayerSpawn.class, RecPlayerSpawn::new);

        this.registerRecordable(21, RecParticle.class, RecParticle::new);

        this.registerRecordable(22, RecCustomSoundEffect.class, RecCustomSoundEffect::new);
        this.registerRecordable(23, RecEntitySound.class, RecEntitySound::new);
        this.registerRecordable(24, RecSoundEffect.class, RecSoundEffect::new);
        this.registerRecordable(25, RecStopSound.class, RecStopSound::new);

        this.registerRecordable(26, RecWorldEvent.class, RecWorldEvent::new);
    }

    public <R extends Recordable<?>> void registerRecordable(int id,
                                                             @NotNull Class<R> recordableClass,
                                                             @NotNull Function<ReplayByteBuffer, R> recordableConstructor) {
        this.recordableRegistry.putIfAbsent(id, new RecordableDefinitionImpl<>(id, recordableClass, recordableConstructor));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Recordable<?>> R getRecordable(int id, @NotNull ReplayByteBuffer buffer) {
        RecordableDefinitionImpl<R> recordableDefinition = (RecordableDefinitionImpl<R>) this.recordableRegistry.get(id);
        if (recordableDefinition == null) {
            throw new IllegalArgumentException("Recordable id '" + id + "' is not registered");
        }

        return recordableDefinition.construct(buffer);
    }

    @Override
    public <R extends Recordable<?>> int getRecordableId(@NotNull Class<R> recordableClass) {
        for (Map.Entry<Integer, RecordableDefinitionImpl<?>> entry : this.recordableRegistry.entrySet()) {
            if (entry.getValue().recordableClass().equals(recordableClass)) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("Recordable class '" + recordableClass.getName() + "' is not registered");
    }

    @Override
    public Map<Integer, RecordableDefinition<?>> getPacketRegistry() {
        return null;
    }
}