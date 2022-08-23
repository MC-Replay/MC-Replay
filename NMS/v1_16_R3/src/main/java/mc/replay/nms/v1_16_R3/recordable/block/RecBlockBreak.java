package mc.replay.nms.v1_16_R3.recordable.block;

import mc.replay.common.recordables.RecordableBlock;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockBreak;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecBlockBreak(Vector blockPosition, BlockData blockData, String digType,
                            boolean instaBreak) implements RecordableBlock {

    public static RecBlockBreak of(Vector blockPosition, BlockData blockData, String digType, boolean instaBreak) {
        return new RecBlockBreak(
                blockPosition,
                blockData,
                digType,
                instaBreak
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(Function<Void, Void> function) {
        BlockPosition position = new BlockPosition(
                this.blockPosition.getBlockX(),
                this.blockPosition.getBlockY(),
                this.blockPosition.getBlockZ()
        );

        IBlockData blockData = ((CraftBlockData) this.blockData).getState();
        PacketPlayInBlockDig.EnumPlayerDigType enumPlayerDigType = PacketPlayInBlockDig.EnumPlayerDigType.valueOf(this.digType);

        return List.of(new PacketPlayOutBlockBreak(position, blockData, enumPlayerDigType, this.instaBreak, ""));
    }
}