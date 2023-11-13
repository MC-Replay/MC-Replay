package mc.replay.mappings.mapped;

import mc.replay.mappings.MappingId;
import mc.replay.mappings.MappingKey;
import mc.replay.mappings.MappingsLoader;
import mc.replay.mappings.objects.EntityTypeMapping;
import mc.replay.packetlib.utils.ProtocolVersion;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public final class MappedEntityType {

    private final EntityType entityType;
    private final EntityTypeMapping mapping;

    public MappedEntityType(@NotNull ProtocolVersion protocolVersion, @NotNull EntityType entityType) {
        this.entityType = entityType;
        this.mapping = MappingsLoader.getEntityMappingsByKey(protocolVersion).get(MappingKey.from(entityType.getKey()));
    }

    public MappedEntityType(@NotNull EntityType entityType) {
        this(ProtocolVersion.getServerVersion(), entityType);
    }

    public MappedEntityType(@NotNull ProtocolVersion protocolVersion, int typeId) {
        this.mapping = MappingsLoader.getEntityMappingsById(protocolVersion).get(MappingId.from(typeId));
        this.entityType = EntityType.fromName(this.mapping.key());
    }

    public MappedEntityType(int typeId) {
        this(ProtocolVersion.getServerVersion(), typeId);
    }

    public @NotNull EntityType bukkit() {
        return this.entityType;
    }

    public @NotNull EntityTypeMapping mapping() {
        return this.mapping;
    }
}