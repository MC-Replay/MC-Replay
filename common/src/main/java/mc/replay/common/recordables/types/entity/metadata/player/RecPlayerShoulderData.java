package mc.replay.common.recordables.types.entity.metadata.player;

import com.github.steveice10.opennbt.tag.builtin.Tag;
import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.BOOLEAN;
import static mc.replay.packetlib.network.ReplayByteBuffer.NBT;

public record RecPlayerShoulderData(EntityId entityId, boolean right, Tag shoulderData) implements Recordable {

    public RecPlayerShoulderData(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(BOOLEAN),
                reader.read(NBT)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(BOOLEAN, this.right);
        writer.write(NBT, this.shoulderData);
    }
}