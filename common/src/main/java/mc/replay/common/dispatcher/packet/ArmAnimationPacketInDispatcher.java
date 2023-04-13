package mc.replay.common.dispatcher.packet;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketIn;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntitySwingHandAnimation;
import mc.replay.packetlib.network.packet.serverbound.play.ServerboundAnimationPacket;
import org.bukkit.entity.Player;

import java.util.List;

public final class ArmAnimationPacketInDispatcher implements DispatcherPacketIn<ServerboundAnimationPacket> {

    @Override
    public List<Recordable> getRecordables(Player player, ServerboundAnimationPacket packet) {
        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(
                new RecEntitySwingHandAnimation(
                        entityId,
                        packet.hand()
                )
        );
    }
}