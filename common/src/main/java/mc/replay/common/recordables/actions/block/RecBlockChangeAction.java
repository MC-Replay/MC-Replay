package mc.replay.common.recordables.actions.block;

import mc.replay.common.recordables.actions.internal.InternalBlockRecordableAction;
import mc.replay.common.recordables.types.block.RecBlockChange;
import mc.replay.common.replay.IReplayBlockProvider;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundBlockChangePacket;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public final class RecBlockChangeAction implements InternalBlockRecordableAction<RecBlockChange> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecBlockChange recordable, @UnknownNullability IReplayBlockProvider data) {
        data.setBlockDefault(new Vector(recordable.blockPosition().getBlockX(), recordable.blockPosition().getBlockY(), recordable.blockPosition().getBlockZ()));

        return List.of(
                new ClientboundBlockChangePacket(
                        recordable.blockPosition(),
                        recordable.blockStateId()
                )
        );
    }
}