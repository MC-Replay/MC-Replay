package mc.replay.nms.v1_16_R3.recordable.block;

import mc.replay.common.recordables.RecordableBlock;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_16_R3.SectionPosition;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

public record RecMultiBlockChange(Vector blockPosition, short[] blockIndexes, BlockData[] blockData,
                                  boolean flag) implements RecordableBlock {

    public static RecMultiBlockChange of(Vector blockPosition, short[] blockIndexes, BlockData[] blockData, boolean flag) {
        return new RecMultiBlockChange(
                blockPosition,
                blockIndexes,
                blockData,
                flag
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(Function<Void, Void> function) {
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

        return List.of(packet);
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