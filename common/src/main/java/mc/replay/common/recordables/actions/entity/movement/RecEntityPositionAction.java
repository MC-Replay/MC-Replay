package mc.replay.common.recordables.actions.entity.movement;

import mc.replay.api.data.entity.IREntity;
import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.common.recordables.types.entity.movement.RecEntityPosition;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityPositionAndRotationPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntityTeleportPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public final class RecEntityPositionAction implements EntityRecordableAction<RecEntityPosition> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntityPosition recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        IREntity entity = data.entity();

        Pos oldPosition = entity.getPosition();
        Pos newPosition = recordable.position()
                .withRotation(oldPosition.yaw(), recordable.pitch());

        entity.setPosition(newPosition);

        if (this.distanceSquared(oldPosition, newPosition) >= 64) {
            return List.of(
                    new ClientboundEntityTeleportPacket(
                            data.entityId(),
                            newPosition,
                            false
                    )
            );
        }

        Pos deltaPosition = newPosition.subtract(oldPosition);

        return List.of(
                new ClientboundEntityPositionAndRotationPacket(
                        data.entityId(),
                        deltaPosition,
                        false
                )
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPacketsTimeJump(@NotNull RecEntityPosition recordable, @UnknownNullability IEntityProvider provider) {
        RecordableEntityData data = provider.getEntity(recordable.entityId().entityId());
        if (data == null) return List.of();

        EntityWrapper entity = data.entity();

        Pos oldPosition = entity.getPosition();
        Pos newPosition = recordable.position()
                .withRotation(oldPosition.yaw(), recordable.pitch());

        entity.setPosition(newPosition);

        return List.of(
                new ClientboundEntityTeleportPacket(
                        data.entityId(),
                        newPosition,
                        false
                )
        );
    }

    private double distanceSquared(Pos pos1, Pos pos2) {
        return this.square(pos1.x() - pos2.x()) + this.square(pos1.y() - pos2.y()) + this.square(pos1.z() - pos2.z());
    }

    private double square(double value) {
        return value * value;
    }
}