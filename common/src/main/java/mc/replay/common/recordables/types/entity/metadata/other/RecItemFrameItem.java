package mc.replay.common.recordables.types.entity.metadata.other;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.wrapper.item.ItemWrapper;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.ITEM;

public record RecItemFrameItem(EntityId entityId, ItemWrapper item) implements Recordable {

    public RecItemFrameItem(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                new ItemWrapper(reader.read(ITEM))
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(ITEM, this.item);
    }
}