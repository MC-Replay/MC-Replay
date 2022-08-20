package mc.replay.recordables.block;

import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockChange;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public record RecBlockChange(Vector blockPosition, BlockData blockData) implements BlockRecordable {

    public static RecBlockChange of(Vector blockPosition, BlockData blockData) {
        return new RecBlockChange(
                blockPosition,
                blockData
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

        return new PacketPlayOutBlockChange(position, ((CraftBlockData) this.blockData).getState());
    }
}