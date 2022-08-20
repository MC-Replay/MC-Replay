package mc.replay.dispatcher;

import mc.replay.MCReplayPlugin;
import mc.replay.dispatcher.event.ReplayEventDispatcher;
import mc.replay.dispatcher.packet.ReplayPacketDispatcher;
import mc.replay.dispatcher.tick.ReplayTickDispatcher;

import java.util.ArrayList;
import java.util.Collection;

public class ReplayDispatchManager {

    private final MCReplayPlugin plugin;
    private final Collection<ReplayDispatcher> dispatchers;

    public ReplayDispatchManager(MCReplayPlugin plugin) {
        this.plugin = plugin;
        this.dispatchers = new ArrayList<>();

        this.dispatchers.add(new ReplayEventDispatcher(plugin));
        this.dispatchers.add(new ReplayPacketDispatcher(plugin));
        this.dispatchers.add(new ReplayTickDispatcher(plugin));
    }

    public void start() {
        dispatchers.forEach(ReplayDispatcher::start);
    }

    public void stop() {
        dispatchers.forEach(ReplayDispatcher::stop);
    }
}
