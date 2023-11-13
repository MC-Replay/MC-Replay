package mc.replay.mappings.mapped;

import mc.replay.mappings.MappingId;
import mc.replay.mappings.MappingKey;
import mc.replay.mappings.MappingsLoader;
import mc.replay.mappings.objects.MaterialMapping;
import mc.replay.packetlib.utils.ProtocolVersion;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public final class MappedMaterial {

    private final Material material;
    private final MaterialMapping mapping;

    public MappedMaterial(@NotNull ProtocolVersion protocolVersion, @NotNull Material material) {
        this.material = material;
        this.mapping = MappingsLoader.getMaterialMappingsByKey(protocolVersion).get(MappingKey.from(material.getKey()));
    }

    public MappedMaterial(@NotNull Material material) {
        this(ProtocolVersion.getServerVersion(), material);
    }

    public MappedMaterial(@NotNull ProtocolVersion protocolVersion, int typeId) {
        this.mapping = MappingsLoader.getMaterialMappingsById(protocolVersion).getOrDefault(MappingId.from(typeId), new MaterialMapping(null, -1));
        this.material = this.mapping.key() == null ? Material.AIR : Material.matchMaterial(this.mapping.key());
    }

    public MappedMaterial(int typeId) {
        this(ProtocolVersion.getServerVersion(), typeId);
    }

    public @NotNull Material bukkit() {
        return this.material;
    }

    public @NotNull MaterialMapping mapping() {
        return this.mapping;
    }
}