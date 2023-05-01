package mc.replay.recording.dispatcher.dispatchers.packet.entity;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.packetlib.network.packet.clientbound.play.legacy.ClientboundLivingEntitySpawn754_758Packet;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;
import mc.replay.wrapper.entity.EntityTypeWrapper;
import org.bukkit.util.Vector;

import java.util.List;

public final class EntityLivingSpawn754_758PacketOutDispatcher extends DispatcherPacketOut<ClientboundLivingEntitySpawn754_758Packet> {

    private EntityLivingSpawn754_758PacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundLivingEntitySpawn754_758Packet packet) {
        return List.of(
                new RecEntitySpawn(
                        EntityId.of(packet.entityId()),
                        new EntityTypeWrapper(packet.type()).getBukkitType(),
                        packet.position(),
                        0,
                        new Vector(
                                packet.velocityX(),
                                packet.velocityY(),
                                packet.velocityZ()
                        )
                )
        );
    }
}