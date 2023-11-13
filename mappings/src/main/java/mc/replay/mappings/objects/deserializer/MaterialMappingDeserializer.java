package mc.replay.mappings.objects.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import mc.replay.mappings.objects.MaterialMapping;

import java.lang.reflect.Type;

public final class MaterialMappingDeserializer implements MappingDeserializer<MaterialMapping> {

    @Override
    public MaterialMapping deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String key = object.get("mojangName").getAsString().toLowerCase();
        int id = object.get("id").getAsInt();
        return new MaterialMapping(key, id);
    }
}