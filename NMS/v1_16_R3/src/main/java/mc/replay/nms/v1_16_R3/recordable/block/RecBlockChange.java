package mc.replay.nms.v1_16_R3.recordable.block;

import mc.replay.common.recordables.RecordableBlock;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockChange;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecBlockChange(Vector blockPosition, BlockData blockData) implements RecordableBlock {

    public static RecBlockChange of(Vector blockPosition, BlockData blockData) {
        return new RecBlockChange(
                blockPosition,
                blockData
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(Function<Void, Void> function) {
        BlockPosition position = new BlockPosition(
                this.blockPosition.getBlockX(),
                this.blockPosition.getBlockY(),
                this.blockPosition.getBlockZ()
        );

        return List.of(new PacketPlayOutBlockChange(position, ((CraftBlockData) this.blockData).getState()));
    }
}