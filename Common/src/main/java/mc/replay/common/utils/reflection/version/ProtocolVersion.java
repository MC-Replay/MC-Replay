package mc.replay.common.utils.reflection.version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum ProtocolVersion {

    MINECRAFT_1_19(759, MinecraftVersion.v1_19),
    MINECRAFT_1_18_2(758, MinecraftVersion.v1_18),
    MINECRAFT_1_18_1(757, MinecraftVersion.v1_18),
    MINECRAFT_1_18(757, MinecraftVersion.v1_18),
    MINECRAFT_1_17_1(756, MinecraftVersion.v1_17),
    MINECRAFT_1_17(755, MinecraftVersion.v1_17),
    MINECRAFT_1_16_5(754, MinecraftVersion.v1_16),
    MINECRAFT_1_16_4(754, MinecraftVersion.v1_16),
    MINECRAFT_1_16_3(753, MinecraftVersion.v1_16),
    MINECRAFT_1_16_2(751, MinecraftVersion.v1_16),
    MINECRAFT_1_16_1(736, MinecraftVersion.v1_16),
    MINECRAFT_1_16(735, MinecraftVersion.v1_16),
    MINECRAFT_1_15_2(578, MinecraftVersion.v1_15),
    MINECRAFT_1_15_1(575, MinecraftVersion.v1_15),
    MINECRAFT_1_15(573, MinecraftVersion.v1_15),
    MINECRAFT_1_14_4(498, MinecraftVersion.v1_14),
    MINECRAFT_1_14_3(490, MinecraftVersion.v1_14),
    MINECRAFT_1_14_2(485, MinecraftVersion.v1_14),
    MINECRAFT_1_14_1(480, MinecraftVersion.v1_14),
    MINECRAFT_1_14(477, MinecraftVersion.v1_14),
    MINECRAFT_1_13_2(404, MinecraftVersion.v1_13),
    MINECRAFT_1_13_1(401, MinecraftVersion.v1_13),
    MINECRAFT_1_13(393, MinecraftVersion.v1_13),
    MINECRAFT_1_12_2(340, MinecraftVersion.v1_12),
    MINECRAFT_1_12_1(338, MinecraftVersion.v1_12),
    MINECRAFT_1_12(335, MinecraftVersion.v1_12),
    MINECRAFT_1_11_2(316, MinecraftVersion.v1_11),
    MINECRAFT_1_11_1(316, MinecraftVersion.v1_11),
    MINECRAFT_1_11(315, MinecraftVersion.v1_11),
    MINECRAFT_1_10(210, MinecraftVersion.v1_10),
    MINECRAFT_1_9_4(110, MinecraftVersion.v1_9),
    MINECRAFT_1_9_2(109, MinecraftVersion.v1_9),
    MINECRAFT_1_9_1(108, MinecraftVersion.v1_9),
    MINECRAFT_1_9(107, MinecraftVersion.v1_9),
    MINECRAFT_1_8(47, MinecraftVersion.v1_8),
    MINECRAFT_1_7_6(5, MinecraftVersion.v1_7),
    MINECRAFT_1_7_2(4, MinecraftVersion.v1_7),
    UNKNOWN(0, null);

    private final int number;
    private final MinecraftVersion minecraftVersion;

    public boolean isLowerOrEqual(@NotNull ProtocolVersion version) {
        return this.number <= version.getNumber();
    }

    public boolean isLower(@NotNull ProtocolVersion version) {
        return this.number < version.getNumber();
    }

    public boolean isHigherOrEqual(@NotNull ProtocolVersion version) {
        return this.number >= version.getNumber();
    }

    public boolean isHigher(@NotNull ProtocolVersion version) {
        return this.number > version.getNumber();
    }

    public boolean isEqual(@NotNull ProtocolVersion version) {
        return this.number == version.getNumber();
    }

    private static final Map<Integer, ProtocolVersion> PROTOCOL_VERSIONS = new LinkedHashMap<>();

    static {
        for (ProtocolVersion version : values()) {
            PROTOCOL_VERSIONS.put(version.number, version);
        }
    }

    public static ProtocolVersion getVersion(int versionNumber) {
        ProtocolVersion protocolVersion = PROTOCOL_VERSIONS.get(versionNumber);
        return (protocolVersion == null) ? UNKNOWN : protocolVersion;
    }

    public static ProtocolVersion matchVersion(int versionNumber) {
        ProtocolVersion protocolVersion = getVersion(versionNumber);

        if (protocolVersion == UNKNOWN) {
            for (ProtocolVersion version : values()) {
                if (version.getNumber() > versionNumber) continue;

                return version;
            }
        }

        return protocolVersion;
    }

    public static ProtocolVersion getByVersionString(@NotNull String versionString) {
        for (ProtocolVersion protocolVersion : values()) {
            if (protocolVersion == UNKNOWN) continue;

            String version = protocolVersion.name().substring(10).replaceAll("_", ".");
            if (version.equalsIgnoreCase(versionString)) {
                return protocolVersion;
            }
        }

        return UNKNOWN;
    }
}