package mc.replay.mappings;

import org.jetbrains.annotations.NotNull;

public record MappingId(int id) {

    public static @NotNull MappingId from(int id) {
        return new MappingId(id);
    }
}