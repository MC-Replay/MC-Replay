package mc.replay.common.dispatcher.packet.sound;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.types.sound.RecStopSound;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundStopSoundPacket;

import java.util.List;

public final class StopSoundPacketOutConverter implements DispatcherPacketOut<ClientboundStopSoundPacket> {

    @Override
    public List<Recordable> getRecordables(ClientboundStopSoundPacket packet) {
        return List.of(new RecStopSound(
                packet.flags(),
                packet.sourceId(),
                packet.sound()
        ));
    }
}