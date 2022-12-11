package mc.replay.common.dispatcher.packet;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketIn;
import mc.replay.common.recordables.entity.miscellaneous.RecEntitySwingHandAnimation;
import mc.replay.packetlib.network.packet.serverbound.ServerboundAnimationPacket;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Function;

public final class ArmAnimationPacketInConverter implements DispatcherPacketIn<ServerboundAnimationPacket> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(Player player, ServerboundAnimationPacket packet) {
        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(RecEntitySwingHandAnimation.of(
                entityId,
                packet.handId()
        ));
    }
}