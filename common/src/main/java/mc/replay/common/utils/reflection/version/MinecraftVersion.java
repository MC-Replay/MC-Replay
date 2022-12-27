package mc.replay.common.utils.reflection.version;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MinecraftVersion {

    v1_7("v1_7", 1),
    v1_8("v1_8", 2),
    v1_9("v1_9", 3),
    v1_10("v1_10", 4),
    v1_11("v1_11", 5),
    v1_12("v1_12", 6),
    v1_13("v1_13", 7),
    v1_14("v1_14", 8),
    v1_15("v1_15", 9),
    v1_16("v1_16", 10),
    v1_17("v1_17", 11),
    v1_18("v1_18", 12),
    v1_19("v1_19", 13);

    private final String protocol;
    private final int id;

    public String getName() {
        return this.protocol.replace("v", "").replaceAll("_", ".");
    }

    public boolean isHigherOrEqual(MinecraftVersion version) {
        return this.id >= version.id;
    }

    public boolean isHigher(MinecraftVersion version) {
        return this.id > version.id;
    }

    public boolean isLowerOrEqual(MinecraftVersion version) {
        return this.id <= version.id;
    }

    public boolean isLower(MinecraftVersion version) {
        return this.id < version.id;
    }
}