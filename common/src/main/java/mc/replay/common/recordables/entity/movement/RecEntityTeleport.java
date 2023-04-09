package mc.replay.common.recordables.entity.movement;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableBufferTypes;
import mc.replay.common.recordables.interfaces.RecordableEntity;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityTeleportPacket;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public record RecEntityTeleport(EntityId entityId, Pos pos, boolean onGround) implements RecordableEntity {

    public RecEntityTeleport(EntityId entityId, Location to, boolean onGround) {
        this(
                entityId,
                Pos.from(to),
                onGround
        );
    }

    public RecEntityTeleport(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(RecordableBufferTypes.ENTITY_POSITION_WITH_ROTATION),
                reader.read(BOOLEAN)
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());
        if (data == null) return new ArrayList<>();

        return List.of(new ClientboundEntityTeleportPacket(
                data.entityId(),
                this.pos,
                this.onGround
        ));
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(RecordableBufferTypes.ENTITY_POSITION_WITH_ROTATION, this.pos);
        writer.write(BOOLEAN, this.onGround);
    }
}