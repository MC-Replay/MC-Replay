package mc.replay.recordables.block;

import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockBreak;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public record RecBlockBreak(Vector blockPosition, BlockData blockData, String digType,
                            boolean instaBreak) implements BlockRecordable {

    public static RecBlockBreak of(Vector blockPosition, BlockData blockData, String digType, boolean instaBreak) {
        return new RecBlockBreak(
                blockPosition,
                blockData,
                digType,
                instaBreak
        );
    }

    @Override
    public void play(Player viewer) {
        MinecraftPlayerNMS.sendPacket(viewer, this.createPacket());
    }

    private Object createPacket() {
        BlockPosition position = new BlockPosition(
                this.blockPosition.getBlockX(),
                this.blockPosition.getBlockY(),
                this.blockPosition.getBlockZ()
        );

        IBlockData blockData = ((CraftBlockData) this.blockData).getState();
        PacketPlayInBlockDig.EnumPlayerDigType enumPlayerDigType = PacketPlayInBlockDig.EnumPlayerDigType.valueOf(this.digType);

        return new PacketPlayOutBlockBreak(position, blockData, enumPlayerDigType, this.instaBreak, "");
    }
}