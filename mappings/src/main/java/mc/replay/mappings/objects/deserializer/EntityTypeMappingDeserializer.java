package mc.replay.mappings.objects.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import mc.replay.mappings.objects.EntityTypeMapping;

import java.lang.reflect.Type;

public final class EntityTypeMappingDeserializer implements MappingDeserializer<EntityTypeMapping> {

    @Override
    public EntityTypeMapping deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String key = object.get("mojangName").getAsString().toLowerCase();
        int id = object.get("id").getAsInt();
        EntityTypeMapping.PacketType packetType = context.deserialize(object.get("packetType"), EntityTypeMapping.PacketType.class);
        return new EntityTypeMapping(key, id, packetType);
    }
}