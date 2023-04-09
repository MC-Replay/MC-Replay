package mc.replay.classgenerator;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import mc.replay.api.MCReplayAPI;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.utils.ProtocolVersion;
import mc.replay.packetlib.utils.ReflectionUtils;
import mc.replay.wrapper.utils.WrapperReflections;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

@SuppressWarnings("rawtypes")
public final class ClassGeneratorReflections {

    private ClassGeneratorReflections() {
    }

    public static Class<?> MINECRAFT_SERVER;
    public static Class<?> CRAFT_WORLD;
    public static Class<?> PLAYER_INTERACT_MANAGER;
    public static Class<?> ENUM_GAME_MODE;
    public static Class<?> PLAYER_LIST;
    public static Class<?> PACKET;

    public static Class<?> CRAFT_SERVER;
    public static Class<?> CRAFT_PLAYER;

    public static Method GET_BUKKIT_ENTITY_METHOD;

    public static Method ID_FROM_PACKET_METHOD;
    public static Method SERIALIZE_PACKET_METHOD;
    public static Object CLIENTBOUND_PROTOCOL_DIRECTION;
    public static Object PLAY_ENUM_PROTOCOL;

    public static Field PLAYERS_FIELD;
    public static Field PLAYER_VIEW_FIELD;

    public static Object PLAYER_LIST_INSTANCE;
    public static List PLAYER_LIST_FIELD_INSTANCE;
    public static Object CRAFT_SERVER_INSTANCE;

    static {
        try {
            MINECRAFT_SERVER = ReflectionUtils.nmsClass("server", "MinecraftServer");
            CRAFT_WORLD = ReflectionUtils.obcClass("CraftWorld");
            PLAYER_INTERACT_MANAGER = ReflectionUtils.nmsClass("server.level", "PlayerInteractManager");
            ENUM_GAME_MODE = ReflectionUtils.nmsClass("world.level", "EnumGamemode");
            PLAYER_LIST = ReflectionUtils.nmsClass("server.players", "PlayerList");
            PACKET = ReflectionUtils.nmsClass("network.protocol", "Packet");

            CRAFT_SERVER = ReflectionUtils.obcClass("CraftServer");
            CRAFT_PLAYER = ReflectionUtils.obcClass("entity.CraftPlayer");

            GET_BUKKIT_ENTITY_METHOD = WrapperReflections.ENTITY.getMethod("getBukkitEntity");

            Class<?> enumProtocolDirection = ReflectionUtils.nmsClass("network.protocol", "EnumProtocolDirection");
            CLIENTBOUND_PROTOCOL_DIRECTION = Enum.valueOf(enumProtocolDirection.asSubclass(Enum.class), "CLIENTBOUND");
            Class<?> enumProtocol = ReflectionUtils.nmsClass("network", "EnumProtocol");
            PLAY_ENUM_PROTOCOL = enumProtocol.getMethod("a", int.class).invoke(null, 0);
            ID_FROM_PACKET_METHOD = PLAY_ENUM_PROTOCOL.getClass().getMethod("a", enumProtocolDirection, PACKET);
            if (ProtocolVersion.getServerVersion().isLower(ProtocolVersion.MINECRAFT_1_18)) {
                SERIALIZE_PACKET_METHOD = PACKET.getMethod("b", WrapperReflections.PACKET_DATA_SERIALIZER);
            } else {
                SERIALIZE_PACKET_METHOD = PACKET.getMethod("a", WrapperReflections.PACKET_DATA_SERIALIZER);
            }

            PLAYERS_FIELD = ReflectionUtils.findFieldEquals(PLAYER_LIST, List.class);
            PLAYER_VIEW_FIELD = ReflectionUtils.getField(CRAFT_SERVER, "playerView");

            Object minecraftServerInstance = MINECRAFT_SERVER.getMethod("getServer").invoke(null);
            PLAYER_LIST_INSTANCE = ReflectionUtils.findFieldEquals(MINECRAFT_SERVER, PLAYER_LIST)
                    .get(minecraftServerInstance);

            PLAYER_LIST_FIELD_INSTANCE = (List) PLAYERS_FIELD.get(PLAYER_LIST_INSTANCE);

            Object craftServerInstance = Bukkit.class.getMethod("getServer").invoke(null);
            CRAFT_SERVER_INSTANCE = CRAFT_SERVER.cast(craftServerInstance);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static ClientboundPacket readClientboundPacket(Object packetObject) {
        if (!PACKET.isAssignableFrom(packetObject.getClass())) return null;

        try {
            Integer packetId = getClientboundPacketId(packetObject);
            if (packetId != null && MCReplayAPI.getPacketLib().getPacketRegistry().isClientboundRegistered(packetId)) {
                ByteBuffer buffer = serializePacket(packetObject);

                ReplayByteBuffer byteBuffer = new ReplayByteBuffer(buffer);
                return MCReplayAPI.getPacketLib().getPacketRegistry().getClientboundPacket(packetId, byteBuffer);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

    private static @Nullable Integer getClientboundPacketId(@NotNull Object packetObject) {
        try {
            return (Integer) ID_FROM_PACKET_METHOD.invoke(PLAY_ENUM_PROTOCOL, CLIENTBOUND_PROTOCOL_DIRECTION, packetObject);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static @NotNull ByteBuffer serializePacket(Object packetObject) throws Exception {
        Object packetDataSerializer = WrapperReflections.createPacketDataSerializer(Unpooled.buffer());
        ((ByteBuf) packetDataSerializer).writerIndex(0);
        ((ByteBuf) packetDataSerializer).readerIndex(0);
        serializePacket(packetObject, packetDataSerializer);

        return ByteBuffer.wrap(((ByteBuf) packetDataSerializer).array());
    }

    private static void serializePacket(Object minecraftPacket, Object packetDataSerializer) {
        try {
            SERIALIZE_PACKET_METHOD.invoke(minecraftPacket, packetDataSerializer);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}