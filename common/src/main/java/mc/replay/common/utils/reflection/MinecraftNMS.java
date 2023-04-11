package mc.replay.common.utils.reflection;

import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

import static mc.replay.packetlib.utils.ReflectionUtils.nmsClass;
import static mc.replay.packetlib.utils.ReflectionUtils.obcClass;

public final class MinecraftNMS {

    // NMS
    public static Class<?> NBT_TAG_COMPOUND;
    public static Class<?> NBT_BASE;
    public static Class<?> NMS_ITEM_STACK;
    public static Class<?> MINECRAFT_SERVER;

    // OBC
    public static Class<?> CRAFT_ITEM_STACK;

    public static Method GET_BUKKIT_ITEM_STACK;
    public static Method GET_NMS_ITEM_STACK;

    static {
        try {
            NBT_TAG_COMPOUND = nmsClass("nbt", "NBTTagCompound");
            NBT_BASE = nmsClass("nbt", "NBTBase");
            NMS_ITEM_STACK = nmsClass("world.item", "ItemStack");
            MINECRAFT_SERVER = nmsClass("server", "MinecraftServer");

            CRAFT_ITEM_STACK = obcClass("inventory.CraftItemStack");

            GET_BUKKIT_ITEM_STACK = CRAFT_ITEM_STACK.getMethod("asBukkitCopy", NMS_ITEM_STACK);
            GET_NMS_ITEM_STACK = CRAFT_ITEM_STACK.getMethod("asNMSCopy", ItemStack.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static int getCurrentServerTick() {
        try {
            return (int) MINECRAFT_SERVER.getField("currentTick").get(null);
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
    }
}