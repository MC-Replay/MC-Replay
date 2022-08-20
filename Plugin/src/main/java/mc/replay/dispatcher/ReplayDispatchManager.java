package mc.replay.dispatcher;

import lombok.Getter;
import mc.replay.MCReplayPlugin;
import mc.replay.dispatcher.event.ReplayEventDispatcher;
import mc.replay.dispatcher.packet.ReplayPacketDispatcher;
import mc.replay.dispatcher.tick.ReplayTickDispatcher;

@Getter
public class ReplayDispatchManager {

    private final MCReplayPlugin plugin;

    private final ReplayEventDispatcher replayEventDispatcher;
    private final ReplayPacketDispatcher replayPacketDispatcher;
    private final ReplayTickDispatcher replayTickDispatcher;

    public ReplayDispatchManager(MCReplayPlugin plugin) {
        this.plugin = plugin;

        this.replayEventDispatcher = new ReplayEventDispatcher(plugin);
        this.replayPacketDispatcher = new ReplayPacketDispatcher(plugin);
        this.replayTickDispatcher = new ReplayTickDispatcher(plugin);
    }

    public void start() {
        this.replayEventDispatcher.start();
        this.replayPacketDispatcher.start();
        this.replayTickDispatcher.start();
    }

    public void stop() {
        this.replayEventDispatcher.stop();
        this.replayPacketDispatcher.stop();
        this.replayTickDispatcher.stop();
    }
}
