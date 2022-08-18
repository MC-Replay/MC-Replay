package mc.replay.dispatcher.event;

import mc.replay.dispatcher.ReplayDispatcher;
import mc.replay.dispatcher.event.listeners.entity.ReplayEntityDeathEventListener;
import mc.replay.dispatcher.event.listeners.entity.ReplayEntitySpawnEventListener;
import mc.replay.dispatcher.event.listeners.entity.ReplayEntityToggleGlideEventListener;
import mc.replay.dispatcher.event.listeners.entity.ReplayEntityToggleSwimEventListener;
import mc.replay.dispatcher.event.listeners.player.ReplayPlayerJoinEventListener;
import mc.replay.dispatcher.event.listeners.player.ReplayPlayerQuitEventListener;
import mc.replay.dispatcher.event.listeners.player.ReplayPlayerToggleSneakEventListener;
import mc.replay.dispatcher.event.listeners.player.ReplayPlayerToggleSprintEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashSet;

public final class ReplayEventDispatcher extends ReplayDispatcher implements Listener {

    private final Collection<ReplayEventListener<?>> eventListeners = new HashSet<>();

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

    public <T extends Event> void registerListener(ReplayEventListener<T> eventListener) {
        this.eventListeners.add(eventListener);
    }

    @Override
    public void start() {
        for (ReplayEventListener<?> eventListener : this.eventListeners) {
            this.registerReplayEventListener(eventListener);
        }
    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(this);
    }

    @SuppressWarnings("rawtypes, unchecked")
    private void registerReplayEventListener(ReplayEventListener eventListener) {
        this.javaPlugin.getServer().getPluginManager().registerEvent(
                eventListener.eventClass(),
                this,
                eventListener.getPriority(),
                ($, event) -> eventListener.listen((Event) eventListener.eventClass().cast(event)),
                this.javaPlugin,
                eventListener.ignoreCancelled()
        );
    }
}