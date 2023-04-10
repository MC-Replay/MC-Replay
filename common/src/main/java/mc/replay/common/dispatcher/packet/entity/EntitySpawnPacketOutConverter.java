package mc.replay.common.dispatcher.packet.entity;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.entity.RecEntitySpawn;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntitySpawnPacket;
import mc.replay.wrapper.entity.EntityTypeWrapper;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Function;

public final class EntitySpawnPacketOutConverter implements DispatcherPacketOut<ClientboundEntitySpawnPacket> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(ClientboundEntitySpawnPacket packet) {
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