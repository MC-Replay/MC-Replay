package mc.replay.dispatcher.packet;

import lombok.Getter;
import mc.replay.MCReplayPlugin;
import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketIn;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.dispatcher.ReplayDispatcher;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.serverbound.ServerboundPacket;
import mc.replay.recording.RecordingSessionImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public final class ReplayPacketDispatcher extends ReplayDispatcher {

    private final Map<Class<ServerboundPacket>, DispatcherPacketIn<ServerboundPacket>> packetInConverters = new HashMap<>();
    private final Map<Class<ClientboundPacket>, DispatcherPacketOut<ClientboundPacket>> packetOutConverters = new HashMap<>();

    public ReplayPacketDispatcher(MCReplayPlugin plugin) {
        super(plugin);

        plugin.getPacketLib().getPacketListener().listenClientbound((packet) -> {
            for (Map.Entry<Class<ClientboundPacket>, DispatcherPacketOut<ClientboundPacket>> entry : this.packetOutConverters.entrySet()) {
                if (!packet.getClass().equals(entry.getKey())) continue;

                List<Recordable> recordables = entry.getValue().getRecordables(packet);
                if (recordables == null) continue;

                for (RecordingSession recordingSession : plugin.getRecordingHandler().getRecordingSessions().values()) {
                    ((RecordingSessionImpl) recordingSession).addRecordables(recordables);
                }
            }
        });

        plugin.getPacketLib().getPacketListener().listenServerbound((player, packet) -> {
            for (Map.Entry<Class<ServerboundPacket>, DispatcherPacketIn<ServerboundPacket>> entry : this.packetInConverters.entrySet()) {
                if (!packet.getClass().equals(entry.getKey())) continue;

                List<Recordable> recordables = entry.getValue().getRecordables(player, packet);
                if (recordables == null) continue;

                for (RecordingSession recordingSession : plugin.getRecordingHandler().getRecordingSessions().values()) {
                    ((RecordingSessionImpl) recordingSession).addRecordables(recordables);
                }
            }
        });
    }

    public void registerPacketInConverter(DispatcherPacketIn<ServerboundPacket> converter) {
        this.packetInConverters.put(converter.getInputClass(), converter);
    }

    public void registerPacketOutConverter(DispatcherPacketOut<ClientboundPacket> converter) {
        this.packetOutConverters.put(converter.getInputClass(), converter);
    }

    public int getDispatcherCount() {
        return this.packetInConverters.size() + this.packetOutConverters.size();
    }
}