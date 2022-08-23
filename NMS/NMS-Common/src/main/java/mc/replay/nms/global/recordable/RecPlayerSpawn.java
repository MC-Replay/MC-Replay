package mc.replay.nms.global.recordable;

import com.mojang.authlib.properties.Property;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableOther;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecPlayerSpawn(EntityId entityId, String name, Property skinTexture,
                             Location location) implements RecordableOther {

    public static RecPlayerSpawn of(EntityId entityId, String name, Property skinTexture, Location location) {
        // TODO get all needed information (equipment, etc)
        return new RecPlayerSpawn(
                entityId,
                name,
                skinTexture,
                location
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(Function<Void, Void> function) {
        return List.of();
    }
}