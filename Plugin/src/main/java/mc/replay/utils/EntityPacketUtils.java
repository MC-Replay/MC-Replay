package mc.replay.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import mc.replay.MCReplayPlugin;
import mc.replay.utils.reflection.MinecraftReflections;
import mc.replay.utils.reflection.nms.MinecraftPlayerNMS;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.UUID;

import static mc.replay.utils.reflection.nms.NPCPacketUtils.*;

public class EntityPacketUtils {

    private static Object teamObject;
    private static Executable INITIALIZE_SCOREBOARD_TEAM;

    static {
        try {
            teamObject = SCOREBOARD_CREATE_TEAM_METHOD.invoke(SCOREBOARD_INSTANCE, "ReplayPlayers");

            Object color = GET_ENUM_CHAT_FORMAT_BY_NAME.invoke(null, "RED");
            SCOREBOARD_SET_COLOR_METHOD.invoke(teamObject, color);

            INITIALIZE_SCOREBOARD_TEAM = (MinecraftReflections.isRepackaged())
                    ? PACKET_PLAY_OUT_SCOREBOARD_TEAM.getMethod("a", SCOREBOARD_TEAM, boolean.class)
                    : PACKET_PLAY_OUT_SCOREBOARD_TEAM.getConstructor(SCOREBOARD_TEAM, int.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static Object spawnNPC(Player viewer, Location location, String name, Property skinTexture) {
        if (location == null || location.getWorld() == null || viewer == null) return null;

        try {
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);
            if (skinTexture != null) {
                gameProfile.getProperties().clear();
                gameProfile.getProperties().put("textures", skinTexture);
            }

            Object craftWorld = CRAFT_WORLD.cast(location.getWorld());
            Object worldServer = GET_WORLD_SERVER.invoke(craftWorld);

            final Object entityPlayer = createEntityPlayer(worldServer, gameProfile);
            int entityId = getEntityId(entityPlayer);
            SET_ENTITY_POSITION.invoke(entityPlayer, location.getX(), location.getY(), location.getZ());

            ((Collection<String>) GET_PLAYER_NAMES_IN_TEAM.invoke(teamObject)).add(name);

            Object dataWatcher = GET_DATA_WATCHER.invoke(entityPlayer);
            Object skinDataWatcherObject = GET_DATA_WATCHER_OBJECT.invoke(DATA_WATCHER_BYTE_SERIALIZER_INSTANCE, (MinecraftReflections.isRepackaged()) ? 17 : 16);
            byte overlays = JACKET | LEFT_SLEEVE | RIGHT_SLEEVE | LEFT_PANTS | RIGHT_PANTS | HAT;
            SET_DATA_WATCHER_OBJECT.invoke(dataWatcher, skinDataWatcherObject, overlays);

            entityPlayer.getClass().getMethod("setFlag", int.class, boolean.class).invoke(entityPlayer, 6, true);

            Object addArray = createSingleEntityPlayerArray(entityPlayer);

            Object packetPlayOutPlayerInfoAdd = PACKET_PLAY_OUT_PLAYER_INFO.getConstructor(ENUM_PLAYER_INFO_ACTION, addArray.getClass()).newInstance(ENUM_PLAYER_INFO_ACTION_ADD_PLAYER, addArray);
            Object packetPlayOutNamedEntitySpawn = PACKET_PLAY_OUT_NAMED_ENTITY_SPAWN_CONSTRUCTOR.newInstance(entityPlayer);
            Object packetPlayOutEntityMetadata = PACKET_PLAY_OUT_ENTITY_METADATA_CONSTRUCTOR.newInstance(entityId, dataWatcher, true);
            Object packetPlayOutScoreboardTeam = createScoreboardTeamPacket(teamObject);

            MinecraftPlayerNMS.sendPacket(viewer, packetPlayOutPlayerInfoAdd);
            MinecraftPlayerNMS.sendPacket(viewer, packetPlayOutNamedEntitySpawn);
            MinecraftPlayerNMS.sendPacket(viewer, packetPlayOutEntityMetadata);
            MinecraftPlayerNMS.sendPacket(viewer, packetPlayOutScoreboardTeam);

            updateRotation(viewer, location.getYaw(), location.getPitch(), entityPlayer, true);

            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        Object removeArray = createSingleEntityPlayerArray(entityPlayer);
                        Object packetPlayOutPlayerInfoRemove = PACKET_PLAY_OUT_PLAYER_INFO.getConstructor(ENUM_PLAYER_INFO_ACTION, removeArray.getClass())
                                .newInstance(ENUM_PLAYER_INFO_ACTION_REMOVE_PLAYER, removeArray);

                        MinecraftPlayerNMS.sendPacket(viewer, packetPlayOutPlayerInfoRemove);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }.runTaskLaterAsynchronously(MCReplayPlugin.getInstance(), 20L);

            return entityPlayer;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object spawnEntity(Player viewer, Location location, EntityType entityType) {
        //TODO entity spawn
        //        if (location == null || location.getWorld() == null || viewer == null || entityType == EntityType.PLAYER)
        //            return null;
        //
        //        try {
        //            EntityTypes<?> entityTypes = EntityTypes.getByName(entityType.getKey().getKey()).orElse(null);
        //            if (entityTypes == null) return null;
        //
        //            Entity entity = entityTypes.a(((CraftWorld) location.getWorld()).getHandle());
        //            if (entity == null) return null;
        //
        //            SET_ENTITY_POSITION.invoke(entity, location.getX(), location.getY(), location.getZ());
        //
        //            Object dataWatcher = GET_DATA_WATCHER.invoke(entity);
        //
        //            PacketPlayOutSpawnEntity packet = new PacketPlayOutSpawnEntity(entity);
        //            Object packetPlayOutEntityMetadata = PACKET_PLAY_OUT_ENTITY_METADATA_CONSTRUCTOR.newInstance(entity.getId(), dataWatcher, true);
        //
        //            MinecraftPlayerNMS.sendPacket(viewer, packet);
        //            MinecraftPlayerNMS.sendPacket(viewer, packetPlayOutEntityMetadata);
        //
        //            updateRotation(viewer, location.getYaw(), location.getPitch(), entity, true);
        //
        //            return entity;
        //        } catch (Exception exception) {
        //            exception.printStackTrace();
        //            return null;
        //        }
        return null;
    }

    public static void updateRotation(Player viewer, float yaw, float pitch, Object entityPlayer) {
        updateRotation(viewer, yaw, pitch, entityPlayer, false);
    }

    public static void updateRotation(Player viewer, float yaw, float pitch, Object entityPlayer, boolean spawn) {
        try {
            int entityId = getEntityId(entityPlayer);

            Object packetPlayOutEntityLook = PACKET_PLAY_OUT_ENTITY_LOOK_CONSTRUCTOR.newInstance(entityId, getCompressedAngle(yaw), getCompressedAngle(pitch), true);
            Object packetPlayOutEntityHeadRotation = PACKET_PLAY_OUT_ENTITY_HEAD_ROTATION_CONSTRUCTOR.newInstance(entityPlayer, getCompressedAngle(yaw));

            MinecraftPlayerNMS.sendPacket(viewer, packetPlayOutEntityLook);
            MinecraftPlayerNMS.sendPacket(viewer, packetPlayOutEntityHeadRotation);

            if (spawn) {
                Object packetPlayOutAnimation = PACKET_PLAY_OUT_ANIMATION_CONSTRUCTOR.newInstance(entityPlayer, SWING_ARM_ANIMATION_ID);
                MinecraftPlayerNMS.sendPacket(viewer, packetPlayOutAnimation);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void destroy(Player viewer, Object entityPlayer) {
        try {
            int entityId = getEntityId(entityPlayer);

            Object packetPlayOutEntityDestroy = PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR.newInstance((Object) new int[]{entityId});
            MinecraftPlayerNMS.sendPacket(viewer, packetPlayOutEntityDestroy);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static int getEntityId(Object entity) {
        if (entity == null) return -1;

        try {
            return (int) GET_ENTITY_ID.invoke(entity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    public static byte getCompressedAngle(float value) {
        return (byte) (value * 256 / 360);
    }

    private static Object createScoreboardTeamPacket(Object team) throws Exception {
        if (INITIALIZE_SCOREBOARD_TEAM instanceof Method method) {
            return method.invoke(null, team, true);
        } else if (INITIALIZE_SCOREBOARD_TEAM instanceof Constructor<?> constructor) {
            return constructor.newInstance(team, 0);
        }

        return null;
    }
}