package mc.replay.recording.dispatcher.types;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recording.IRecordingSession;
import mc.replay.common.MCReplayInternal;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherEvent;
import mc.replay.recording.dispatcher.RecordingDispatcher;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public final class RecordingEventDispatcher extends RecordingDispatcher implements Listener {

    private final Collection<DispatcherEvent<?>> eventDispatchers = new HashSet<>();

    public RecordingEventDispatcher(MCReplayInternal plugin) {
        super(plugin);
    }

    public <T extends Event> void registerEvent(DispatcherEvent<T> eventListener) {
        this.eventDispatchers.add(eventListener);
    }

    public int getDispatcherCount() {
        return this.eventDispatchers.size();
    }

    @Override
    public void start() {
        for (DispatcherEvent<?> event : this.eventDispatchers) {
            this.registerBukkitEvent(event);
        }
    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(this);
    }

    @SuppressWarnings("rawtypes, unchecked")
    private void registerBukkitEvent(DispatcherEvent event) {
        Bukkit.getServer().getPluginManager().registerEvent(
                event.getInputClass(),
                this,
                event.getPriority(),
                ($, bukkitEvent) -> {
                    if (!this.shouldRecord()) return;

                    List<Recordable> recordables = event.getRecordables(bukkitEvent);
                    if (recordables == null) return;

                    for (IRecordingSession recordingSession : this.plugin.getRecordingHandler().getRecordingSessions().values()) {
                        ((RecordingSession) recordingSession).addRecordables(recordables);
                    }
                },
                this.plugin.getJavaPlugin(),
                event.ignoreCancelled()
        );
    }
}