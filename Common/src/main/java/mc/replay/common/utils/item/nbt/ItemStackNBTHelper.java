package mc.replay.common.utils.item.nbt;

import mc.replay.common.utils.Pair;
import mc.replay.common.utils.reflection.nms.MinecraftNMS;
import mc.replay.common.utils.reflection.nms.MinecraftVersionNMS;
import mc.replay.common.utils.reflection.version.ProtocolVersion;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

final class ItemStackNBTHelper {

    public static ProtocolVersion VERSION;

    public static Method HAS_TAG;
    public static Method GET_TAG;
    public static Method SET_TAG;

    public static Method HAS_KEY;
    public static Method REMOVE_KEY;
    public static Method GET_KEYS;

    public static Method NBT_BASE_AS_STRING;

    public static Field ALL_NBT_VALUES;

    static {
        try {
            VERSION = MinecraftVersionNMS.getServerProtocolVersionEnum();

            if (VERSION.isLowerOrEqual(ProtocolVersion.MINECRAFT_1_16_5)) {
                HAS_TAG = MinecraftNMS.NMS_ITEM_STACK.getMethod("hasTag");
                GET_TAG = MinecraftNMS.NMS_ITEM_STACK.getMethod("getTag");
                SET_TAG = MinecraftNMS.NMS_ITEM_STACK.getMethod("setTag", MinecraftNMS.NBT_TAG_COMPOUND);

                HAS_KEY = MinecraftNMS.NBT_TAG_COMPOUND.getMethod("hasKey", String.class);
                REMOVE_KEY = MinecraftNMS.NBT_TAG_COMPOUND.getMethod("remove", String.class);
                GET_KEYS = MinecraftNMS.NBT_TAG_COMPOUND.getMethod("getKeys");

                NBT_BASE_AS_STRING = MinecraftNMS.NBT_BASE.getMethod("asString");

                ALL_NBT_VALUES = MinecraftNMS.NBT_TAG_COMPOUND.getField("map");
            } else if (VERSION.isLowerOrEqual(ProtocolVersion.MINECRAFT_1_18_1)) {
                HAS_TAG = MinecraftNMS.NMS_ITEM_STACK.getMethod("r");
                GET_TAG = MinecraftNMS.NMS_ITEM_STACK.getMethod("s");
                SET_TAG = MinecraftNMS.NMS_ITEM_STACK.getMethod("c", MinecraftNMS.NBT_TAG_COMPOUND);

                HAS_KEY = MinecraftNMS.NBT_TAG_COMPOUND.getMethod("e", String.class);
                REMOVE_KEY = MinecraftNMS.NBT_TAG_COMPOUND.getMethod("r", String.class);
                GET_KEYS = MinecraftNMS.NBT_TAG_COMPOUND.getMethod("d");

                NBT_BASE_AS_STRING = MinecraftNMS.NBT_BASE.getMethod("e_");

                ALL_NBT_VALUES = MinecraftNMS.NBT_TAG_COMPOUND.getDeclaredField("x");
            } else {
                HAS_TAG = MinecraftNMS.NMS_ITEM_STACK.getMethod("s");
                GET_TAG = MinecraftNMS.NMS_ITEM_STACK.getMethod("t");
                SET_TAG = MinecraftNMS.NMS_ITEM_STACK.getMethod("c", MinecraftNMS.NBT_TAG_COMPOUND);

                HAS_KEY = MinecraftNMS.NBT_TAG_COMPOUND.getMethod("e", String.class);
                REMOVE_KEY = MinecraftNMS.NBT_TAG_COMPOUND.getMethod("r", String.class);
                GET_KEYS = MinecraftNMS.NBT_TAG_COMPOUND.getMethod("d");

                NBT_BASE_AS_STRING = MinecraftNMS.NBT_BASE.getMethod("e_");

                ALL_NBT_VALUES = MinecraftNMS.NBT_TAG_COMPOUND.getDeclaredField("x");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static <T> ItemStack setInternal(ItemStack item, String key, T value) {
        Object tag = getTag(item);
        if (tag == null) return item;

        try {
            Pair<String, Class<?>> info = getMethod(value.getClass());
            if (info.getKey() == null || info.getValue() == null) return item;

            String methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? "a" : "set" + info.getKey();
            tag.getClass().getMethod(methodName, String.class, info.getValue()).invoke(tag, key, value);
        } catch (Exception exception) {
            exception.printStackTrace();
            return item;
        }

        return setTag(item, tag);
    }

    public static <T> T getInternal(ItemStack item, String key, Class<T> value, T def) {
        Object tag = getTag(item);
        if (tag == null) return def;

        try {
            Pair<String, Class<?>> info = getMethod(value);
            if (info.getKey() == null || info.getValue() == null) return def;

            String methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? info.getKey() : "get" + info.getKey();
            return value.cast(tag.getClass().getMethod(methodName, String.class).invoke(tag, key));
        } catch (Exception exception) {
            exception.printStackTrace();
            return def;
        }
    }

    public static ItemStack removeInternal(ItemStack item, String key) {
        Object tag = getTag(item);
        if (tag == null) return item;

        try {
            REMOVE_KEY.invoke(tag, key);
        } catch (Exception exception) {
            exception.printStackTrace();
            return item;
        }

        return setTag(item, tag);
    }

    public static boolean hasKeyInternal(ItemStack item, String key) {
        Object tag = getTag(item);
        if (tag == null) return false;

        try {
            Object hasKeyObject = HAS_KEY.invoke(tag, key);
            return hasKeyObject instanceof Boolean hasKey && hasKey;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static Object getTag(ItemStack item) {
        try {
            Object nmsItemStack = MinecraftNMS.GET_NMS_ITEM_STACK.invoke(null, item);
            Object hasTagObject = HAS_TAG.invoke(nmsItemStack);

            return (hasTagObject instanceof Boolean hasTag && hasTag)
                    ? GET_TAG.invoke(nmsItemStack)
                    : MinecraftNMS.NBT_TAG_COMPOUND.getConstructor().newInstance();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static ItemStack setTag(ItemStack item, Object tag) {
        try {
            Object nmsItemStack = MinecraftNMS.GET_NMS_ITEM_STACK.invoke(null, item);
            SET_TAG.invoke(nmsItemStack, tag);

            return (ItemStack) MinecraftNMS.GET_BUKKIT_ITEM_STACK.invoke(null, nmsItemStack);
        } catch (Exception exception) {
            exception.printStackTrace();
            return item;
        }
    }

    @SuppressWarnings("unchecked")
    public static Set<String> getKeysInternal(ItemStack item) {
        if (item == null) return new HashSet<>();

        try {
            Object tag = getTag(item);
            return (Set<String>) GET_KEYS.invoke(tag);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new HashSet<>();
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> getNBTValuesInternal(ItemStack item) {
        if (item == null) return new HashMap<>();

        try {
            Object tag = getTag(item);
            ALL_NBT_VALUES.setAccessible(true);

            Map<String, String> values = new HashMap<>();

            Map<String, Object> nbt = (Map<String, Object>) ALL_NBT_VALUES.get(tag);
            for (Map.Entry<String, Object> entry : nbt.entrySet()) {
                values.put(entry.getKey(), (String) NBT_BASE_AS_STRING.invoke(entry.getValue()));
            }

            ALL_NBT_VALUES.setAccessible(false);

            return values;
        } catch (Exception exception) {
            exception.printStackTrace();
            return new HashMap<>();
        }
    }

    public static <T> Pair<String, Class<?>> getMethod(Class<T> valueClass) {
        String methodName = null;
        Class<?> value = null;

        if (valueClass == int.class || valueClass == Integer.class) {
            methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? "h" : "Int";
            value = int.class;
        } else if (valueClass == byte.class || valueClass == Byte.class) {
            methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? "f" : "Byte";
            value = byte.class;
        } else if (valueClass == short.class || valueClass == Short.class) {
            methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? "g" : "Short";
            value = short.class;
        } else if (valueClass == long.class || valueClass == Long.class) {
            methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? "i" : "Long";
            value = long.class;
        } else if (valueClass == float.class || valueClass == Float.class) {
            methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? "j" : "Float";
            value = float.class;
        } else if (valueClass == double.class || valueClass == Double.class) {
            methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? "k" : "Double";
            value = double.class;
        } else if (valueClass == String.class) {
            methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? "l" : "String";
            value = String.class;
        } else if (valueClass == byte[].class || valueClass == Byte[].class) {
            methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? "m" : "ByteArray";
            value = byte[].class;
        } else if (valueClass == int[].class || valueClass == Integer[].class) {
            methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? "n" : "IntArray";
            value = int[].class;
        } else if (valueClass == boolean.class || valueClass == Boolean.class) {
            methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? "q" : "Boolean";
            value = boolean.class;
        } else if (valueClass == UUID.class) {
            methodName = (VERSION.isHigherOrEqual(ProtocolVersion.MINECRAFT_1_17)) ? "a" : "UUID";
            value = UUID.class;
        }

        return new Pair<>(methodName, value);
    }
}