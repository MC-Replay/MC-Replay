package mc.replay.api.recordables.data;

import mc.replay.wrapper.entity.EntityWrapper;

public record RecordableEntityData(int entityId, EntityWrapper entity) {
}