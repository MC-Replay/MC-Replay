package mc.replay.replay.session.listener;

import mc.replay.api.MCReplay;
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
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;

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

    // TODO check if other players still receive the movement packets of the replay players
    // TODO check if chunks are still loaded for the replay players
    // TODO check if there are any other issues with this
    private void interceptMovementPackets() {
        this.instance.getPacketLib().packetListener().interceptServerbound(ServerboundPacketIdentifier.PLAYER_POSITION_AND_ROTATION, (player, serverboundPacket) -> {
            ReplayPlayerImpl replayPlayer = this.replayHandler.getReplayPlayer(player);
            if (replayPlayer == null) return false; // This is not a replay player, so no need to intercept the packet

            ServerboundPlayerPositionAndRotationPacket packet = (ServerboundPlayerPositionAndRotationPacket) serverboundPacket;

            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
            entityPlayer.setPositionRaw(
                    packet.x(),
                    packet.y(),
                    packet.z()
            );

            entityPlayer.yaw = packet.yaw();
            entityPlayer.pitch = packet.pitch();

            return true;
        });

        this.instance.getPacketLib().packetListener().interceptServerbound(ServerboundPacketIdentifier.PLAYER_POSITION, (player, serverboundPacket) -> {
            ReplayPlayerImpl replayPlayer = this.replayHandler.getReplayPlayer(player);
            if (replayPlayer == null) return false; // This is not a replay player, so no need to intercept the packet

            ServerboundPlayerPositionPacket packet = (ServerboundPlayerPositionPacket) serverboundPacket;

            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
            entityPlayer.setPositionRaw(
                    packet.x(),
                    packet.y(),
                    packet.z()
            );

            return true;
        });

        this.instance.getPacketLib().packetListener().interceptServerbound(ServerboundPacketIdentifier.PLAYER_ROTATION, (player, serverboundPacket) -> {
            ReplayPlayerImpl replayPlayer = this.replayHandler.getReplayPlayer(player);
            if (replayPlayer == null) return false; // This is not a replay player, so no need to intercept the packet

            ServerboundPlayerRotationPacket packet = (ServerboundPlayerRotationPacket) serverboundPacket;

            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
            entityPlayer.yaw = packet.yaw();
            entityPlayer.pitch = packet.pitch();

            return true;
        });
    }
}