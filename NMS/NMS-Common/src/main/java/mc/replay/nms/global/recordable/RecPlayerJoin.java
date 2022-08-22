package mc.replay.nms.global.recordable;

import com.mojang.authlib.properties.Property;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.replay.ReplayEntity;
import mc.replay.common.replay.ReplayNPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public record RecPlayerJoin(EntityId entityId, String name, Property skinTexture,
                            Location location) implements RecordableEntity {

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