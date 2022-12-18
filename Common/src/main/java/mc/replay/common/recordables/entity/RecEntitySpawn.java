package mc.replay.common.recordables.entity;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableOther;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.wrapper.entity.EntityWrapper;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public record RecEntitySpawn(EntityId entityId, EntityType entityType, Pos position,
                             Map<Integer, Metadata.Entry<?>> metadata,
                             Vector velocity) implements RecordableOther {

    public static RecEntitySpawn of(EntityId entityId, EntityWrapper entityWrapper) {
        // TODO get all needed information (equipment, etc)
        return new RecEntitySpawn(
                entityId,
                entityWrapper.getType(),
                entityWrapper.getPosition(),
                entityWrapper.getMetadata().getEntries(),
                entityWrapper.getVelocity()
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of();
    }
}