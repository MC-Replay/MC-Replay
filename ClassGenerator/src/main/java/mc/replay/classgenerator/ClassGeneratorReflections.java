package mc.replay.classgenerator;

import mc.replay.packetlib.utils.ReflectionUtils;
import mc.replay.wrapper.utils.WrapperReflections;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ClassGeneratorReflections {

    private ClassGeneratorReflections() {
    }

    public static Class<?> MINECRAFT_SERVER;
    public static Class<?> CRAFT_WORLD;
    public static Class<?> PLAYER_INTERACT_MANAGER;
    public static Class<?> ENUM_GAME_MODE;
    public static Class<?> PLAYER_LIST;

    public static Class<?> CRAFT_SERVER;
    public static Class<?> CRAFT_PLAYER;

    public static Method GET_BUKKIT_ENTITY_METHOD;

    public static Field PLAYERS_FIELD;
    public static Field PLAYER_VIEW_FIELD;

    public static Object PLAYER_LIST_INSTANCE;
    public static Object CRAFT_SERVER_INSTANCE;

    static {
        try {
            MINECRAFT_SERVER = ReflectionUtils.nmsClass("server", "MinecraftServer");
            CRAFT_WORLD = ReflectionUtils.obcClass("CraftWorld");
            PLAYER_INTERACT_MANAGER = ReflectionUtils.nmsClass("server.level", "PlayerInteractManager");
            ENUM_GAME_MODE = ReflectionUtils.nmsClass("world.level", "EnumGamemode");
            PLAYER_LIST = ReflectionUtils.nmsClass("server.players", "PlayerList");

            CRAFT_SERVER = ReflectionUtils.obcClass("CraftServer");
            CRAFT_PLAYER = ReflectionUtils.obcClass("entity.CraftPlayer");

            GET_BUKKIT_ENTITY_METHOD = WrapperReflections.ENTITY.getMethod("getBukkitEntity");

            PLAYERS_FIELD = ReflectionUtils.getField(PLAYER_LIST, "players");
            PLAYER_VIEW_FIELD = ReflectionUtils.getField(CRAFT_SERVER, "playerView");

            Object minecraftServerInstance = MINECRAFT_SERVER.getMethod("getServer").invoke(null);
            PLAYER_LIST_INSTANCE = MINECRAFT_SERVER.getMethod("getPlayerList").invoke(minecraftServerInstance);

            Object craftServerInstance = Bukkit.class.getMethod("getServer").invoke(null);
            CRAFT_SERVER_INSTANCE = CRAFT_SERVER.cast(craftServerInstance);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}