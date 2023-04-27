package mc.replay.recording.dispatcher.dispatchers.packet.sound;

import mc.replay.api.recordables.Recordable;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.common.recordables.types.sound.RecStopSound;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundStopSoundPacket;

import java.util.List;

public final class StopSoundPacketOutDispatcher implements DispatcherPacketOut<ClientboundStopSoundPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundStopSoundPacket packet) {
        return List.of(
                new RecStopSound(
                        packet.flags(),
                        packet.sourceId(),
                        packet.sound()
                )
        );
    }
}