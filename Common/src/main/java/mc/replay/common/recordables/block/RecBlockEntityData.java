package mc.replay.common.recordables.block;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import mc.replay.common.recordables.RecordableBlock;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.ClientboundBlockEntityDataPacket;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecBlockEntityData(Vector blockPosition, int action, CompoundTag data) implements RecordableBlock {

    public static RecBlockEntityData of(Vector blockPosition, int action, CompoundTag data) {
        return new RecBlockEntityData(
                blockPosition,
                action,
                data
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new ClientboundBlockEntityDataPacket(
                this.blockPosition,
                this.action,
                this.data
        ));
    }
}