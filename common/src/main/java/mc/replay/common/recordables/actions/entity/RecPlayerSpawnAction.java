package mc.replay.common.recordables.actions.entity;

import mc.replay.common.recordables.actions.internal.InternalEntityRecordableAction;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import mc.replay.common.replay.IReplayEntityProvider;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public record RecPlayerSpawnAction() implements InternalEntityRecordableAction<RecPlayerSpawn> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecPlayerSpawn recordable, @UnknownNullability IReplayEntityProvider provider) {
        provider.spawnPlayer(recordable);
        return List.of();
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPacketsForwards(@NotNull RecPlayerSpawn recordable, @UnknownNullability IReplayEntityProvider provider) {
        return List.of();
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPacketsBackwards(@NotNull RecPlayerSpawn recordable, @UnknownNullability IReplayEntityProvider provider) {
        return List.of();
    }
}