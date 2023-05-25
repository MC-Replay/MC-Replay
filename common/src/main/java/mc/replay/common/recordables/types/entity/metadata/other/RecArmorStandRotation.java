package mc.replay.common.recordables.types.entity.metadata.other;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import static mc.replay.common.recordables.RecordableBufferTypes.ENTITY_VECTOR_ROTATION;

public record RecArmorStandRotation(EntityId entityId, RotationType type, Vector rotation) implements Recordable {

    public RecArmorStandRotation(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.readEnum(RotationType.class),
                reader.read(ENTITY_VECTOR_ROTATION)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.writeEnum(RotationType.class, this.type);
        writer.write(ENTITY_VECTOR_ROTATION, this.rotation);
    }

    public enum RotationType {
        HEAD,
        BODY,
        LEFT_ARM,
        RIGHT_ARM,
        LEFT_LEG,
        RIGHT_LEG
    }
}