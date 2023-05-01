package mc.replay.recording.dispatcher.dispatchers.packet.entity;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.*;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityMetadataPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.wrapper.entity.EntityWrapper;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.LivingEntityMetadata;
import mc.replay.wrapper.entity.metadata.other.AreaEffectCloudMetadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityMetadataChangePacketOutDispatcher implements DispatcherPacketOut<ClientboundEntityMetadataPacket> {

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundEntityMetadataPacket packet) {
        Map<Integer, Metadata.Entry<?>> entries = new HashMap<>(packet.entries());

        List<Recordable> recordables = new ArrayList<>();

        int id = packet.entityId();
        EntityId entityId = EntityId.of(id);

        EntityWrapper entityWrapper = session.getEntityTracker().getOrFindEntityWrapper(null, id, true);
        if (entityWrapper == null) return List.of();

        EntityMetadata metadata = entityWrapper.getMetadata();
        if (metadata instanceof AreaEffectCloudMetadata) return List.of();

        boolean onFire = metadata.isOnFire();
        boolean invisible = metadata.isInvisible();
        boolean glowing = metadata.isHasGlowingEffect();

        entityWrapper.addMetadata(entries);

        if (entries.remove(EntityMetadata.MASK_INDEX) != null) {
            if (onFire != metadata.isOnFire()) {
                recordables.add(
                        new RecEntityCombust(
                                entityId,
                                metadata.isOnFire()
                        )
                );
            }

            if (invisible != metadata.isInvisible()) {
                recordables.add(
                        new RecEntityInvisible(
                                entityId,
                                metadata.isInvisible()
                        )
                );
            }

            if (glowing != metadata.isHasGlowingEffect()) {
                recordables.add(
                        new RecEntityGlowing(
                                entityId,
                                metadata.isHasGlowingEffect()
                        )
                );
            }
        }

        if (entries.remove(EntityMetadata.CUSTOM_NAME_INDEX) != null) {
            recordables.add(
                    new RecEntityCustomName(
                            entityId,
                            metadata.getCustomName()
                    )
            );
        }

        if (entries.remove(EntityMetadata.CUSTOM_NAME_VISIBLE_INDEX) != null) {
            recordables.add(
                    new RecEntityCustomNameVisible(
                            entityId,
                            metadata.isCustomNameVisible()
                    )
            );
        }

        if (metadata instanceof LivingEntityMetadata livingMetadata) {
            if (entries.remove(LivingEntityMetadata.HEALTH_INDEX) != null) {
                recordables.add(
                        new RecEntityHealth(
                                entityId,
                                livingMetadata.getHealth()
                        )
                );
            }

            if (entries.remove(LivingEntityMetadata.HAND_STATES_MASK_INDEX) != null) {
                recordables.add(
                        new RecEntityHandState(
                                entityId,
                                livingMetadata.isHandActive(),
                                livingMetadata.getActiveHand(),
                                livingMetadata.isInRiptideSpinAttack()
                        )
                );
            }
        }

        if (entries.isEmpty()) return recordables;

        recordables.add(new RecEntityMetadataChange(
                entityId,
                entries
        ));

        return recordables;
    }
}