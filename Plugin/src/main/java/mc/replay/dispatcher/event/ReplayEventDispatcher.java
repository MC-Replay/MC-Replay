package mc.replay.dispatcher.event;

import mc.replay.MCReplayPlugin;
import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.dispatcher.ReplayDispatcher;
import mc.replay.recording.RecordingSessionImpl;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

public final class ReplayEventDispatcher extends ReplayDispatcher implements Listener {

    private final Collection<DispatcherEvent<?>> eventListeners = new HashSet<>();

    public ReplayEventDispatcher(MCReplayPlugin plugin) {
        super(plugin);
    }

    public <T extends Event> void registerListener(DispatcherEvent<T> eventListener) {
        this.eventListeners.add(eventListener);
    }

    public int getDispatcherCount() {
        return this.eventListeners.size();
    }

    @Override
    public void start() {
        for (DispatcherEvent<?> eventListener : this.eventListeners) {
            this.registerReplayEventListener(eventListener);
        }
    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(this);
    }

    @SuppressWarnings("rawtypes, unchecked")
    private void registerReplayEventListener(DispatcherEvent eventListener) {
        Bukkit.getServer().getPluginManager().registerEvent(
                eventListener.getInputClass(),
                this,
                eventListener.getPriority(),
                ($, event) -> {
                    if (!this.shouldRecord()) return;

                    List<Recordable<? extends Function<?, ?>>> recordables = eventListener.getRecordables(event);

                    for (RecordingSession recordingSession : this.plugin.getRecordingHandler().getRecordingSessions().values()) {
                        ((RecordingSessionImpl) recordingSession).addRecordables(recordables);
                    }
                },
                this.plugin,
                eventListener.ignoreCancelled()
        );
    }
}