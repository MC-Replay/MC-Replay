package mc.replay.replay.utils;

import mc.replay.api.MCReplayAPI;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.nms.entity.REntity;
import mc.replay.nms.entity.RLivingEntity;
import mc.replay.nms.entity.metadata.ObjectDataProvider;
import mc.replay.nms.entity.metadata.PlayerMetadata;
import mc.replay.nms.entity.metadata.ShooterProvider;
import mc.replay.nms.entity.player.PlayerProfile;
import mc.replay.nms.entity.player.RPlayer;
import mc.replay.nms.entity.player.SkinTexture;
import mc.replay.nms.scoreboard.RScoreboardTeam;
import mc.replay.packetlib.PacketLib;
import mc.replay.packetlib.data.PlayerProfileProperty;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.data.entity.EntityAnimation;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.data.team.CollisionRule;
import mc.replay.packetlib.data.team.TeamAction;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacketIdentifier;
import mc.replay.packetlib.network.packet.clientbound.play.*;
import mc.replay.packetlib.network.packet.clientbound.play.legacy.ClientboundLivingEntitySpawn754_758Packet;
import mc.replay.packetlib.network.packet.clientbound.play.legacy.ClientboundPlayerInfo754_758Packet;
import mc.replay.packetlib.utils.ProtocolVersion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public final class EntityPacketUtils {

    private EntityPacketUtils() {
    }

    private static final RScoreboardTeam REPLAY_SUSPECTS_TEAM = new RScoreboardTeam("ReplaySuspects")
            .color(NamedTextColor.RED)
            .collisionRule(CollisionRule.NEVER);

    public static RPlayer spawnNPC(Collection<IReplayPlayer> viewers, Pos position, String name, SkinTexture skinTexture, Map<Integer, Metadata.Entry<?>> originMetadata) {
        if (viewers == null || viewers.isEmpty()) return null;

        Map<String, PlayerProfileProperty> properties = new HashMap<>();
        if (skinTexture != null) {
            properties = Map.of(
                    SkinTexture.TEXTURES_KEY, new PlayerProfileProperty(SkinTexture.TEXTURES_KEY, skinTexture.value(), skinTexture.signature())
            );
        }
        PlayerProfile playerProfile = new PlayerProfile.PlayerProfileImpl(UUID.randomUUID(), name, properties);

        RPlayer playerWrapper = new RPlayer(playerProfile);
        playerWrapper.setPosition(position);
        playerWrapper.addMetadata(originMetadata);

        PlayerMetadata metadata = playerWrapper.getMetadata();

        metadata.setHasGlowingEffect(true);

        REPLAY_SUSPECTS_TEAM.addEntry(playerWrapper.getUsername());

        TeamAction.CreateTeamAction createTeamAction = new TeamAction.CreateTeamAction(
                REPLAY_SUSPECTS_TEAM.displayName(),
                (byte) 0,
                REPLAY_SUSPECTS_TEAM.visibility(),
                REPLAY_SUSPECTS_TEAM.collisionRule(),
                REPLAY_SUSPECTS_TEAM.color(),
                REPLAY_SUSPECTS_TEAM.prefix(),
                REPLAY_SUSPECTS_TEAM.suffix(),
                REPLAY_SUSPECTS_TEAM.entries()
        );

        ClientboundPacket infoPacket;
        if (PacketLib.getPacketRegistry().isClientboundRegistered(ClientboundPacketIdentifier.PLAYER_INFO)) {
            infoPacket = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, new ClientboundPlayerInfoPacket.Entry(
                    playerWrapper.getUniqueId(),
                    playerWrapper.getUsername(),
                    List.copyOf(playerProfile.properties().values()),
                    false,
                    0,
                    GameMode.SURVIVAL,
                    null
            ));
        } else {
            ClientboundPlayerInfo754_758Packet.PlayerInfoEntry.AddPlayer addPlayer = new ClientboundPlayerInfo754_758Packet.PlayerInfoEntry.AddPlayer(
                    playerWrapper.getUniqueId(),
                    playerWrapper.getUsername(),
                    List.copyOf(playerProfile.properties().values()),
                    GameMode.SURVIVAL,
                    0,
                    (Component) null
            );

            infoPacket = new ClientboundPlayerInfo754_758Packet(ClientboundPlayerInfo754_758Packet.PlayerInfoAction.ADD_PLAYER, addPlayer);
        }

        ClientboundPlayerSpawnPacket spawnPacket = new ClientboundPlayerSpawnPacket(playerWrapper.getEntityId(), playerWrapper.getUniqueId(), playerWrapper.getPosition());
        ClientboundEntityMetadataPacket metadataPacket = new ClientboundEntityMetadataPacket(playerWrapper.getEntityId(), metadata.getEntries());
        ClientboundTeamsPacket teamsPacket = new ClientboundTeamsPacket(REPLAY_SUSPECTS_TEAM.name(), createTeamAction);

        for (IReplayPlayer viewerReplayPlayer : viewers) {
            Player viewer = viewerReplayPlayer.player();

            MCReplayAPI.getPacketLib().sendPacket(viewer, infoPacket);
            MCReplayAPI.getPacketLib().sendPacket(viewer, spawnPacket);
            MCReplayAPI.getPacketLib().sendPacket(viewer, metadataPacket);
            MCReplayAPI.getPacketLib().sendPacket(viewer, teamsPacket);

            updateRotation(viewer, position.yaw(), position.pitch(), playerWrapper, true);
        }

        // If the server doesn't support the PlayerInfoRemovePacket, we have to remove the player after 1 second
        if (!PacketLib.getPacketRegistry().isClientboundRegistered(ClientboundPacketIdentifier.PLAYER_INFO_REMOVE)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    ClientboundPlayerInfo754_758Packet.PlayerInfoEntry.RemovePlayer removePlayer = new ClientboundPlayerInfo754_758Packet.PlayerInfoEntry.RemovePlayer(playerWrapper.getUniqueId());

                    for (IReplayPlayer viewerReplayPlayer : viewers) {
                        Player viewer = viewerReplayPlayer.player();

                        MCReplayAPI.getPacketLib().sendPacket(viewer, new ClientboundPlayerInfo754_758Packet(ClientboundPlayerInfo754_758Packet.PlayerInfoAction.REMOVE_PLAYER, removePlayer));
                    }
                }
            }.runTaskLaterAsynchronously(MCReplayAPI.getJavaPlugin(), 20L);
        }

        return playerWrapper;
    }

    public static REntity spawnEntity(IEntityProvider entityProvider, Collection<IReplayPlayer> viewers, Pos position, EntityType entityType, int data, Vector velocity) {
        if (viewers == null || viewers.isEmpty() || entityType == EntityType.PLAYER)
            return null;

        REntity entityWrapper;
        if (entityType.isAlive()) {
            entityWrapper = new RLivingEntity(entityType, UUID.randomUUID());
        } else {
            entityWrapper = new REntity(entityType, UUID.randomUUID());
        }

        entityWrapper.setPosition(position);

        short velocityX = 0, velocityY = 0, velocityZ = 0;
        boolean requiresVelocityPacket = false;
        if (entityWrapper.getMetadata() instanceof ObjectDataProvider provider) {
            if (entityWrapper.getMetadata() instanceof ShooterProvider shooterProvider) {
                RecordableEntityData entityData = entityProvider.getEntity(data);
                if (entityData != null) {
                    shooterProvider.setShooterId(data = entityData.entityId());
                }
            } else {
                data = provider.getObjectData();
            }

            if (provider.requiresVelocityPacketAtSpawn()) {
                requiresVelocityPacket = true;
                velocityX = (short) velocity.getX();
                velocityY = (short) velocity.getY();
                velocityZ = (short) velocity.getZ();
            }
        }

        ClientboundPacket spawnPacket;
        if (ProtocolVersion.getServerVersion().isHigherOrEqual(ProtocolVersion.MINECRAFT_1_19_4) || !entityType.isAlive()) {
            spawnPacket = new ClientboundEntitySpawnPacket(
                    entityWrapper.getEntityId(),
                    entityWrapper.getUniqueId(),
                    entityWrapper.getMappedType().id(),
                    entityWrapper.getPosition(),
                    entityWrapper.getPosition().yaw(),
                    data,
                    velocityX,
                    velocityY,
                    velocityZ
            );
        } else {
            spawnPacket = new ClientboundLivingEntitySpawn754_758Packet(
                    entityWrapper.getEntityId(),
                    entityWrapper.getUniqueId(),
                    entityWrapper.getMappedType().id(),
                    entityWrapper.getPosition(),
                    entityWrapper.getPosition().yaw(),
                    velocityX,
                    velocityY,
                    velocityZ
            );
        }

        ClientboundEntityVelocityPacket velocityPacket = new ClientboundEntityVelocityPacket(entityWrapper.getEntityId(), velocityX, velocityY, velocityZ);

        for (IReplayPlayer replayPlayer : viewers) {
            Player viewer = replayPlayer.player();

            MCReplayAPI.getPacketLib().sendPacket(viewer, spawnPacket);
            if (requiresVelocityPacket) {
                MCReplayAPI.getPacketLib().sendPacket(viewer, velocityPacket);
            }

            updateRotation(viewer, position.yaw(), position.pitch(), entityWrapper, true);
        }

        return entityWrapper;
    }

    public static void updateRotation(Player viewer, float yaw, float pitch, REntity entityWrapper, boolean spawn) {
        ClientboundEntityRotationPacket rotationPacket = new ClientboundEntityRotationPacket(entityWrapper.getEntityId(), yaw, pitch, false);
        ClientboundEntityHeadRotationPacket headRotationPacket = new ClientboundEntityHeadRotationPacket(entityWrapper.getEntityId(), yaw);

        MCReplayAPI.getPacketLib().sendPacket(viewer, rotationPacket);
        MCReplayAPI.getPacketLib().sendPacket(viewer, headRotationPacket);

        if (spawn) {
            ClientboundEntityAnimationPacket animationPacket = new ClientboundEntityAnimationPacket(entityWrapper.getEntityId(), EntityAnimation.SWING_MAIN_ARM);
            MCReplayAPI.getPacketLib().sendPacket(viewer, animationPacket);
        }
    }

    public static void destroy(Collection<IReplayPlayer> viewers, REntity entityWrapper) {
        ClientboundEntityDestroyPacket destroyPacket = new ClientboundEntityDestroyPacket(entityWrapper.getEntityId());

        for (IReplayPlayer replayPlayer : viewers) {
            Player viewer = replayPlayer.player();

            MCReplayAPI.getPacketLib().sendPacket(viewer, destroyPacket);
        }
    }
}