package mc.replay.utils.reflection.nms;

import mc.replay.utils.reflection.MinecraftReflections;
import org.bukkit.inventory.ItemStack;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class MinecraftNMS {

    // NMS
    public static Class<?> PACKET;
    public static Class<?> ENTITY_PLAYER;
    public static Class<?> ENTITY_HUMAN;
    public static Class<?> PLAYER_CONNECTION;
    public static Class<?> CHAT_BASE_COMPONENT;
    public static Class<?> NETWORK_MANAGER;
    public static Class<?> NBT_TAG_COMPOUND;
    public static Class<?> NBT_BASE;
    public static Class<?> NMS_ITEM_STACK;
    public static Class<?> MINECRAFT_SERVER;

    // OBC
    public static Class<?> CRAFT_PLAYER;
    public static Class<?> CRAFT_CHAT_MESSAGE;
    public static Class<?> CRAFT_ITEM_STACK;

    public static Method GET_BUKKIT_ITEM_STACK;
    public static Method GET_NMS_ITEM_STACK;

    public static MethodHandle GET_PLAYER_HANDLE_METHOD;
    public static MethodHandle GET_PLAYER_CONNECTION_METHOD;
    public static MethodHandle SEND_PACKET_METHOD;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();

            PACKET = MinecraftReflections.nmsClass("network.protocol", "Packet");
            ENTITY_PLAYER = MinecraftReflections.nmsClass("server.level", "EntityPlayer");
            ENTITY_HUMAN = MinecraftReflections.nmsClass("world.entity.player", "EntityHuman");
            PLAYER_CONNECTION = MinecraftReflections.nmsClass("server.network", "PlayerConnection");
            CHAT_BASE_COMPONENT = MinecraftReflections.nmsClass("network.chat", "IChatBaseComponent");
            NETWORK_MANAGER = MinecraftReflections.nmsClass("network", "NetworkManager");
            NBT_TAG_COMPOUND = MinecraftReflections.nmsClass("nbt", "NBTTagCompound");
            NBT_BASE = MinecraftReflections.nmsClass("nbt", "NBTBase");
            NMS_ITEM_STACK = MinecraftReflections.nmsClass("world.item", "ItemStack");
            MINECRAFT_SERVER = MinecraftReflections.nmsClass("server", "MinecraftServer");

            CRAFT_PLAYER = MinecraftReflections.obcClass("entity.CraftPlayer");
            CRAFT_CHAT_MESSAGE = MinecraftReflections.obcClass("util.CraftChatMessage");
            CRAFT_ITEM_STACK = MinecraftReflections.obcClass("inventory.CraftItemStack");

            GET_BUKKIT_ITEM_STACK = CRAFT_ITEM_STACK.getMethod("asBukkitCopy", NMS_ITEM_STACK);
            GET_NMS_ITEM_STACK = CRAFT_ITEM_STACK.getMethod("asNMSCopy", ItemStack.class);

            Field playerConnectionField = Arrays.stream(ENTITY_PLAYER.getFields())
                    .filter(field -> field.getType().isAssignableFrom(PLAYER_CONNECTION))
                    .findFirst().orElseThrow(NoSuchFieldException::new);

            Method sendPacketMethod = Arrays.stream(PLAYER_CONNECTION.getMethods())
                    .filter(m -> m.getParameterCount() == 1 && m.getParameterTypes()[0] == PACKET)
                    .findFirst().orElseThrow(NoSuchMethodException::new);

            GET_PLAYER_HANDLE_METHOD = lookup.findVirtual(CRAFT_PLAYER, "getHandle", MethodType.methodType(ENTITY_PLAYER));
            GET_PLAYER_CONNECTION_METHOD = lookup.unreflectGetter(playerConnectionField);
            SEND_PACKET_METHOD = lookup.unreflect(sendPacketMethod);
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