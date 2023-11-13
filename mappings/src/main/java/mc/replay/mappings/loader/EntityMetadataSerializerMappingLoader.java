package mc.replay.mappings.loader;

import com.google.gson.Gson;
import mc.replay.api.utils.Pair;
import mc.replay.mappings.MappingId;
import mc.replay.mappings.MappingKey;
import mc.replay.mappings.MappingLoader;
import mc.replay.mappings.objects.EntityMetadataSerializerMapping;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class EntityMetadataSerializerMappingLoader implements MappingLoader<EntityMetadataSerializerMapping> {

    @Override
    public Map<EntityMetadataSerializerMapping, Pair<MappingKey, MappingId>> loadMappings(@NotNull Gson gson, @NotNull InputStreamReader reader) {
        EntityMetadataSerializerMapping[] mappings = gson.fromJson(reader, EntityMetadataSerializerMapping[].class);

        Map<EntityMetadataSerializerMapping, Pair<MappingKey, MappingId>> map = new HashMap<>();

        for (EntityMetadataSerializerMapping mapping : mappings) {
            MappingKey mappingKey = MappingKey.from(mapping.type().name());
            MappingId mappingId = MappingId.from(mapping.id());

            map.put(mapping, new Pair<>(mappingKey, mappingId));
        }

        return map;
    }
}