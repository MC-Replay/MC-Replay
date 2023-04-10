package mc.replay.common.recordables;

import mc.replay.api.recording.recordables.IRecordableRegistry;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.RecordableDefinition;
import mc.replay.common.recordables.block.*;
import mc.replay.common.recordables.entity.*;
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

    private final Map<Byte, RecordableDefinitionImpl<?>> recordableRegistry = new HashMap<>();

    public RecordableRegistry() {
        this.registerRecordable((byte) 0, RecAcknowledgePlayerDigging.class, RecAcknowledgePlayerDigging::new);
        this.registerRecordable((byte) 1, RecBlockAction.class, RecBlockAction::new);
        this.registerRecordable((byte) 2, RecBlockChange.class, RecBlockChange::new);
        this.registerRecordable((byte) 3, RecBlockEntityData.class, RecBlockEntityData::new);
        this.registerRecordable((byte) 4, RecMultiBlockChange.class, RecMultiBlockChange::new);

        //        this.registerRecordable((byte) 5, RecPlayerChat.class, RecPlayerChat::new); // chat
        //        this.registerRecordable((byte) 6, RecPlayerCommand.class, RecPlayerCommand::new); // command

        this.registerRecordable((byte) 7, RecEntityGliding.class, RecEntityGliding::new);
        this.registerRecordable((byte) 8, RecEntitySneaking.class, RecEntitySneaking::new);
        this.registerRecordable((byte) 9, RecEntitySprinting.class, RecEntitySprinting::new);
        this.registerRecordable((byte) 10, RecEntitySwimming.class, RecEntitySwimming::new);
        this.registerRecordable((byte) 11, RecEntityAnimation.class, RecEntityAnimation::new);
        this.registerRecordable((byte) 12, RecEntityEquipment.class, RecEntityEquipment::new);
        this.registerRecordable((byte) 13, RecEntitySwingHandAnimation.class, RecEntitySwingHandAnimation::new);
        this.registerRecordable((byte) 14, RecEntityHeadRotation.class, RecEntityHeadRotation::new);
        this.registerRecordable((byte) 15, RecEntityPositionAndRotation.class, RecEntityPositionAndRotation::new);
        this.registerRecordable((byte) 16, RecEntityTeleport.class, RecEntityTeleport::new);
        this.registerRecordable((byte) 17, RecEntityDestroy.class, RecEntityDestroy::new);
        this.registerRecordable((byte) 18, RecEntitySpawn.class, RecEntitySpawn::new);
        this.registerRecordable((byte) 19, RecEntitySpawnMetadata.class, RecEntitySpawnMetadata::new);
        this.registerRecordable((byte) 20, RecPlayerDestroy.class, RecPlayerDestroy::new);
        this.registerRecordable((byte) 21, RecPlayerSpawn.class, RecPlayerSpawn::new);

        this.registerRecordable((byte) 22, RecParticle.class, RecParticle::new);

        this.registerRecordable((byte) 23, RecCustomSoundEffect.class, RecCustomSoundEffect::new);
        this.registerRecordable((byte) 24, RecEntitySound.class, RecEntitySound::new);
        this.registerRecordable((byte) 25, RecSoundEffect.class, RecSoundEffect::new);
        this.registerRecordable((byte) 26, RecStopSound.class, RecStopSound::new);

        this.registerRecordable((byte) 27, RecWorldEvent.class, RecWorldEvent::new);
    }

    public <R extends Recordable<?>> void registerRecordable(byte id,
                                                             @NotNull Class<R> recordableClass,
                                                             @NotNull Function<ReplayByteBuffer, R> recordableConstructor) {
        this.recordableRegistry.putIfAbsent(id, new RecordableDefinitionImpl<>(id, recordableClass, recordableConstructor));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Recordable<?>> R getRecordable(byte id, @NotNull ReplayByteBuffer buffer) {
        RecordableDefinitionImpl<R> recordableDefinition = (RecordableDefinitionImpl<R>) this.recordableRegistry.get(id);
        if (recordableDefinition == null) {
            throw new IllegalArgumentException("Recordable id '" + id + "' is not registered");
        }

        return recordableDefinition.construct(buffer);
    }

    @Override
    public <R extends Recordable<?>> byte getRecordableId(@NotNull Class<R> recordableClass) {
        for (Map.Entry<Byte, RecordableDefinitionImpl<?>> entry : this.recordableRegistry.entrySet()) {
            if (entry.getValue().recordableClass().equals(recordableClass)) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("Recordable class '" + recordableClass.getName() + "' is not registered");
    }

    @Override
    public Map<Byte, RecordableDefinition<?>> getPacketRegistry() {
        return null;
    }
}