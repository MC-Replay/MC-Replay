package mc.replay.common.dispatcher.packet.sound;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.recordables.sound.RecStopSound;
import mc.replay.packetlib.network.packet.clientbound.ClientboundStopSoundPacket;

import java.util.List;
import java.util.function.Function;

public final class StopSoundPacketOutConverter implements DispatcherPacketOut<ClientboundStopSoundPacket> {

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(ClientboundStopSoundPacket packet) {
        return List.of(RecStopSound.of(
                packet.flags(),
                packet.sourceId(),
                packet.sound()
        ));
    }
}