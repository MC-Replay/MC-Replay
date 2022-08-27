package mc.replay.nms.v1_16_R3.recordable.block;

import mc.replay.common.recordables.RecordableBlock;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.PacketPlayOutTileEntityData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecTileEntityData(Vector blockPosition, int action,
                                NBTTagCompound nbtTagCompound) implements RecordableBlock {

    public static RecTileEntityData of(Vector blockPosition, int action, NBTTagCompound nbtTagCompound) {
        return new RecTileEntityData(
                blockPosition,
                action,
                nbtTagCompound
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Void, Void> function) {
        BlockPosition position = new BlockPosition(
                this.blockPosition.getBlockX(),
                this.blockPosition.getBlockY(),
                this.blockPosition.getBlockZ()
        );

        return List.of(new PacketPlayOutTileEntityData(position, this.action, this.nbtTagCompound));
    }
}