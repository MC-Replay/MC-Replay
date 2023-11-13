package mc.replay.mappings;

import mc.replay.mappings.loader.EntityMetadataSerializerMappingLoader;
import mc.replay.mappings.loader.EntityTypeMappingLoader;
import mc.replay.mappings.loader.MaterialMappingLoader;
import mc.replay.mappings.objects.EntityMetadataSerializerMapping;
import mc.replay.mappings.objects.EntityTypeMapping;
import mc.replay.mappings.objects.Mapping;
import mc.replay.mappings.objects.MaterialMapping;
import mc.replay.mappings.objects.deserializer.EntityMetadataSerializerMappingDeserializer;
import mc.replay.mappings.objects.deserializer.EntityTypeMappingDeserializer;
import mc.replay.mappings.objects.deserializer.MappingDeserializer;
import mc.replay.mappings.objects.deserializer.MaterialMappingDeserializer;
import org.jetbrains.annotations.NotNull;

public enum MappingType {

    ENTITY_METADATA_SERIALIZER("entity_data_serializers", EntityMetadataSerializerMappingLoader.class, EntityMetadataSerializerMapping.class, new EntityMetadataSerializerMappingDeserializer()),
    ENTITY_TYPE("entities", EntityTypeMappingLoader.class, EntityTypeMapping.class, new EntityTypeMappingDeserializer()),
    MATERIAL("items", MaterialMappingLoader.class, MaterialMapping.class, new MaterialMappingDeserializer());

    private final String fileSuffix;
    private final Class<? extends MappingLoader<?>> mappingLoaderClass;
    private final Class<? extends Mapping> mappingClass;
    private final MappingDeserializer<? extends Mapping> deserializer;

    MappingType(@NotNull String fileSuffix, @NotNull Class<? extends MappingLoader<?>> mappingLoaderClass, @NotNull Class<? extends Mapping> mappingClass, @NotNull MappingDeserializer<? extends Mapping> deserializer) {
        this.fileSuffix = fileSuffix;
        this.mappingLoaderClass = mappingLoaderClass;
        this.mappingClass = mappingClass;
        this.deserializer = deserializer;
    }

    public @NotNull String getFileSuffix() {
        return this.fileSuffix;
    }

    public @NotNull Class<? extends MappingLoader<?>> getMappingLoaderClass() {
        return this.mappingLoaderClass;
    }

    public @NotNull Class<? extends Mapping> getMappingClass() {
        return this.mappingClass;
    }

    public @NotNull MappingDeserializer<? extends Mapping> getDeserializer() {
        return this.deserializer;
    }
}