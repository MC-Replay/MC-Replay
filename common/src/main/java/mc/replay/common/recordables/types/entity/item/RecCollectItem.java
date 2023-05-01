package mc.replay.common.recordables.types.entity.item;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.VAR_INT;

public record RecCollectItem(EntityId collectedEntityId, EntityId collectorEntityid,
                             int pickupItemCount) implements Recordable {

    public RecCollectItem(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                new EntityId(reader),
                reader.read(VAR_INT)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.collectedEntityId);
        writer.write(this.collectorEntityid);
        writer.write(VAR_INT, this.pickupItemCount);
    }
}