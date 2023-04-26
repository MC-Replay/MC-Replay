package mc.replay.common.dispatcher.packet.entity;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntitySpawnPacket;
import mc.replay.wrapper.entity.EntityTypeWrapper;
import org.bukkit.util.Vector;

import java.util.List;

public final class EntitySpawnPacketOutDispatcher implements DispatcherPacketOut<ClientboundEntitySpawnPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundEntitySpawnPacket packet) {
        return List.of(
                new RecEntitySpawn(
                        EntityId.of(packet.entityId()),
                        new EntityTypeWrapper(packet.type()).getBukkitType(),
                        packet.position(),
                        packet.data(),
                        new Vector(
                                packet.velocityX(),
                                packet.velocityY(),
                                packet.velocityZ()
                        )
                )
        );
    }
}