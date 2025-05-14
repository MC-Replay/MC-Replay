package mc.replay.replay.session.listener;

import mc.replay.api.MCReplay;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.utils.config.templates.ReplaySettings;
import mc.replay.nms.MCReplayNMS;
import mc.replay.nms.entity.player.RPlayer;
import mc.replay.packetlib.data.entity.InteractEntityType;
import mc.replay.packetlib.data.entity.PlayerHand;
import mc.replay.packetlib.data.entity.player.DiggingStatus;
import mc.replay.packetlib.network.packet.serverbound.ServerboundPacketIdentifier;
import mc.replay.packetlib.network.packet.serverbound.play.*;
import mc.replay.replay.ReplayHandler;
import mc.replay.replay.session.ReplayPlayer;
import mc.replay.replay.session.entity.AbstractReplayEntity;
import mc.replay.replay.session.menu.ReplayPlayerInfoMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public final class ReplaySessionPacketListener {

    private final ReplayHandler replayHandler;
    private final MCReplay instance;

    public ReplaySessionPacketListener(ReplayHandler replayHandler, MCReplay instance) {
        this.replayHandler = replayHandler;
        this.instance = instance;

        this.listenAndInterceptInteractPackets();
        this.listenAndInterceptMovementPackets();
    }

    private void listenAndInterceptInteractPackets() {
        this.instance.getPacketLib().packetListener().listenServerbound(ServerboundPacketIdentifier.INTERACT_ENTITY, (player, serverboundPacket) -> {
            ReplayPlayer replayPlayer = this.replayHandler.getReplayPlayer(player);
            if (replayPlayer == null) return; // This is not a replay player, so no need to listen to the packet

            ServerboundInteractEntityPacket packet = (ServerboundInteractEntityPacket) serverboundPacket;

            if (packet.type() instanceof InteractEntityType.Interact interact) {
                if (interact.hand() == PlayerHand.MAIN_HAND) {
                    AbstractReplayEntity<?> replayEntity = replayPlayer.replaySession().getPlayTask().getEntityCache().getEntityByReplayId(packet.targetId());
                    if (replayEntity != null && replayEntity.getEntity() instanceof RPlayer entity) {
                        this.replayHandler.getInstance().getMenuHandler().openMenu(new ReplayPlayerInfoMenu(entity), player);
                    }
                }
            }
        });

        this.instance.getPacketLib().packetListener().interceptServerbound(ServerboundPacketIdentifier.PLAYER_BLOCK_PLACEMENT, (player, serverboundPacket) -> {
            ReplayPlayer replayPlayer = this.replayHandler.getReplayPlayer(player);
            return replayPlayer != null; // Only intercept if the player is a replay player
        });

        this.instance.getPacketLib().packetListener().interceptServerbound(ServerboundPacketIdentifier.PLAYER_DIGGING, (player, serverboundPacket) -> {
            ReplayPlayer replayPlayer = this.replayHandler.getReplayPlayer(player);
            if (replayPlayer == null) return false; // This is not a replay player, so no need to intercept the packet

            ServerboundPlayerDiggingPacket packet = (ServerboundPlayerDiggingPacket) serverboundPacket;
            return packet.status() == DiggingStatus.STARTED_DIGGING || packet.status() == DiggingStatus.CANCELLED_DIGGING || packet.status() == DiggingStatus.FINISHED_DIGGING;
        });
    }

    private void listenAndInterceptMovementPackets() {
        this.instance.getPacketLib().packetListener().interceptServerbound(ServerboundPacketIdentifier.PLAYER_POSITION_AND_ROTATION, (player, serverboundPacket) -> {
            ReplayPlayer replayPlayer = this.replayHandler.getReplayPlayer(player);
            if (replayPlayer == null) return false; // This is not a replay player, so no need to intercept the packet

            ServerboundPlayerPositionAndRotationPacket packet = (ServerboundPlayerPositionAndRotationPacket) serverboundPacket;

            Location from = player.getLocation().clone();
            Location to = new Location(from.getWorld(), packet.x(), packet.y(), packet.z(), packet.yaw(), packet.pitch());

            this.handleMovementPacket(player, to);
            return true;
        });

        this.instance.getPacketLib().packetListener().interceptServerbound(ServerboundPacketIdentifier.PLAYER_POSITION, (player, serverboundPacket) -> {
            ReplayPlayer replayPlayer = this.replayHandler.getReplayPlayer(player);
            if (replayPlayer == null) return false; // This is not a replay player, so no need to intercept the packet

            ServerboundPlayerPositionPacket packet = (ServerboundPlayerPositionPacket) serverboundPacket;

            Location from = player.getLocation().clone();
            Location to = new Location(from.getWorld(), packet.x(), packet.y(), packet.z(), from.getYaw(), from.getPitch());

            this.handleMovementPacket(player, to);
            return true;
        });

        this.instance.getPacketLib().packetListener().interceptServerbound(ServerboundPacketIdentifier.PLAYER_ROTATION, (player, serverboundPacket) -> {
            ReplayPlayer replayPlayer = this.replayHandler.getReplayPlayer(player);
            if (replayPlayer == null) return false; // This is not a replay player, so no need to intercept the packet

            ServerboundPlayerRotationPacket packet = (ServerboundPlayerRotationPacket) serverboundPacket;

            Location from = player.getLocation().clone();
            Location to = new Location(from.getWorld(), from.getX(), from.getY(), from.getZ(), packet.yaw(), packet.pitch());

            this.handleMovementPacket(player, to);
            return true;
        });
    }

    // TODO test reflection on all server versions
    private void handleMovementPacket(Player player, Location to) {
        Location from = player.getLocation().clone();
        if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ() && from.getYaw() == to.getYaw() && from.getPitch() == to.getPitch()) {
            return; // No need to set the player position if the player didn't move
        }

        MCReplayNMS.getInstance().movePlayerSync(player, to, () -> {
            boolean shouldChangeBlock = MCReplayAPI.getSettingsProcessor().getBoolean(ReplaySettings.REPLAY_PLAYER_MOVEMENT_BLOCK_CHANGE);
            if (shouldChangeBlock && from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
                return; // No need to call the event if the player didn't change block
            }

            PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent(player, from, to);
            Bukkit.getPluginManager().callEvent(playerMoveEvent);
        });
    }
}