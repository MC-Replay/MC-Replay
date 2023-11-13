package mc.replay.mappings.objects.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import mc.replay.mappings.objects.EntityMetadataSerializerMapping;

import java.lang.reflect.Type;

public final class EntityMetadataSerializerMappingDeserializer implements MappingDeserializer<EntityMetadataSerializerMapping> {

    @Override
    public EntityMetadataSerializerMapping deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int id = object.get("id").getAsInt();
        EntityMetadataSerializerMapping.Type serializerType = context.deserialize(object.get("type"), EntityMetadataSerializerMapping.Type.class);
        return new EntityMetadataSerializerMapping(id, serializerType);
    }
}