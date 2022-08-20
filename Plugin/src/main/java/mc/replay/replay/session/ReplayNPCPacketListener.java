package mc.replay.replay.session;//package mc.replay.replay.session;
//
//import io.netty.channel.ChannelHandlerContext;
//import mc.replay.MCReplay;
//import mc.replay.common.replay.ReplayEntity;
//import mc.replay.common.replay.ReplayNPC;
//import mc.replay.utils.EntityPacketUtils;
//import mc.replay.utils.reflection.JavaReflections;
//import org.bukkit.Bukkit;
//import org.bukkit.entity.Player;
//
//public class ReplayNPCPacketListener implements PlayerPipeline {
//
//    @Override
//    public String getName() {
//        return "replay_npc";
//    }
//
//    @Override
//    public void readInbound(Player player, ChannelHandlerContext ctx, Object packetObject) {
//        try {
//            if (PACKET_PLAY_IN_USE_ENTITY.isAssignableFrom(packetObject.getClass())) {
//                int entityId = (int) JavaReflections.getField(packetObject.getClass(), int.class, "a").get(packetObject);
//                if (entityId < 0 || player.getEntityId() == entityId) return;
//
//                ReplaySession replaySession = MCReplay.getInstance().getSessions().get(player);
//                if (replaySession == null) return;
//
//                for (ReplayEntity<?> replayEntity : replaySession.getEntities()) {
//                    if (!(replayEntity instanceof ReplayNPC replayNPC)) continue;
//
//                    Object entityPlayer = replayNPC.getViewers().get(player);
//                    if (entityPlayer == null) continue;
//
//                    if (EntityPacketUtils.getEntityId(entityPlayer) == entityId) {
//                        Bukkit.getScheduler().runTask(MCReplay.getInstance().getJavaPlugin(), () -> {
//                            ReplayPlayerInfoMenu.INVENTORY(replayNPC).open(player);
//                        });
//
//                        return;
//                    }
//                }
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
//}