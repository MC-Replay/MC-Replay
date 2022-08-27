package mc.replay.nms.v1_16_R3.recordable.block;

import mc.replay.common.recordables.RecordableBlock;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockAction;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecBlockAction(Vector blockPosition, int blockId, int actionId,
                             int actionParam) implements RecordableBlock {

    public static RecBlockAction of(Vector blockPosition, int blockId, int actionId, int actionParam) {
        return new RecBlockAction(
                blockPosition,
                blockId,
                actionId,
                actionParam
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Void, Void> function) {
        BlockPosition position = new BlockPosition(
                this.blockPosition.getBlockX(),
                this.blockPosition.getBlockY(),
                this.blockPosition.getBlockZ()
        );

        Block block = IRegistry.BLOCK.fromId(this.blockId);
        return List.of(new PacketPlayOutBlockAction(position, block, this.actionId, this.actionParam));
    }
}