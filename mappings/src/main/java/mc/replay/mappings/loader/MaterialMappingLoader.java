package mc.replay.mappings.loader;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import mc.replay.api.utils.Pair;
import mc.replay.mappings.MappingId;
import mc.replay.mappings.MappingKey;
import mc.replay.mappings.MappingLoader;
import mc.replay.mappings.objects.MaterialMapping;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

public final class MaterialMappingLoader implements MappingLoader<MaterialMapping> {

    @Override
    public Map<MaterialMapping, Pair<MappingKey, MappingId>> loadMappings(@NotNull Gson gson, @NotNull InputStreamReader reader) {
        Type type = new TypeToken<Map<String, MaterialMapping>>() {}.getType();
        Map<String, MaterialMapping> map = gson.fromJson(reader, type);

        return this.convertMap(map, (mapping) -> new MappingId(mapping.id()));
    }
}