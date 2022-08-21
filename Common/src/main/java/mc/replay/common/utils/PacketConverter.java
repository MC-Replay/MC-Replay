package mc.replay.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class PacketConverter {

    public static ConvertedPacket convert(Object packet) {
        Map<String, Object> table = new HashMap<>();

        try {
            for (Field declaredField : packet.getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);

                table.put(declaredField.getName(), declaredField.get(packet));

                declaredField.setAccessible(false);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return new ConvertedPacket(table);
    }

    public record ConvertedPacket(Map<String, Object> packetFieldsTable) {

        public <T> T get(String fieldName, Class<T> type) {
            return type.cast(this.packetFieldsTable.get(fieldName));
        }
    }
}