package mc.replay.nms.v1_16_R3.dispatcher.packet.block;

import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.v1_16_R3.recordable.block.RecBlockAction;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockAction;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

public final class BlockActionPacketOutConverter implements DispatcherPacketOut<PacketPlayOutBlockAction> {

    @Override
    public @Nullable List<Recordable> getRecordables(PacketPlayOutBlockAction packet) {
        try {
            Field positionField = packet.getClass().getDeclaredField("a");
            positionField.setAccessible(true);

            BlockPosition position = (BlockPosition) positionField.get(packet);
            Vector blockPosition = new Vector(
                    position.getX(),
                    position.getY(),
                    position.getZ()
            );

            int actionId = JavaReflections.getField(packet.getClass(), "b", int.class).get(packet);
            int actionParam = JavaReflections.getField(packet.getClass(), "c", int.class).get(packet);

            Field blockField = packet.getClass().getDeclaredField("d");
            blockField.setAccessible(true);

            Block blockObject = (Block) blockField.get(packet);
            int blockId = IRegistry.BLOCK.a(blockObject);

            return List.of(RecBlockAction.of(blockPosition, blockId, actionId, actionParam));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}