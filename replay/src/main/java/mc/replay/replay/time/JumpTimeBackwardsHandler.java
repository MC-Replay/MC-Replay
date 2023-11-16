package mc.replay.replay.time;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.MCReplayInternal;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import mc.replay.common.recordables.types.internal.BlockRelatedRecordable;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.replay.ReplaySession;
import mc.replay.replay.session.entity.ReplaySessionEntityHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

final class JumpTimeBackwardsHandler extends AbstractJumpTimeHandler {

    JumpTimeBackwardsHandler(MCReplayInternal instance) {
        super(instance);
    }

    @Override
    void jumpTime(@NotNull ReplaySession session, int until, @NotNull NavigableMap<Integer, List<Recordable>> recordablesBetween) {
        recordablesBetween = recordablesBetween.descendingMap(); // Reverse the map so that the recordables are in the correct order

        List<Recordable> blockChangeRecordables = super.findRecordables(recordablesBetween, (recordable) -> recordable instanceof BlockRelatedRecordable);
        List<Recordable> spawnRecordables = super.findRecordables(recordablesBetween, (recordable) -> recordable instanceof RecPlayerSpawn || recordable instanceof RecEntitySpawn);
        List<Recordable> destroyRecordables = super.findRecordables(recordablesBetween, (recordable) -> recordable instanceof RecPlayerDestroy || recordable instanceof RecEntityDestroy);

        this.filterSpawnAndDestroyRecordables(spawnRecordables, destroyRecordables); // Filter out entities that are spawned and destroyed in the skipped time range

        List<ClientboundPacket> packets = new ArrayList<>();
        for (Recordable recordable : blockChangeRecordables) {
            packets.addAll(super.handleRecordable(session, recordable));
        }

        ReplaySessionEntityHandler entityCache = session.getEntityCache();

        for (Recordable recordable : spawnRecordables) {
            if (recordable instanceof RecPlayerSpawn recPlayerSpawn) {
                entityCache.destroyPlayer(recPlayerSpawn.entityId().entityId());
                continue;
            }

            if (recordable instanceof RecEntitySpawn recEntitySpawn) {
                entityCache.destroyEntity(recEntitySpawn.entityId().entityId());
            }
        }

        // Spawn players and entities that were destroyed in the skipped time range
        for (Recordable recordable : destroyRecordables) {
            if (recordable instanceof RecPlayerDestroy recPlayerDestroy) {
                RecPlayerSpawn spawnRecordable = super.findRecordable(RecPlayerSpawn.class, until, recordablesBetween, (rec) -> rec.entityId().entityUuid().equals(recPlayerDestroy.entityId().entityUuid()));
                if (spawnRecordable != null) {
                    entityCache.spawnPlayer(spawnRecordable);
                }

                continue;
            }

            if (recordable instanceof RecEntityDestroy recEntityDestroy) {
                List<Recordable> recordables = super.findRecordables(recordablesBetween, (rec) -> rec instanceof RecEntitySpawn recEntitySpawn && recEntityDestroy.entityIds().contains(recEntitySpawn.entityId()));

                for (Recordable spawnRecordable : recordables) {
                    if (spawnRecordable instanceof RecEntitySpawn recEntitySpawn) {
                        entityCache.spawnEntity(recEntitySpawn);
                    }
                }
            }
        }

        session.sendPackets(packets);
    }
}