package mc.replay.common.utils.item.nbt;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static mc.replay.common.utils.item.nbt.ItemStackNBTHelper.*;

public final class ItemStackNBT {

    private static <T> ItemStack add(ItemStack item, String key, T value) {
        return setInternal(item, key, value);
    }

    private static <T> T get(ItemStack item, String key, Class<T> value, T def) {
        return getInternal(item, key, value, def);
    }

    public static boolean has(ItemStack item, String key) {
        return hasKeyInternal(item, key);
    }

    public static ItemStack setInteger(ItemStack item, String key, int value) {
        return add(item, key, value);
    }

    public static ItemStack setByte(ItemStack item, String key, byte value) {
        return add(item, key, value);
    }

    public static ItemStack setShort(ItemStack item, String key, short value) {
        return add(item, key, value);
    }

    public static ItemStack setLong(ItemStack item, String key, long value) {
        return add(item, key, value);
    }

    public static ItemStack setFloat(ItemStack item, String key, float value) {
        return add(item, key, value);
    }

    public static ItemStack setDouble(ItemStack item, String key, double value) {
        return add(item, key, value);
    }

    public static ItemStack setString(ItemStack item, String name, String value) {
        return add(item, name, value);
    }

    public static ItemStack setByteArray(ItemStack item, String key, byte[] value) {
        return add(item, key, value);
    }

    public static ItemStack setIntegerArray(ItemStack item, String key, int[] value) {
        return add(item, key, value);
    }

    public static ItemStack setBoolean(ItemStack item, String key, boolean value) {
        return add(item, key, value);
    }

    public static ItemStack setUUID(ItemStack item, String key, UUID value) {
        return add(item, key, value);
    }

    public static int getInteger(ItemStack item, String key, int def) {
        return get(item, key, int.class, def);
    }

    public static byte getByte(ItemStack item, String key, byte def) {
        return get(item, key, byte.class, def);
    }

    public static short getShort(ItemStack item, String key, short def) {
        return get(item, key, short.class, def);
    }

    public static long getLong(ItemStack item, String key, long def) {
        return get(item, key, long.class, def);
    }

    public static float getFloat(ItemStack item, String key, float def) {
        return get(item, key, float.class, def);
    }

    public static double getDouble(ItemStack item, String key, double def) {
        return get(item, key, double.class, def);
    }

    public static String getString(ItemStack item, String key, String def) {
        return get(item, key, String.class, def);
    }

    public static byte[] getByteArray(ItemStack item, String key, byte[] def) {
        return get(item, key, byte[].class, def);
    }

    public static int[] getIntegerArray(ItemStack item, String key, int[] def) {
        return get(item, key, int[].class, def);
    }

    public static boolean getBoolean(ItemStack item, String key, boolean def) {
        return get(item, key, boolean.class, def);
    }

    public static UUID getUUID(ItemStack item, String key, UUID def) {
        return get(item, key, UUID.class, def);
    }

    public static int getInteger(ItemStack item, String key) {
        return getInteger(item, key, 0);
    }

    public static byte getByte(ItemStack item, String key) {
        return getByte(item, key, (byte) 0);
    }

    public static short getShort(ItemStack item, String key) {
        return getShort(item, key, (short) 0);
    }

    public static long getLong(ItemStack item, String key) {
        return getLong(item, key, 0L);
    }

    public static float getFloat(ItemStack item, String key) {
        return getFloat(item, key, 0F);
    }

    public static double getDouble(ItemStack item, String key) {
        return getDouble(item, key, 0D);
    }

    public static String getString(ItemStack item, String key) {
        return getString(item, key, null);
    }

    public static byte[] getByteArray(ItemStack item, String key) {
        return getByteArray(item, key, new byte[0]);
    }

    public static int[] getIntegerArray(ItemStack item, String key) {
        return getIntegerArray(item, key, new int[0]);
    }

    public static boolean getBoolean(ItemStack item, String key) {
        return getBoolean(item, key, false);
    }

    public static UUID getUUID(ItemStack item, String key) {
        return getUUID(item, key, null);
    }

    public static ItemStack remove(ItemStack item, String key) {
        return removeInternal(item, key);
    }

    public static @NotNull Set<String> getKeys(ItemStack item) {
        return getKeysInternal(item);
    }

    public static @NotNull Map<String, String> getNBTValues(ItemStack item) {
        return getNBTValuesInternal(item);
    }
}