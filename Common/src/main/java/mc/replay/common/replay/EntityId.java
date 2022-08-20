package mc.replay.common.replay;

import java.util.UUID;

public record EntityId(UUID entityUuid, int entityId) {

    public static EntityId of(UUID entityUuid, int entityId) {
        return new EntityId(entityUuid, entityId);
    }

    public static EntityId of(int entityId) {
        return new EntityId(null, entityId);
    }
}