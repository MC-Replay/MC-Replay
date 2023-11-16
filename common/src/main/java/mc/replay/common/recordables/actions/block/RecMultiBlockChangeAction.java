package mc.replay.common.recordables.actions.block;

import mc.replay.common.recordables.actions.internal.InternalBlockRecordableAction;
import mc.replay.common.recordables.types.block.RecMultiBlockChange;
import mc.replay.common.replay.IReplayBlockProvider;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundMultiBlockChangePacket;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public final class RecMultiBlockChangeAction implements InternalBlockRecordableAction<RecMultiBlockChange> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecMultiBlockChange recordable, @UnknownNullability IReplayBlockProvider data) {
        Vector chunkSectionPosition = this.readChunkSectionPosition(recordable.chunkSectorPosition());
        long[] blocks = recordable.blocks();

        for (long block : blocks) {
            short position = (short) ((int) (block & 4095L));

            int relativeX = (chunkSectionPosition.getBlockX() << 4) + (position >>> 8 & 15);
            int relativeY = (chunkSectionPosition.getBlockY() << 4) + (position & 15);
            int relativeZ = (chunkSectionPosition.getBlockZ() << 4) + (position >>> 4 & 15);

            data.setBlockDefault(new Vector(relativeX, relativeY, relativeZ));
        }

        return List.of(
                new ClientboundMultiBlockChangePacket(
                        recordable.chunkSectorPosition(),
                        recordable.suppressLightUpdates(),
                        recordable.blocks()
                )
        );
    }

    private Vector readChunkSectionPosition(long chunkSectionPosition) {
        int x = (int) (chunkSectionPosition >> 42);
        int y = (int) (chunkSectionPosition << 44 >> 44);
        int z = (int) (chunkSectionPosition << 22 >> 42);

        return new Vector(x, y, z);
    }
}