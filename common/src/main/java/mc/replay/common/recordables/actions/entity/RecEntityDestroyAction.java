package mc.replay.common.recordables.actions.entity;

import mc.replay.common.recordables.actions.internal.InternalEntityRecordableAction;
import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.common.replay.IReplayEntityProcessor;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public record RecEntityDestroyAction() implements InternalEntityRecordableAction<RecEntityDestroy> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityDestroy recordable, @UnknownNullability IReplayEntityProcessor data) {
        data.destroyEntity(recordable);
        return List.of();
    }
}