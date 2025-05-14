package mc.replay.recording.dispatcher.dispatchers.packet;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntitySwingHandAnimation;
import mc.replay.packetlib.network.packet.serverbound.play.ServerboundAnimationPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketIn;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;
import org.bukkit.entity.Player;

import java.util.List;

public final class ArmAnimationPacketInDispatcher extends DispatcherPacketIn<ServerboundAnimationPacket> {

    private ArmAnimationPacketInDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, Player player, ServerboundAnimationPacket packet) {
        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(
                new RecEntitySwingHandAnimation(
                        entityId,
                        packet.hand()
                )
        );
    }
}