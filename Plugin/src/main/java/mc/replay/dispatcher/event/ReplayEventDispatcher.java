package mc.replay.dispatcher.event;

import mc.replay.MCReplayPlugin;
import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.Recordable;
import mc.replay.dispatcher.ReplayDispatcher;
import mc.replay.nms.v1_16_5.dispatcher.event.entity.ReplayEntityDeathEventListener;
import mc.replay.nms.v1_16_5.dispatcher.event.entity.ReplayEntitySpawnEventListener;
import mc.replay.nms.v1_16_5.dispatcher.event.entity.ReplayEntityToggleGlideEventListener;
import mc.replay.nms.v1_16_5.dispatcher.event.entity.ReplayEntityToggleSwimEventListener;
import mc.replay.nms.v1_16_5.dispatcher.event.player.ReplayPlayerJoinEventListener;
import mc.replay.nms.v1_16_5.dispatcher.event.player.ReplayPlayerQuitEventListener;
import mc.replay.nms.v1_16_5.dispatcher.event.player.ReplayPlayerToggleSneakEventListener;
import mc.replay.nms.v1_16_5.dispatcher.event.player.ReplayPlayerToggleSprintEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public final class ReplayEventDispatcher extends ReplayDispatcher implements Listener {

    private final Collection<DispatcherEvent<?>> eventListeners = new HashSet<>();

    public ReplayEventDispatcher(JavaPlugin javaPlugin) {
        super(javaPlugin);

        this.registerListener(new ReplayEntityDeathEventListener());
        this.registerListener(new ReplayEntitySpawnEventListener());
        this.registerListener(new ReplayEntityToggleGlideEventListener());
        this.registerListener(new ReplayEntityToggleSwimEventListener());

        this.registerListener(new ReplayPlayerJoinEventListener());
        this.registerListener(new ReplayPlayerQuitEventListener());
        this.registerListener(new ReplayPlayerToggleSneakEventListener());
        this.registerListener(new ReplayPlayerToggleSprintEventListener());
    }

    public <T extends Event> void registerListener(DispatcherEvent<T> eventListener) {
        this.eventListeners.add(eventListener);
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
        this.javaPlugin.getServer().getPluginManager().registerEvent(
                eventListener.getInputClass(),
                this,
                eventListener.getPriority(),
                ($, event) -> {
                    List<Recordable> recordables = eventListener.getRecordable(event);
                    if (recordables == null || recordables.isEmpty()) return;

                    for (Recordable recordable : recordables) {
                        MCReplayPlugin.getInstance().getReplayStorage().addRecordable(recordable);
                    }
                },
                this.javaPlugin,
                eventListener.ignoreCancelled()
        );
    }
}