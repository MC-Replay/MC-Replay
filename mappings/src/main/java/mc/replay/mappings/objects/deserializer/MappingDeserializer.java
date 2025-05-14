package mc.replay.mappings.objects.deserializer;

import com.google.gson.JsonDeserializer;
import mc.replay.mappings.objects.Mapping;

public interface MappingDeserializer<T extends Mapping> extends JsonDeserializer<T> {
}