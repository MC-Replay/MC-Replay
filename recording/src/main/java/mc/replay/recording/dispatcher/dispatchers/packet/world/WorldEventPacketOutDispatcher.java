package mc.replay.recording.dispatcher.dispatchers.packet.world;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.recordables.types.world.RecWorldEvent;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundWorldEventPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.util.List;

public final class WorldEventPacketOutDispatcher extends DispatcherPacketOut<ClientboundWorldEventPacket> {

    private WorldEventPacketOutDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, ClientboundWorldEventPacket packet) {
        return List.of(
                new RecWorldEvent(
                        packet.effectId(),
                        packet.position(),
                        packet.data(),
                        packet.disableRelativeVolume()
                )
        );
    }
}