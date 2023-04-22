package mc.replay.replay.session.listener;

import mc.replay.api.MCReplay;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.utils.config.templates.ReplaySettings;
import mc.replay.packetlib.data.entity.InteractEntityType;
import mc.replay.packetlib.data.entity.PlayerHand;
import mc.replay.packetlib.network.packet.serverbound.ServerboundPacketIdentifier;
import mc.replay.packetlib.network.packet.serverbound.play.ServerboundInteractEntityPacket;
import mc.replay.packetlib.network.packet.serverbound.play.ServerboundPlayerPositionAndRotationPacket;
import mc.replay.packetlib.network.packet.serverbound.play.ServerboundPlayerPositionPacket;
import mc.replay.packetlib.network.packet.serverbound.play.ServerboundPlayerRotationPacket;
import mc.replay.replay.ReplayHandler;
import mc.replay.replay.session.ReplayPlayerImpl;
import mc.replay.replay.session.entity.AbstractReplayEntity;
import mc.replay.replay.session.menu.ReplayPlayerInfoMenu;
import mc.replay.wrapper.entity.PlayerWrapper;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public final class ReplaySessionPacketListener {

    private final ReplayHandler replayHandler;
    private final MCReplay instance;

    public ReplaySessionPacketListener(ReplayHandler replayHandler, MCReplay instance) {
        this.replayHandler = replayHandler;
        this.instance = instance;

        this.listenInteractPackets();
        this.interceptMovementPackets();
    }

    private void listenInteractPackets() {
        this.instance.getPacketLib().packetListener().listenServerbound(ServerboundPacketIdentifier.INTERACT_ENTITY, (player, serverboundPacket) -> {
            ServerboundInteractEntityPacket packet = (ServerboundInteractEntityPacket) serverboundPacket;

            if (packet.type() instanceof InteractEntityType.Interact interact) {
                if (interact.hand() == PlayerHand.MAIN_HAND) {
                    ReplayPlayerImpl replayPlayer = this.replayHandler.getReplayPlayer(player);
                    if (replayPlayer == null) return;

                    AbstractReplayEntity<?> replayEntity = replayPlayer.replaySession().getReplayEntityByReplayId(packet.targetId());
                    if (replayEntity != null && replayEntity.getEntity() instanceof PlayerWrapper entity) {
                        this.replayHandler.getInstance().getMenuHandler().openMenu(new ReplayPlayerInfoMenu(entity), player);
                    }
                }
            }
        });
    }

    // TODO use reflection to set player position and rotation
    private void interceptMovementPackets() {
        this.instance.getPacketLib().packetListener().interceptServerbound(ServerboundPacketIdentifier.PLAYER_POSITION_AND_ROTATION, (player, serverboundPacket) -> {
            ReplayPlayerImpl replayPlayer = this.replayHandler.getReplayPlayer(player);
            if (replayPlayer == null) return false; // This is not a replay player, so no need to intercept the packet

            ServerboundPlayerPositionAndRotationPacket packet = (ServerboundPlayerPositionAndRotationPacket) serverboundPacket;

            Location from = player.getLocation().clone();
            Location to = new Location(from.getWorld(), packet.x(), packet.y(), packet.z(), packet.yaw(), packet.pitch());

            this.handleMovementPacket(player, to);
            return true;
        });

        this.instance.getPacketLib().packetListener().interceptServerbound(ServerboundPacketIdentifier.PLAYER_POSITION, (player, serverboundPacket) -> {
            ReplayPlayerImpl replayPlayer = this.replayHandler.getReplayPlayer(player);
            if (replayPlayer == null) return false; // This is not a replay player, so no need to intercept the packet

            ServerboundPlayerPositionPacket packet = (ServerboundPlayerPositionPacket) serverboundPacket;

            Location from = player.getLocation().clone();
            Location to = new Location(from.getWorld(), packet.x(), packet.y(), packet.z(), from.getYaw(), from.getPitch());

            this.handleMovementPacket(player, to);
            return true;
        });

        this.instance.getPacketLib().packetListener().interceptServerbound(ServerboundPacketIdentifier.PLAYER_ROTATION, (player, serverboundPacket) -> {
            ReplayPlayerImpl replayPlayer = this.replayHandler.getReplayPlayer(player);
            if (replayPlayer == null) return false; // This is not a replay player, so no need to intercept the packet

            ServerboundPlayerRotationPacket packet = (ServerboundPlayerRotationPacket) serverboundPacket;

            Location from = player.getLocation().clone();
            Location to = new Location(from.getWorld(), from.getX(), from.getY(), from.getZ(), packet.yaw(), packet.pitch());

            this.handleMovementPacket(player, to);
            return true;
        });
    }

    private void handleMovementPacket(Player player, Location to) {
        Location from = player.getLocation().clone();
        if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ() && from.getYaw() == to.getYaw() && from.getPitch() == to.getPitch()) {
            return; // No need to set the player position if the player didn't move
        }

        MinecraftServer.getServer().execute(() -> { // Make sure to execute this on the main thread
            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
            entityPlayer.setPositionRaw(
                    to.getX(),
                    to.getY(),
                    to.getZ()
            );

            entityPlayer.yaw = to.getYaw();
            entityPlayer.pitch = to.getPitch();

            boolean shouldChangeBlock = MCReplayAPI.getSettingsProcessor().getBoolean(ReplaySettings.REPLAY_PLAYER_MOVEMENT_BLOCK_CHANGE);
            if (shouldChangeBlock && from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
                return; // No need to call the event if the player didn't change block
            }

            PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent(player, from, to);
            Bukkit.getPluginManager().callEvent(playerMoveEvent);
        });
    }
}