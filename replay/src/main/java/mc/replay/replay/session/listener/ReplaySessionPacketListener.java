package mc.replay.replay.session.listener;

import mc.replay.api.MCReplay;
import mc.replay.packetlib.data.entity.InteractEntityType;
import mc.replay.packetlib.data.entity.PlayerHand;
import mc.replay.packetlib.network.packet.serverbound.ServerboundPacketIdentifier;
import mc.replay.packetlib.network.packet.serverbound.play.ServerboundInteractEntityPacket;
import mc.replay.replay.ReplayHandler;
import mc.replay.replay.session.ReplayPlayerImpl;
import mc.replay.replay.session.entity.AbstractReplayEntity;
import mc.replay.replay.session.menu.ReplayPlayerInfoMenu;
import mc.replay.wrapper.entity.PlayerWrapper;

public final class ReplaySessionPacketListener {

    public ReplaySessionPacketListener(ReplayHandler replayHandler, MCReplay instance) {
        instance.getPacketLib().packetListener().listenServerbound(ServerboundPacketIdentifier.INTERACT_ENTITY, (player, serverboundPacket) -> {
            ServerboundInteractEntityPacket packet = (ServerboundInteractEntityPacket) serverboundPacket;

            if (packet.type() instanceof InteractEntityType.Interact interact) {
                if (interact.hand() == PlayerHand.MAIN_HAND) {
                    ReplayPlayerImpl replayPlayer = replayHandler.getReplayPlayer(player);
                    if (replayPlayer == null) return;

                    AbstractReplayEntity<?> replayEntity = replayPlayer.replaySession().getReplayEntityByReplayId(packet.targetId());
                    if (replayEntity != null && replayEntity.getEntity() instanceof PlayerWrapper entity) {
                        replayHandler.getInstance().getMenuHandler().openMenu(new ReplayPlayerInfoMenu(entity), player);
                    }
                }
            }
        });
    }
}