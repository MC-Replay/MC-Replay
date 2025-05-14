package mc.replay.common.recordables.types.entity.metadata.player;

import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.internal.EntityStateRecordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.BOOLEAN;

public record RecPlayerDisplayedSkinParts(EntityId entityId, boolean cape, boolean jacket, boolean leftSleeve,
                                          boolean rightSleeve, boolean leftPants, boolean rightPants,
                                          boolean hat) implements EntityStateRecordable {

    public RecPlayerDisplayedSkinParts(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(BOOLEAN),
                reader.read(BOOLEAN),
                reader.read(BOOLEAN),
                reader.read(BOOLEAN),
                reader.read(BOOLEAN),
                reader.read(BOOLEAN),
                reader.read(BOOLEAN)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(BOOLEAN, this.cape);
        writer.write(BOOLEAN, this.jacket);
        writer.write(BOOLEAN, this.leftSleeve);
        writer.write(BOOLEAN, this.rightSleeve);
        writer.write(BOOLEAN, this.leftPants);
        writer.write(BOOLEAN, this.rightPants);
        writer.write(BOOLEAN, this.hat);
    }
}