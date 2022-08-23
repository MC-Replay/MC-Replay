package mc.replay.nms.v1_16_R3.recordable.world;

import mc.replay.common.recordables.RecordableWorldEvent;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecWorldEvent(int effectId, Vector position, int data,
                            boolean disableRelativeVolume) implements RecordableWorldEvent {

    public static RecWorldEvent of(int effectId, Vector position, int data, boolean disableRelativeVolume) {
        return new RecWorldEvent(
                effectId,
                position,
                data,
                disableRelativeVolume
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(Function<Void, Void> function) {
        BlockPosition position = new BlockPosition(
                this.position.getBlockX(),
                this.position.getBlockY(),
                this.position.getBlockZ()
        );

        return List.of(new PacketPlayOutWorldEvent(
                this.effectId,
                position,
                this.data,
                this.disableRelativeVolume
        ));
    }
}