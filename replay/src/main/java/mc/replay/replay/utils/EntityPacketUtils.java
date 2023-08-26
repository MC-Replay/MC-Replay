package mc.replay.replay.utils;

import mc.replay.api.MCReplayAPI;
import mc.replay.api.recordables.data.IEntityProvider;
import mc.replay.api.recordables.data.RecordableEntityData;
import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.packetlib.data.PlayerProfileProperty;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.data.entity.EntityAnimation;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.data.entity.player.PlayerInfoAction;
import mc.replay.packetlib.data.entity.player.PlayerInfoEntry;
import mc.replay.packetlib.data.team.CollisionRule;
import mc.replay.packetlib.data.team.TeamAction;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.*;
import mc.replay.packetlib.network.packet.clientbound.play.legacy.ClientboundLivingEntitySpawn754_758Packet;
import mc.replay.packetlib.utils.ProtocolVersion;
import mc.replay.wrapper.data.PlayerProfile;
import mc.replay.wrapper.data.SkinTexture;
import mc.replay.wrapper.entity.EntityWrapper;
import mc.replay.wrapper.entity.LivingEntityWrapper;
import mc.replay.wrapper.entity.PlayerWrapper;
import mc.replay.wrapper.entity.metadata.ObjectDataProvider;
import mc.replay.wrapper.entity.metadata.PlayerMetadata;
import mc.replay.wrapper.entity.metadata.ShooterProvider;
import mc.replay.wrapper.team.TeamWrapper;
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

    private static final TeamWrapper REPLAY_SUSPECTS_TEAM = new TeamWrapper("ReplaySuspects")
            .withColor(NamedTextColor.RED)
            .withCollisionRule(CollisionRule.NEVER);

    public static PlayerWrapper spawnNPC(Collection<IReplayPlayer> viewers, Pos position, String name, SkinTexture skinTexture, Map<Integer, Metadata.Entry<?>> originMetadata) {
        if (viewers == null || viewers.isEmpty()) return null;

        Map<String, PlayerProfileProperty> properties = new HashMap<>();
        if (skinTexture != null) {
            properties = Map.of(
                    SkinTexture.TEXTURES_KEY, new PlayerProfileProperty(SkinTexture.TEXTURES_KEY, skinTexture.value(), skinTexture.signature())
            );
        }
        PlayerProfile playerProfile = new PlayerProfile(UUID.randomUUID(), name, properties);

        PlayerWrapper playerWrapper = new PlayerWrapper(playerProfile);
        playerWrapper.setPosition(position);
        playerWrapper.addMetadata(originMetadata);

        PlayerMetadata metadata = playerWrapper.getMetadata();

        metadata.setHasGlowingEffect(true);

        REPLAY_SUSPECTS_TEAM.addEntry(playerWrapper.getUsername());

        PlayerInfoEntry.AddPlayer addPlayer = new PlayerInfoEntry.AddPlayer(
                playerWrapper.getUniqueId(),
                playerWrapper.getUsername(),
                List.copyOf(playerProfile.properties().values()),
                GameMode.SURVIVAL,
                0,
                (Component) null
        );

        TeamAction.CreateTeamAction createTeamAction = new TeamAction.CreateTeamAction(
                REPLAY_SUSPECTS_TEAM.getDisplayName(),
                (byte) 0,
                REPLAY_SUSPECTS_TEAM.getVisibility(),
                REPLAY_SUSPECTS_TEAM.getCollisionRule(),
                REPLAY_SUSPECTS_TEAM.getColor(),
                REPLAY_SUSPECTS_TEAM.getPrefix(),
                REPLAY_SUSPECTS_TEAM.getSuffix(),
                REPLAY_SUSPECTS_TEAM.getEntries()
        );

        ClientboundPlayerInfoPacket infoPacket = new ClientboundPlayerInfoPacket(PlayerInfoAction.ADD_PLAYER, addPlayer);
        ClientboundPlayerSpawnPacket spawnPacket = new ClientboundPlayerSpawnPacket(playerWrapper.getEntityId(), playerWrapper.getUniqueId(), playerWrapper.getPosition());
        ClientboundEntityMetadataPacket metadataPacket = new ClientboundEntityMetadataPacket(playerWrapper.getEntityId(), metadata.getEntries());
        ClientboundTeamsPacket teamsPacket = new ClientboundTeamsPacket(REPLAY_SUSPECTS_TEAM.getName(), createTeamAction);

        for (IReplayPlayer viewerReplayPlayer : viewers) {
            Player viewer = viewerReplayPlayer.player();

            MCReplayAPI.getPacketLib().sendPacket(viewer, infoPacket);
            MCReplayAPI.getPacketLib().sendPacket(viewer, spawnPacket);
            MCReplayAPI.getPacketLib().sendPacket(viewer, metadataPacket);
            MCReplayAPI.getPacketLib().sendPacket(viewer, teamsPacket);

            updateRotation(viewer, position.yaw(), position.pitch(), playerWrapper, true);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                PlayerInfoEntry.RemovePlayer removePlayer = new PlayerInfoEntry.RemovePlayer(playerWrapper.getUniqueId());

                for (IReplayPlayer viewerReplayPlayer : viewers) {
                    Player viewer = viewerReplayPlayer.player();

                    MCReplayAPI.getPacketLib().sendPacket(viewer, new ClientboundPlayerInfoPacket(PlayerInfoAction.REMOVE_PLAYER, removePlayer));
                }
            }
        }.runTaskLaterAsynchronously(MCReplayAPI.getJavaPlugin(), 20L);

        return playerWrapper;
    }

    public static EntityWrapper spawnEntity(IEntityProvider entityProvider, Collection<IReplayPlayer> viewers, Pos position, EntityType entityType, int data, Vector velocity) {
        if (viewers == null || viewers.isEmpty() || entityType == EntityType.PLAYER)
            return null;

        EntityWrapper entityWrapper;
        if (entityType.isAlive()) {
            entityWrapper = new LivingEntityWrapper(entityType, UUID.randomUUID());
        } else {
            entityWrapper = new EntityWrapper(entityType, UUID.randomUUID());
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
                    entityWrapper.getType().getMapping().id(),
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
                    entityWrapper.getType().getMapping().id(),
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

    public static void updateRotation(Player viewer, float yaw, float pitch, EntityWrapper entityWrapper) {
        updateRotation(viewer, yaw, pitch, entityWrapper, false);
    }

    public static void updateRotation(Player viewer, float yaw, float pitch, EntityWrapper entityWrapper, boolean spawn) {
        ClientboundEntityRotationPacket rotationPacket = new ClientboundEntityRotationPacket(entityWrapper.getEntityId(), yaw, pitch, false);
        ClientboundEntityHeadRotationPacket headRotationPacket = new ClientboundEntityHeadRotationPacket(entityWrapper.getEntityId(), yaw);

        MCReplayAPI.getPacketLib().sendPacket(viewer, rotationPacket);
        MCReplayAPI.getPacketLib().sendPacket(viewer, headRotationPacket);

        if (spawn) {
            ClientboundEntityAnimationPacket animationPacket = new ClientboundEntityAnimationPacket(entityWrapper.getEntityId(), EntityAnimation.SWING_MAIN_ARM);
            MCReplayAPI.getPacketLib().sendPacket(viewer, animationPacket);
        }
    }

    public static void destroy(Collection<IReplayPlayer> viewers, EntityWrapper entityWrapper) {
        ClientboundEntityDestroyPacket destroyPacket = new ClientboundEntityDestroyPacket(entityWrapper.getEntityId());

        for (IReplayPlayer replayPlayer : viewers) {
            Player viewer = replayPlayer.player();

            MCReplayAPI.getPacketLib().sendPacket(viewer, destroyPacket);
        }
    }
}