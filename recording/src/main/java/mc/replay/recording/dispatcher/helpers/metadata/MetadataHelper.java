package mc.replay.recording.dispatcher.helpers.metadata;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.LivingEntityMetadata;
import mc.replay.wrapper.entity.metadata.PlayerMetadata;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class MetadataHelper {

    private final Map<Class<?>, Collection<Integer>> skippedIndexes = new HashMap<>();
    private final Map<Class<?>, MetadataReader<?>> readers = new HashMap<>();

    public MetadataHelper() {
        this.registerReader(EntityMetadata.class, new EntityMetadataReader());
        this.registerReader(LivingEntityMetadata.class, new LivingEntityMetadataReader());
        this.registerReader(PlayerMetadata.class, new PlayerMetadataReader());
    }

    @SuppressWarnings("unchecked, rawtypes")
    public List<Recordable> read(@NotNull EntityMetadata before, @NotNull EntityMetadata metadata, @NotNull Map<Integer, Metadata.Entry<?>> entries, @NotNull EntityId entityId) {
        for (Map.Entry<Class<?>, Collection<Integer>> entry : this.skippedIndexes.entrySet()) {
            if (entry.getKey().isAssignableFrom(metadata.getClass())) {
                entries.keySet().removeAll(entry.getValue());
            }
        }

        List<Recordable> recordables = new ArrayList<>();

        for (Map.Entry<Class<?>, MetadataReader<?>> entry : this.readers.entrySet()) {
            if (entry.getKey().isAssignableFrom(metadata.getClass())) {
                MetadataReader reader = entry.getValue();
                recordables.addAll(reader.read(before, metadata, entries, entityId));
            }
        }

        return recordables;
    }

    private <M extends EntityMetadata> void registerReader(@NotNull Class<M> clazz, @NotNull MetadataReader<M> reader) {
        this.readers.put(clazz, reader);
        this.skippedIndexes.put(clazz, reader.skippedIndexes());
    }
}