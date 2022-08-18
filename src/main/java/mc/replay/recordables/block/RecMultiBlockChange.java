package mc.replay.recordables.block;

import mc.replay.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_16_R3.SectionPosition;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;

public record RecMultiBlockChange(Vector blockPosition, short[] blockIndexes, BlockData[] blockData,
                                  boolean flag) implements BlockRecordable {

    public static RecMultiBlockChange of(Vector blockPosition, short[] blockIndexes, BlockData[] blockData, boolean flag) {
        return new RecMultiBlockChange(
                blockPosition,
                blockIndexes,
                blockData,
                flag
        );
    }

    @Override
    public void play(Player viewer) {
        MinecraftPlayerNMS.sendPacket(viewer, this.createPacket());
    }

    private Object createPacket() {
        SectionPosition position = SectionPosition.a(
                this.blockPosition.getBlockX(),
                this.blockPosition.getBlockY(),
                this.blockPosition.getBlockZ()
        );

        IBlockData[] blockData = new IBlockData[this.blockData.length];
        for (int i = 0; i < this.blockData.length; i++) {
            blockData[i] = ((CraftBlockData) this.blockData[i]).getState();
        }

        PacketPlayOutMultiBlockChange packet = new PacketPlayOutMultiBlockChange();

        this.setField(packet, "a", position);
        this.setField(packet, "b", this.blockIndexes);
        this.setField(packet, "c", blockData);
        this.setField(packet, "d", this.flag);

        return packet;
    }

    private void setField(Object object, String name, Object data) {
        try {
            Field declaredField = object.getClass().getDeclaredField(name);
            declaredField.setAccessible(true);

            declaredField.set(object, data);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}