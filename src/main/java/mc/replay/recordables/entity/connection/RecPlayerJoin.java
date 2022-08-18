package mc.replay.recordables.entity.connection;

import com.mojang.authlib.properties.Property;
import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.EntityRecordable;
import mc.replay.replay.entity.ReplayEntity;
import mc.replay.replay.entity.ReplayNPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public record RecPlayerJoin(EntityId entityId, String name, Property skinTexture,
                            Location location) implements EntityRecordable {

    public static RecPlayerJoin of(EntityId entityId, String name, Property skinTexture, Location location) {
        return new RecPlayerJoin(
                entityId,
                name,
                skinTexture,
                location
        );
    }

    @Override
    public ReplayEntity<ReplayNPC> play(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId) {
        return new ReplayNPC(
                this.entityId.entityId(),
                new ArrayList<>(),
                this.name,
                this.location,
                this.skinTexture
        );
    }

    @Override
    public ReplayEntity<ReplayNPC> jumpInTime(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId, boolean forward) {
        if (forward) {
            return this.play(viewer, replayEntity, entity, entityId);
        } else {
            replayEntity.destroy();
            return null;
        }
    }
}