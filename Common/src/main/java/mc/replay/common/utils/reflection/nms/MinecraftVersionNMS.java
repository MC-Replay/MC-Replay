package mc.replay.common.utils.reflection.nms;

import mc.replay.common.utils.reflection.MinecraftReflections;
import mc.replay.common.utils.reflection.version.MinecraftVersion;
import mc.replay.common.utils.reflection.version.ProtocolVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class MinecraftVersionNMS {

    private static String SERVER_VERSION = null;
    private static ProtocolVersion SERVER_PROTOCOL_VERSION = ProtocolVersion.UNKNOWN;

    public static String getServerProtocolVersion() {
        if (SERVER_VERSION != null) return SERVER_VERSION;

        String bv = Bukkit.getServer().getClass().getPackage().getName();
        SERVER_VERSION = bv.substring(bv.lastIndexOf('.') + 1);
        return SERVER_VERSION;
    }

    public static ProtocolVersion getServerProtocolVersionEnum() {
        if (SERVER_PROTOCOL_VERSION != ProtocolVersion.UNKNOWN) {
            return SERVER_PROTOCOL_VERSION;
        }

        try {
            Class<?> minecraftServerClass = MinecraftReflections.nmsClass("server", "MinecraftServer");
            Object minecraftServer = minecraftServerClass.getMethod("getServer").invoke(null);

            Object craftServer = minecraftServerClass.getField("server").get(minecraftServer);
            String minecraftVersion = (String) craftServer.getClass().getMethod("getMinecraftVersion").invoke(craftServer);

            return SERVER_PROTOCOL_VERSION = ProtocolVersion.getByVersionString(minecraftVersion);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ProtocolVersion.UNKNOWN;
        }
    }

    public static MinecraftVersion getServerVersion() {
        String protocolVersion = getServerProtocolVersion();

        for (MinecraftVersion version : MinecraftVersion.values()) {
            if (protocolVersion.startsWith(version.getProtocol())) {
                return version;
            }
        }

        return null;
    }

    public static ProtocolVersion getPlayerProtocolVersion(Player player) {
        try {
            Class<?> viaClass = Class.forName("us.myles.ViaVersion.api.Via");
            Object viaAPI = viaClass.getMethod("getAPI").invoke(viaClass);

            Object version = viaAPI.getClass().getMethod("getPlayerVersion", UUID.class).invoke(viaAPI, player.getUniqueId());

            return ProtocolVersion.getVersion((int) version);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return ProtocolVersion.UNKNOWN;
        }
    }

    public static MinecraftVersion getPlayerMinecraftVersion(Player player) {
        return getPlayerProtocolVersion(player).getMinecraftVersion();
    }
}