package mc.replay.replay.time;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.MCReplayInternal;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import mc.replay.common.recordables.types.entity.movement.RecEntityHeadRotation;
import mc.replay.common.recordables.types.entity.movement.RecEntityPosition;
import mc.replay.common.recordables.types.internal.BlockRelatedRecordable;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.replay.ReplaySession;
import mc.replay.replay.session.entity.AbstractReplayEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

final class SkipTimeForwardsHandler extends AbstractSkipTimeHandler {

    SkipTimeForwardsHandler(MCReplayInternal instance) {
        super(instance);
    }

    void skipTime(@NotNull ReplaySession session, int until, @NotNull NavigableMap<Integer, List<Recordable>> recordablesBetween) {
        NavigableMap<Integer, List<Recordable>> recordables = session.getRecording().recordables();

        List<Recordable> blockChangeRecordables = this.findRecordables(recordablesBetween, (recordable) -> recordable instanceof BlockRelatedRecordable);
        List<Recordable> spawnRecordables = this.findRecordables(recordablesBetween, (recordable) -> recordable instanceof RecPlayerSpawn || recordable instanceof RecEntitySpawn);
        List<Recordable> destroyRecordables = this.findRecordables(recordablesBetween, (recordable) -> recordable instanceof RecPlayerDestroy || recordable instanceof RecEntityDestroy);

        this.filterSpawnAndDestroyRecordables(spawnRecordables, destroyRecordables); // Filter out entities that are spawned and destroyed in the skipped time range

        List<ClientboundPacket> packets = new ArrayList<>();
        for (Recordable recordable : blockChangeRecordables) {
            packets.addAll(this.handleRecordableForwards(session, recordable));
        }

        for (Recordable recordable : spawnRecordables) {
            packets.addAll(this.handleRecordableForwards(session, recordable));
        }

        for (Recordable recordable : destroyRecordables) {
            packets.addAll(this.handleRecordableForwards(session, recordable));
        }

        for (AbstractReplayEntity<?> entity : session.getPlayTask().getEntityCache().getEntities().values()) {
            RecEntityPosition position = this.findRecordable(RecEntityPosition.class, until, recordables, (recordable) -> {
                return recordable.entityId().entityId() == entity.getOriginalEntityId();
            });

            RecEntityHeadRotation headRotation = this.findRecordable(RecEntityHeadRotation.class, until, recordables, (recordable) -> {
                return recordable.entityId().entityId() == entity.getOriginalEntityId();
            });

            if (headRotation != null) {
                packets.addAll(this.handleRecordableForwards(session, headRotation));
            }

            if (position != null) {
                packets.addAll(this.handleRecordableForwards(session, position));
            }
        }

        session.getPlayTask().sendPackets(packets);
    }
}