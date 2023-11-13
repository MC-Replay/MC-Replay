package mc.replay.api.data.entity;

import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RMetadata {

    protected final Metadata metadata;

    public RMetadata(@NotNull Metadata metadata) {
        this.metadata = metadata;
    }

    public @NotNull Metadata getMetadata() {
        return this.metadata;
    }

    public @NotNull Map<Integer, Metadata.Entry<?>> getEntries() {
        return this.metadata.getEntries();
    }
}