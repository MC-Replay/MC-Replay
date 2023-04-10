package mc.replay.api.recording.recordables.entity;

import mc.replay.wrapper.entity.EntityWrapper;

public record RecordableEntityData(int entityId, EntityWrapper entity) {
}