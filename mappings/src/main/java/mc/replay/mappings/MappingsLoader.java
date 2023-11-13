package mc.replay.mappings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mc.replay.api.utils.Pair;
import mc.replay.mappings.objects.EntityMetadataSerializerMapping;
import mc.replay.mappings.objects.EntityTypeMapping;
import mc.replay.mappings.objects.Mapping;
import mc.replay.mappings.objects.MaterialMapping;
import mc.replay.packetlib.utils.ProtocolVersion;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class MappingsLoader {

    private MappingsLoader() {
    }

    private static final Map<MappingType, Map<MappingsVersion, Map<MappingKey, Mapping>>> MAPPINGS_BY_KEY = new HashMap<>();
    private static final Map<MappingType, Map<MappingsVersion, Map<MappingId, Mapping>>> MAPPINGS_BY_ID = new HashMap<>();

    public static void initialize(JavaPlugin plugin) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        for (MappingType type : MappingType.values()) {
            gsonBuilder.registerTypeAdapter(type.getMappingClass(), type.getDeserializer());
        }
        Gson gson = gsonBuilder.create();

        ClassLoader classLoader = plugin.getClass().getClassLoader();

        for (MappingsVersion version : MappingsVersion.values()) {
            String versionString = version.name().toLowerCase().substring(1);
            String directory = "mappings/mappings_" + versionString + "/";

            for (MappingType mappingType : MappingType.values()) {
                String fileName = versionString + "_" + mappingType.getFileSuffix() + ".json";

                try (InputStream inputStream = classLoader.getResourceAsStream(directory + fileName)) {
                    if (inputStream == null) continue;

                    try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                        MappingLoader<? extends Mapping> loader = mappingType.getMappingLoaderClass().getConstructor().newInstance();
                        Map<? extends Mapping, Pair<MappingKey, MappingId>> map = loader.loadMappings(gson, reader);

                        MAPPINGS_BY_KEY.putIfAbsent(mappingType, new HashMap<>());
                        MAPPINGS_BY_KEY.get(mappingType).put(version, new HashMap<>());

                        MAPPINGS_BY_ID.putIfAbsent(mappingType, new HashMap<>());
                        MAPPINGS_BY_ID.get(mappingType).put(version, new HashMap<>());

                        for (Map.Entry<? extends Mapping, Pair<MappingKey, MappingId>> entry : map.entrySet()) {
                            Pair<MappingKey, MappingId> value = entry.getValue();

                            MAPPINGS_BY_KEY.get(mappingType).get(version).put(value.getKey(), entry.getKey());
                            MAPPINGS_BY_ID.get(mappingType).get(version).put(value.getValue(), entry.getKey());
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public static @NotNull Map<MappingKey, EntityMetadataSerializerMapping> getEntityMetadataSerializerMappingsByKey(@NotNull ProtocolVersion version) {
        return getMappingsByKey(MappingType.ENTITY_METADATA_SERIALIZER, version);
    }

    public static @NotNull Map<MappingId, EntityMetadataSerializerMapping> getEntityMetadataSerializerMappingsById(@NotNull ProtocolVersion version) {
        return getMappingsById(MappingType.ENTITY_METADATA_SERIALIZER, version);
    }

    public static @NotNull Map<MappingKey, EntityTypeMapping> getEntityMappingsByKey(@NotNull ProtocolVersion version) {
        return getMappingsByKey(MappingType.ENTITY_TYPE, version);
    }

    public static @NotNull Map<MappingId, EntityTypeMapping> getEntityMappingsById(@NotNull ProtocolVersion version) {
        return getMappingsById(MappingType.ENTITY_TYPE, version);
    }

    public static @NotNull Map<MappingKey, MaterialMapping> getMaterialMappingsByKey(@NotNull ProtocolVersion version) {
        return getMappingsByKey(MappingType.MATERIAL, version);
    }

    public static @NotNull Map<MappingId, MaterialMapping> getMaterialMappingsById(@NotNull ProtocolVersion version) {
        return getMappingsById(MappingType.MATERIAL, version);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Mapping> Map<MappingKey, T> getMappingsByKey(MappingType mappingType, ProtocolVersion protocolVersion) {
        MappingsVersion version = MappingsVersion.fromProtocolVersion(protocolVersion);

        Map<MappingsVersion, Map<MappingKey, Mapping>> map = MAPPINGS_BY_KEY.get(mappingType);
        if (map == null) {
            throw new IllegalStateException("Couldn't find mappings for " + mappingType.name());
        }

        Map<MappingKey, T> mappings = (Map<MappingKey, T>) map.get(version);
        if (mappings == null) {
            throw new IllegalStateException("Couldn't find mappings for " + version.name());
        }

        return mappings;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Mapping> Map<MappingId, T> getMappingsById(MappingType mappingType, ProtocolVersion protocolVersion) {
        MappingsVersion version = MappingsVersion.fromProtocolVersion(protocolVersion);

        Map<MappingsVersion, Map<MappingId, Mapping>> map = MAPPINGS_BY_ID.get(mappingType);
        if (map == null) {
            throw new IllegalStateException("Couldn't find mappings for " + mappingType.name());
        }

        Map<MappingId, T> mappings = (Map<MappingId, T>) map.get(version);
        if (mappings == null) {
            throw new IllegalStateException("Couldn't find mappings for " + version.name());
        }

        return mappings;
    }
}