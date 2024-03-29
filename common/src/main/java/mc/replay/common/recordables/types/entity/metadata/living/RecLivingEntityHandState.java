package mc.replay.common.recordables.types.entity.metadata.living;

import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.internal.EntityStateRecordable;
import mc.replay.packetlib.data.entity.PlayerHand;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.BOOLEAN;

public record RecLivingEntityHandState(EntityId entityId, boolean handActive, PlayerHand activeHand,
                                       boolean inSpinAttack) implements EntityStateRecordable {

    public RecLivingEntityHandState(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(BOOLEAN),
                reader.readEnum(PlayerHand.class),
                reader.read(BOOLEAN)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(BOOLEAN, this.handActive);
        writer.writeEnum(PlayerHand.class, this.activeHand);
        writer.write(BOOLEAN, this.inSpinAttack);
    }
}