package mc.replay.recording.dispatcher.dispatchers.packet.entity;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.mappings.mapped.MappedEntityType;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntitySpawnPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;
import org.bukkit.util.Vector;

import java.util.List;

public final class EntitySpawnPacketOutDispatcher extends DispatcherPacketOut<ClientboundEntitySpawnPacket> {

    private EntitySpawnPacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundEntitySpawnPacket packet) {
        return List.of(
                new RecEntitySpawn(
                        EntityId.of(packet.entityId()),
                        new MappedEntityType(packet.type()).bukkit(),
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