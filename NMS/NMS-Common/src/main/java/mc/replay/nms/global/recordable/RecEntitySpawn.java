package mc.replay.nms.global.recordable;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableOther;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntitySpawn(EntityId entityId, EntityType entityType, Location location) implements RecordableOther {

    public static RecEntitySpawn of(EntityId entityId, EntityType entityType, Location location) {
        // TODO get all needed information (equipment, etc)
        return new RecEntitySpawn(
                entityId,
                entityType,
                location
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of();
    }
}