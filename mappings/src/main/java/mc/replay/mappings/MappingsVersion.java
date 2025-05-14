package mc.replay.mappings;

import mc.replay.packetlib.utils.ProtocolVersion;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

enum MappingsVersion {

    V1_16_5(ProtocolVersion.MINECRAFT_1_16_5),
    V1_17(ProtocolVersion.MINECRAFT_1_17_1),
    V1_18_2(ProtocolVersion.MINECRAFT_1_18_2),
    V1_19_4(ProtocolVersion.MINECRAFT_1_19_4),
    V1_20_1(ProtocolVersion.MINECRAFT_1_20_1);

    private final Collection<ProtocolVersion> protocolVersions;

    MappingsVersion(ProtocolVersion... versions) {
        this.protocolVersions = List.of(versions);
    }

    static @NotNull MappingsVersion fromProtocolVersion(@NotNull ProtocolVersion version) {
        if (version == ProtocolVersion.NOT_SUPPORTED) {
            throw new IllegalArgumentException("Protocol version is not supported!");
        }

        for (MappingsVersion mappingsVersion : values()) {
            if (mappingsVersion.protocolVersions.contains(version)) {
                return mappingsVersion;
            }
        }

        throw new IllegalArgumentException("Protocol version is not supported!");
    }
}