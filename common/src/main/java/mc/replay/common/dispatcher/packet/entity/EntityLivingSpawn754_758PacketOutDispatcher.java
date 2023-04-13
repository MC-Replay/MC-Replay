package mc.replay.common.dispatcher.packet.entity;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.packetlib.network.packet.clientbound.play.version.ClientboundLivingEntitySpawn754_758Packet;
import mc.replay.wrapper.entity.EntityTypeWrapper;
import org.bukkit.util.Vector;

import java.util.List;

public final class EntityLivingSpawn754_758PacketOutDispatcher implements DispatcherPacketOut<ClientboundLivingEntitySpawn754_758Packet> {

    @Override
    public List<Recordable> getRecordables(ClientboundLivingEntitySpawn754_758Packet packet) {
        return List.of(
                new RecEntitySpawn(
                        EntityId.of(packet.entityId()),
                        new EntityTypeWrapper(packet.type()).getBukkitType(),
                        packet.position(),
                        new Vector(
                                packet.velocityX(),
                                packet.velocityY(),
                                packet.velocityZ()
                        )
                )
        );
    }
}