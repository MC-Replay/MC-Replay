package mc.replay.common.utils.reflection.nms;

import com.mojang.authlib.GameProfile;
import mc.replay.common.utils.reflection.MinecraftReflections;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public final class NPCPacketUtils {

    // DataWatcher skin parts byte values
    public static final byte JACKET = 0x02;
    public static final byte LEFT_SLEEVE = 0x04;
    public static final byte RIGHT_SLEEVE = 0x8;
    public static final byte LEFT_PANTS = 0x10;
    public static final byte RIGHT_PANTS = 0x20;
    public static final byte HAT = 0x40;

    public static final int SWING_ARM_ANIMATION_ID = 0;

    public static Class<?> MINECRAFT_SERVER;
    public static Class<?> WORLD_SERVER;
    public static Class<?> PLAYER_INTERACT_MANAGER;

    public static Class<?> SCOREBOARD;
    public static Class<?> SCOREBOARD_TEAM;
    public static Class<?> ENUM_CHAT_FORMAT;
    public static Class<?> ENUM_NAME_TAG_VISIBILITY;

    public static Class<?> CRAFT_WORLD;

    public static Class<?> PACKET_PLAY_OUT_PLAYER_INFO;
    public static Class<?> PACKET_PLAY_OUT_NAMED_ENTITY_SPAWN;
    public static Class<?> PACKET_PLAY_OUT_SCOREBOARD_TEAM;
    public static Class<?> PACKET_PLAY_OUT_ENTITY_METADATA;
    public static Class<?> PACKET_PLAY_OUT_ENTITY_TELEPORT;
    public static Class<?> PACKET_PLAY_OUT_ENTITY_EQUIPMENT;
    public static Class<?> PACKET_PLAY_OUT_ENTITY_LOOK;
    public static Class<?> PACKET_PLAY_OUT_ENTITY_HEAD_ROTATION;
    public static Class<?> PACKET_PLAY_OUT_ANIMATION;
    public static Class<?> PACKET_PLAY_OUT_ENTITY_DESTROY;

    public static Class<?> ENUM_PLAYER_INFO_ACTION;
    public static Object ENUM_PLAYER_INFO_ACTION_ADD_PLAYER;
    public static Object ENUM_PLAYER_INFO_ACTION_REMOVE_PLAYER;

    public static Class<?> DATA_WATCHER;
    public static Class<?> DATA_WATCHER_REGISTRY;

    public static Class<?> ENUM_ITEM_SLOT;

    public static Class<?> ENTITY;

    public static Object MINECRAFT_SERVER_INSTANCE;
    public static Object SCOREBOARD_INSTANCE;
    public static Object DATA_WATCHER_BYTE_SERIALIZER_INSTANCE;

    public static Method SCOREBOARD_CREATE_TEAM_METHOD;
    public static Method SCOREBOARD_SET_PREFIX_METHOD;
    public static Method SCOREBOARD_SET_COLOR_METHOD;
    public static Method GET_ENUM_CHAT_FORMAT_BY_NAME;
    public static Method GET_ENUM_NAME_TAG_VISIBILITY_BY_NAME;
    public static Method SET_NAME_TAG_VISIBILITY;
    public static Method GET_WORLD_SERVER;
    public static Method SET_ENTITY_POSITION;
    public static Method GET_DATA_WATCHER;
    public static Method GET_DATA_WATCHER_OBJECT;
    public static Method GET_ENTITY_ID;
    public static Method GET_PLAYER_NAMES_IN_TEAM;
    public static Method SET_DATA_WATCHER_OBJECT;
    public static Method GET_ENUM_ITEM_SLOT;

    public static Constructor<?> PACKET_PLAY_OUT_NAMED_ENTITY_SPAWN_CONSTRUCTOR;
    public static Constructor<?> PACKET_PLAY_OUT_ENTITY_METADATA_CONSTRUCTOR;
    public static Constructor<?> PACKET_PLAY_OUT_ENTITY_TELEPORT_CONSTRUCTOR;
    public static Constructor<?> PACKET_PLAY_OUT_ENTITY_EQUIPMENT_CONSTRUCTOR;
    public static Constructor<?> PACKET_PLAY_OUT_ENTITY_LOOK_CONSTRUCTOR;
    public static Constructor<?> PACKET_PLAY_OUT_ENTITY_HEAD_ROTATION_CONSTRUCTOR;
    public static Constructor<?> PACKET_PLAY_OUT_ANIMATION_CONSTRUCTOR;
    public static Constructor<?> PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR;
    public static Constructor<?> ENTITY_PLAYER_CONSTRUCTOR;

    public static Class<?> PACKET_PLAY_IN_USE_ENTITY;
    public static Class<?> ENUM_ENTITY_USE_ACTION;

    private static final boolean is18 = MinecraftReflections.isRepackaged();
    private static Executable INITIALIZE_SCOREBOARD_TEAM;

    static {
        try {
            MINECRAFT_SERVER = MinecraftReflections.nmsClass("server", "MinecraftServer");
            WORLD_SERVER = MinecraftReflections.nmsClass("server.level", "WorldServer");
            PLAYER_INTERACT_MANAGER = MinecraftReflections.nmsClass("server.level", "PlayerInteractManager");

            SCOREBOARD = MinecraftReflections.nmsClass("world.scores", "Scoreboard");
            SCOREBOARD_TEAM = MinecraftReflections.nmsClass("world.scores", "ScoreboardTeam");
            ENUM_CHAT_FORMAT = MinecraftReflections.nmsClass("", "EnumChatFormat");
            ENUM_NAME_TAG_VISIBILITY = MinecraftReflections.nmsClass("world.scores", "ScoreboardTeamBase$EnumNameTagVisibility");

            CRAFT_WORLD = MinecraftReflections.obcClass("CraftWorld");

            PACKET_PLAY_OUT_PLAYER_INFO = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayOutPlayerInfo");
            PACKET_PLAY_OUT_NAMED_ENTITY_SPAWN = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayOutNamedEntitySpawn");
            PACKET_PLAY_OUT_SCOREBOARD_TEAM = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayOutScoreboardTeam");
            PACKET_PLAY_OUT_ENTITY_METADATA = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayOutEntityMetadata");
            PACKET_PLAY_OUT_ENTITY_TELEPORT = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayOutEntityTeleport");
            PACKET_PLAY_OUT_ENTITY_EQUIPMENT = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayOutEntityEquipment");
            PACKET_PLAY_OUT_ENTITY_LOOK = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayOutEntity$PacketPlayOutEntityLook");
            PACKET_PLAY_OUT_ENTITY_HEAD_ROTATION = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayOutEntityHeadRotation");
            PACKET_PLAY_OUT_ANIMATION = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayOutAnimation");
            PACKET_PLAY_OUT_ENTITY_DESTROY = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayOutEntityDestroy");

            ENUM_PLAYER_INFO_ACTION = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
            ENUM_PLAYER_INFO_ACTION_ADD_PLAYER = (is18)
                    ? ENUM_PLAYER_INFO_ACTION.getField("a").get(null)
                    : ENUM_PLAYER_INFO_ACTION.getMethod("valueOf", String.class).invoke(null, "ADD_PLAYER");
            ENUM_PLAYER_INFO_ACTION_REMOVE_PLAYER = (is18)
                    ? ENUM_PLAYER_INFO_ACTION.getField("e").get(null)
                    : ENUM_PLAYER_INFO_ACTION.getMethod("valueOf", String.class).invoke(null, "REMOVE_PLAYER");

            DATA_WATCHER = MinecraftReflections.nmsClass("network.syncher", "DataWatcher");
            DATA_WATCHER_REGISTRY = MinecraftReflections.nmsClass("network.syncher", "DataWatcherRegistry");

            ENUM_ITEM_SLOT = MinecraftReflections.nmsClass("world.entity", "EnumItemSlot");

            ENTITY = MinecraftReflections.nmsClass("world.entity", "Entity");

            SCOREBOARD_INSTANCE = SCOREBOARD.getConstructor().newInstance();
            MINECRAFT_SERVER_INSTANCE = MINECRAFT_SERVER.getMethod("getServer").invoke(null);
            DATA_WATCHER_BYTE_SERIALIZER_INSTANCE = DATA_WATCHER_REGISTRY.getField("a").get(null);

            SCOREBOARD_CREATE_TEAM_METHOD = SCOREBOARD.getMethod((is18) ? "g" : "createTeam", String.class);
            SCOREBOARD_SET_PREFIX_METHOD = SCOREBOARD_TEAM.getMethod((is18) ? "b" : "setPrefix", MinecraftNMS.CHAT_BASE_COMPONENT);
            SCOREBOARD_SET_COLOR_METHOD = SCOREBOARD_TEAM.getMethod((is18) ? "a" : "setColor", ENUM_CHAT_FORMAT);
            GET_ENUM_CHAT_FORMAT_BY_NAME = ENUM_CHAT_FORMAT.getMethod("b", String.class);
            GET_ENUM_NAME_TAG_VISIBILITY_BY_NAME = ENUM_NAME_TAG_VISIBILITY.getMethod("a", String.class);
            SET_NAME_TAG_VISIBILITY = SCOREBOARD_TEAM.getMethod((is18) ? "a" : "setNameTagVisibility", ENUM_NAME_TAG_VISIBILITY);
            GET_WORLD_SERVER = CRAFT_WORLD.getMethod("getHandle");
            SET_ENTITY_POSITION = MinecraftNMS.ENTITY_PLAYER.getMethod((is18) ? "g" : "setPosition", double.class, double.class, double.class);

            GET_DATA_WATCHER = MinecraftNMS.ENTITY_PLAYER.getMethod((is18) ? "ai" : "getDataWatcher");
            GET_DATA_WATCHER_OBJECT = DATA_WATCHER_BYTE_SERIALIZER_INSTANCE.getClass().getMethod("a", int.class);
            GET_ENTITY_ID = MinecraftNMS.ENTITY_PLAYER.getMethod((is18) ? "ae" : "getId");
            GET_PLAYER_NAMES_IN_TEAM = SCOREBOARD_TEAM.getMethod((is18) ? "g" : "getPlayerNameSet");
            SET_DATA_WATCHER_OBJECT = Arrays.stream(DATA_WATCHER.getMethods())
                    .filter((method) -> method.getName().equals((is18) ? "b" : "set") && method.getParameterCount() == 2)
                    .findFirst()
                    .orElseThrow(NoSuchMethodException::new);
            GET_ENUM_ITEM_SLOT = ENUM_ITEM_SLOT.getMethod("valueOf", String.class);

            PACKET_PLAY_OUT_NAMED_ENTITY_SPAWN_CONSTRUCTOR = PACKET_PLAY_OUT_NAMED_ENTITY_SPAWN.getConstructor(MinecraftNMS.ENTITY_HUMAN);
            PACKET_PLAY_OUT_ENTITY_METADATA_CONSTRUCTOR = PACKET_PLAY_OUT_ENTITY_METADATA.getConstructor(int.class, DATA_WATCHER, boolean.class);
            PACKET_PLAY_OUT_ENTITY_TELEPORT_CONSTRUCTOR = PACKET_PLAY_OUT_ENTITY_TELEPORT.getConstructor(ENTITY);
            PACKET_PLAY_OUT_ENTITY_EQUIPMENT_CONSTRUCTOR = PACKET_PLAY_OUT_ENTITY_EQUIPMENT.getConstructor(int.class, List.class);
            PACKET_PLAY_OUT_ENTITY_LOOK_CONSTRUCTOR = PACKET_PLAY_OUT_ENTITY_LOOK.getConstructor(int.class, byte.class, byte.class, boolean.class);
            PACKET_PLAY_OUT_ENTITY_HEAD_ROTATION_CONSTRUCTOR = PACKET_PLAY_OUT_ENTITY_HEAD_ROTATION.getConstructor(ENTITY, byte.class);
            PACKET_PLAY_OUT_ANIMATION_CONSTRUCTOR = PACKET_PLAY_OUT_ANIMATION.getConstructor(ENTITY, int.class);
            PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR = PACKET_PLAY_OUT_ENTITY_DESTROY.getConstructor(int[].class);
            ENTITY_PLAYER_CONSTRUCTOR = (is18)
                    ? MinecraftNMS.ENTITY_PLAYER.getConstructor(MINECRAFT_SERVER, WORLD_SERVER, GameProfile.class)
                    : MinecraftNMS.ENTITY_PLAYER.getConstructor(MINECRAFT_SERVER, WORLD_SERVER, GameProfile.class, PLAYER_INTERACT_MANAGER);

            PACKET_PLAY_IN_USE_ENTITY = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayInUseEntity");
            ENUM_ENTITY_USE_ACTION = MinecraftReflections.nmsClass("network.protocol.game", "PacketPlayInUseEntity$EnumEntityUseAction");

            INITIALIZE_SCOREBOARD_TEAM = (is18)
                    ? PACKET_PLAY_OUT_SCOREBOARD_TEAM.getMethod("a", SCOREBOARD_TEAM, boolean.class)
                    : PACKET_PLAY_OUT_SCOREBOARD_TEAM.getConstructor(SCOREBOARD_TEAM, int.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static Object createEntityPlayer(Object worldServer, GameProfile gameProfile) throws Exception {
        if (is18) {
            return ENTITY_PLAYER_CONSTRUCTOR.newInstance(MINECRAFT_SERVER_INSTANCE, worldServer, gameProfile);
        } else {
            Object playerInteractManager = PLAYER_INTERACT_MANAGER.getConstructor(WORLD_SERVER).newInstance(worldServer);
            return ENTITY_PLAYER_CONSTRUCTOR.newInstance(MINECRAFT_SERVER_INSTANCE, worldServer, gameProfile, playerInteractManager);
        }
    }

    public static Object createScoreboardTeamPacket(Object team) throws Exception {
        if (INITIALIZE_SCOREBOARD_TEAM instanceof Method method) {
            return method.invoke(null, team, true);
        } else if (INITIALIZE_SCOREBOARD_TEAM instanceof Constructor<?> constructor) {
            return constructor.newInstance(team, 0);
        }

        return null;
    }

    public static Object createSingleEntityPlayerArray(Object entityPlayer) {
        Object array = Array.newInstance(MinecraftNMS.ENTITY_PLAYER, 1);
        Array.set(array, 0, entityPlayer);
        return array;
    }

    public static int getEntityId(Object entityObject) {
        if (entityObject == null || !ENTITY.isAssignableFrom(entityObject.getClass())) return -1;

        try {
            return (int) GET_ENTITY_ID.invoke(entityObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return -1;
    }
}