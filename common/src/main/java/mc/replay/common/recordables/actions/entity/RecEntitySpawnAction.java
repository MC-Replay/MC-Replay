package mc.replay.common.recordables.actions.entity;

import mc.replay.common.recordables.actions.internal.InternalEntityRecordableAction;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.common.replay.IReplayEntityProvider;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public record RecEntitySpawnAction() implements InternalEntityRecordableAction<RecEntitySpawn> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntitySpawn recordable, @UnknownNullability IReplayEntityProvider provider) {
        provider.spawnEntity(recordable);
        return List.of();
    }
}