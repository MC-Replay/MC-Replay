package mc.replay.common.utils.reflection;

import mc.replay.packetlib.utils.ProtocolVersion;
import mc.replay.packetlib.utils.ReflectionUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

import static mc.replay.common.utils.reflection.JavaReflections.MethodInvoker;
import static mc.replay.common.utils.reflection.JavaReflections.getTypedMethod;
import static mc.replay.packetlib.utils.ReflectionUtils.nmsClass;
import static mc.replay.packetlib.utils.ReflectionUtils.obcClass;

public final class MinecraftNMS {

    // NMS
    public static Class<?> NBT_TAG_COMPOUND;
    public static Class<?> NBT_BASE;
    public static Class<?> NMS_ITEM_STACK;
    public static Class<?> MINECRAFT_SERVER;
    public static Class<?> I_ASYNC_TASK_HANDLER;
    public static Class<?> WORLD_SERVER;
    public static Class<?> CHUNK_PROVIDER_SERVER;
    public static Class<?> ENTITY;
    public static Class<?> ENTITY_PLAYER;

    // OBC
    public static Class<?> CRAFT_ITEM_STACK;

    public static MethodHandle GET_PLAYER_HANDLE_METHOD;
    public static Method GET_BUKKIT_ITEM_STACK;
    public static Method GET_NMS_ITEM_STACK;
    public static Method EXECUTE_TASK_METHOD;
    public static Method SET_ENTITY_POSITION_ROTATION_METHOD;
    public static MethodInvoker GET_WORLD_SERVER_METHOD;
    public static MethodInvoker GET_CHUNK_PROVIDER_METHOD;
    public static MethodInvoker MOVE_PLAYER_METHOD;

    public static Object MINECRAFT_SERVER_INSTANCE;

    static {
        try {
            ProtocolVersion version = ProtocolVersion.getServerVersion();
            MethodHandles.Lookup lookup = MethodHandles.lookup();

            NBT_TAG_COMPOUND = nmsClass("nbt", "NBTTagCompound");
            NBT_BASE = nmsClass("nbt", "NBTBase");
            NMS_ITEM_STACK = nmsClass("world.item", "ItemStack");
            MINECRAFT_SERVER = nmsClass("server", "MinecraftServer");
            I_ASYNC_TASK_HANDLER = nmsClass("util.thread", "IAsyncTaskHandler");
            WORLD_SERVER = nmsClass("server.level", "WorldServer");
            CHUNK_PROVIDER_SERVER = nmsClass("server.level", "ChunkProviderServer");
            ENTITY = nmsClass("world.entity", "Entity");
            ENTITY_PLAYER = ReflectionUtils.nmsClass("server.level", "EntityPlayer");

            CRAFT_ITEM_STACK = obcClass("inventory.CraftItemStack");

            Class<?> craftPlayerClass = ReflectionUtils.obcClass("entity.CraftPlayer");
            GET_PLAYER_HANDLE_METHOD = lookup.findVirtual(craftPlayerClass, "getHandle", MethodType.methodType(ENTITY_PLAYER));

            GET_BUKKIT_ITEM_STACK = CRAFT_ITEM_STACK.getMethod("asBukkitCopy", NMS_ITEM_STACK);
            GET_NMS_ITEM_STACK = CRAFT_ITEM_STACK.getMethod("asNMSCopy", ItemStack.class);
            EXECUTE_TASK_METHOD = I_ASYNC_TASK_HANDLER.getMethod("execute", Runnable.class);

            if (version.isEqual(ProtocolVersion.MINECRAFT_1_16_5)) {
                SET_ENTITY_POSITION_ROTATION_METHOD = ENTITY.getMethod("setPositionRotation", double.class, double.class, double.class, float.class, float.class);
            } else {
                SET_ENTITY_POSITION_ROTATION_METHOD = ENTITY.getMethod("a", double.class, double.class, double.class, float.class, float.class);
            }

            GET_WORLD_SERVER_METHOD = getTypedMethod(ENTITY_PLAYER, null, WORLD_SERVER);
            GET_CHUNK_PROVIDER_METHOD = getTypedMethod(WORLD_SERVER, null, CHUNK_PROVIDER_SERVER);
            MOVE_PLAYER_METHOD = getTypedMethod(CHUNK_PROVIDER_SERVER, null, void.class, ENTITY_PLAYER);

            MINECRAFT_SERVER_INSTANCE = MINECRAFT_SERVER.getMethod("getServer").invoke(null);
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

    public static Object getEntityPlayer(@NotNull Player player) throws Throwable {
        return GET_PLAYER_HANDLE_METHOD.invoke(player);
    }
}