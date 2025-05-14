package mc.replay.common.recordables.types.entity.miscellaneous;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.data.entity.PlayerHand;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

public record RecEntitySwingHandAnimation(EntityId entityId, PlayerHand hand) implements Recordable {

    public RecEntitySwingHandAnimation(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.readEnum(PlayerHand.class)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.writeEnum(PlayerHand.class, this.hand);
    }
}