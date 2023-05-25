package mc.replay.common.recordables.types.entity.metadata.villager;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.VILLAGER_DATA;

public record RecVillagerData(EntityId entityId, int[] data) implements Recordable {

    public RecVillagerData(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(VILLAGER_DATA)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(VILLAGER_DATA, this.data);
    }
}