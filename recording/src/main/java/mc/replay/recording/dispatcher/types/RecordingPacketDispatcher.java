package mc.replay.recording.dispatcher.types;

import lombok.Getter;
import mc.replay.api.recordables.Recordable;
import mc.replay.api.recording.IRecordingSession;
import mc.replay.common.MCReplayInternal;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.serverbound.ServerboundPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketIn;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.RecordingDispatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public final class RecordingPacketDispatcher extends RecordingDispatcher {

    private final Map<Class<ServerboundPacket>, DispatcherPacketIn<ServerboundPacket>> packetInDispatchers = new HashMap<>();
    private final Map<Class<ClientboundPacket>, DispatcherPacketOut<ClientboundPacket>> packetOutDispatchers = new HashMap<>();

    public RecordingPacketDispatcher(MCReplayInternal plugin) {
        super(plugin);

        plugin.getPacketLib().packetListener().listenClientbound((player, packet) -> {
            for (Map.Entry<Class<ClientboundPacket>, DispatcherPacketOut<ClientboundPacket>> entry : this.packetOutDispatchers.entrySet()) {
                if (!packet.getClass().equals(entry.getKey())) continue;

                List<Recordable> recordables = entry.getValue().getRecordables(packet);
                if (recordables == null) continue;

                for (IRecordingSession recordingSession : plugin.getRecordingHandler().getRecordingSessions().values()) {
                    ((RecordingSession) recordingSession).addRecordables(recordables);
                }
            }
        });

        plugin.getPacketLib().packetListener().listenServerbound((player, packet) -> {
            for (Map.Entry<Class<ServerboundPacket>, DispatcherPacketIn<ServerboundPacket>> entry : this.packetInDispatchers.entrySet()) {
                if (!packet.getClass().equals(entry.getKey())) continue;

                List<Recordable> recordables = entry.getValue().getRecordables(player, packet);
                if (recordables == null) continue;

                for (IRecordingSession recordingSession : plugin.getRecordingHandler().getRecordingSessions().values()) {
                    ((RecordingSession) recordingSession).addRecordables(recordables);
                }
            }
        });
    }

    public void registerPacketInConverter(DispatcherPacketIn<ServerboundPacket> converter) {
        this.packetInDispatchers.put(converter.getInputClass(), converter);
    }

    public void registerPacketOutConverter(DispatcherPacketOut<ClientboundPacket> converter) {
        this.packetOutDispatchers.put(converter.getInputClass(), converter);
    }

    public int getDispatcherCount() {
        return this.packetInDispatchers.size() + this.packetOutDispatchers.size();
    }
}