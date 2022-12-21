package mc.replay.classgenerator;

import mc.replay.packetlib.utils.ReflectionUtils;

public final class ClassGeneratorReflections {

    private ClassGeneratorReflections() {
    }

    public static Class<?> MINECRAFT_SERVER;
    public static Class<?> CRAFT_WORLD;
    public static Class<?> PLAYER_INTERACT_MANAGER;
    public static Class<?> ENUM_GAME_MODE;

    public static Class<?> CRAFT_PLAYER;

    static {
        try {
            MINECRAFT_SERVER = ReflectionUtils.nmsClass("server", "MinecraftServer");
            CRAFT_WORLD = ReflectionUtils.obcClass("CraftWorld");
            PLAYER_INTERACT_MANAGER = ReflectionUtils.nmsClass("server.level", "PlayerInteractManager");
            ENUM_GAME_MODE = ReflectionUtils.nmsClass("world.level", "EnumGamemode");

            CRAFT_PLAYER = ReflectionUtils.obcClass("entity.CraftPlayer");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}