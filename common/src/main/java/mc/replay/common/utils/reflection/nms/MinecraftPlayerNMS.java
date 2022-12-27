package mc.replay.common.utils.reflection.nms;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.netty.channel.Channel;
import mc.replay.common.utils.reflection.version.ProtocolVersion;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

import static mc.replay.common.utils.reflection.nms.MinecraftNMS.*;

public final class MinecraftPlayerNMS {

    public static Object getEntityPlayer(Player player) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return player.getClass().getMethod("getHandle").invoke(player);
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Object entityPlayer = GET_PLAYER_HANDLE_METHOD.invoke(player);
            Object playerConnection = GET_PLAYER_CONNECTION_METHOD.invoke(entityPlayer);
            SEND_PACKET_METHOD.invoke(playerConnection, packet);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void sendPacket(Object packet) {
        try {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendPacket(player, packet);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void sendPacketNotSelf(Player player, Object packet) {
        try {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.getUniqueId().equals(player.getUniqueId())) continue;

                sendPacket(all, packet);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static Channel getPacketChannel(Player player) {
        try {
            Object entityPlayer = getEntityPlayer(player);
            Object networkManager = ENTITY_PLAYER.getField("networkManager").get(entityPlayer);

            Object channel;
            if (MinecraftVersionNMS.getServerProtocolVersionEnum().isLowerOrEqual(ProtocolVersion.MINECRAFT_1_17_1)) {
                channel = NETWORK_MANAGER.getField("channel").get(networkManager);
            } else if (MinecraftVersionNMS.getServerProtocolVersionEnum().isEqual(ProtocolVersion.MINECRAFT_1_18_1)) {
                channel = NETWORK_MANAGER.getField("k").get(networkManager);
            } else {
                channel = NETWORK_MANAGER.getField("m").get(networkManager);
            }

            return (Channel) channel;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Property getPlayerSkinTexture(Player player) {
        try {
            Object craftPlayer = CRAFT_PLAYER.cast(player);
            GameProfile playerProfile = (GameProfile) CRAFT_PLAYER.getMethod("getProfile").invoke(craftPlayer);

            return playerProfile.getProperties().get("textures").iterator().next();
        } catch (Exception exception) {
            return null;
        }
    }

    public static void sendTitle(Player player, String title, String subtitle, int stayTime) {
        if (player.isOnline()) {
            sendTitle(player, title, subtitle, 5, stayTime, 5);
        }
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stayTime, int fadeOut) {
        if (player.isOnline()) {
            player.sendTitle(title, subtitle, fadeIn, stayTime, fadeOut);
        }
    }

    public static void sendActionbar(Player player, String message) {
        if (player.isOnline()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
        }
    }
}