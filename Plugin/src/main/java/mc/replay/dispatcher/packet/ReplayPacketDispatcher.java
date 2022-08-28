package mc.replay.dispatcher.packet;

import lombok.Getter;
import mc.replay.MCReplayPlugin;
import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacketIn;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.dispatcher.ReplayDispatchManager;
import mc.replay.dispatcher.ReplayDispatcher;
import mc.replay.recording.RecordingSessionImpl;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Getter
public final class ReplayPacketDispatcher extends ReplayDispatcher {

    private final Map<String, DispatcherPacketIn<Object>> packetInConverters = new HashMap<>();
    private final Map<String, DispatcherPacketOut<Object>> packetOutConverters = new HashMap<>();

    public ReplayPacketDispatcher(MCReplayPlugin plugin, ReplayDispatchManager dispatchManager) {
        super(plugin);

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPipelineListener(this), plugin);

        dispatchManager.getNmsCore().setPacketOutDispatcher((packet) -> {
            for (Map.Entry<String, DispatcherPacketOut<Object>> entry : this.packetOutConverters.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(packet.getClass().getSimpleName())) {
                    List<Recordable<? extends Function<?, ?>>> recordables = entry.getValue().getRecordables(packet);
                    if (recordables == null) continue;

                    for (RecordingSession recordingSession : MCReplayPlugin.getInstance().getRecordingHandler().getRecordingSessions().values()) {
                        for (Recordable<? extends Function<?, ?>> recordable : recordables) {
                            if (recordable.shouldRecord().apply(recordingSession)) {
                                ((RecordingSessionImpl) recordingSession).addRecordable(recordable);
                            }
                        }
                    }
                }
            }
        });
    }

    public void registerPacketInConverter(DispatcherPacketIn<Object> converter) {
        this.packetInConverters.put(converter.getInputClass().getSimpleName().toLowerCase(), converter);
    }

    public void registerPacketOutConverter(DispatcherPacketOut<Object> converter) {
        this.packetOutConverters.put(converter.getInputClass().getSimpleName().toLowerCase(), converter);
    }

    public int getDispatcherCount() {
        return this.packetInConverters.size() + this.packetOutConverters.size();
    }
}