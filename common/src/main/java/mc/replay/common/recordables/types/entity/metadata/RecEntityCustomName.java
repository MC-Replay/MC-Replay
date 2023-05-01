package mc.replay.common.recordables.types.entity.metadata;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.COMPONENT;

public record RecEntityCustomName(EntityId entityId, Component customName) implements Recordable {

    public RecEntityCustomName(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.readOptional(COMPONENT)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.writeOptional(COMPONENT, (this.customName == null || this.customName.equals(Component.empty()) ? null : this.customName));
    }
}