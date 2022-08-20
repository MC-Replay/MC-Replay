package mc.replay.dispatcher;

import lombok.Getter;
import mc.replay.MCReplayPlugin;
import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.dispatcher.DispatcherPacketIn;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.dispatcher.DispatcherTick;
import mc.replay.common.nms.ReplayNMSLoader;
import mc.replay.common.utils.reflection.nms.MinecraftVersionNMS;
import mc.replay.dispatcher.event.ReplayEventDispatcher;
import mc.replay.dispatcher.packet.ReplayPacketDispatcher;
import mc.replay.dispatcher.tick.ReplayTickDispatcher;

@Getter
public class ReplayDispatchManager {

    private final MCReplayPlugin plugin;

    private final ReplayEventDispatcher eventDispatcher;
    private final ReplayPacketDispatcher packetDispatcher;
    private final ReplayTickDispatcher tickDispatcher;

    public ReplayDispatchManager(MCReplayPlugin plugin) {
        this.plugin = plugin;

        this.eventDispatcher = new ReplayEventDispatcher(plugin);
        this.packetDispatcher = new ReplayPacketDispatcher(plugin);
        this.tickDispatcher = new ReplayTickDispatcher(plugin);

        try {
            String nmsVersion = MinecraftVersionNMS.getServerProtocolVersion();

            this.loadDispatchers(nmsVersion);
            plugin.getLogger().info("Successfully registered " + this.eventDispatcher.getDispatcherCount() + " event, " + this.packetDispatcher.getDispatcherCount() + " packet and " + this.tickDispatcher.getDispatcherCount() + " tick dispatchers");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void start() {
        this.eventDispatcher.start();
        this.packetDispatcher.start();
        this.tickDispatcher.start();
    }

    public void stop() {
        this.eventDispatcher.stop();
        this.packetDispatcher.stop();
        this.tickDispatcher.stop();
    }

    private void loadDispatchers(String version) throws Exception {
        for (Class<?> c : ReplayNMSLoader.getNMSClasses(this.plugin, "mc.replay.nms")) {
            if (DispatcherEvent.class.isAssignableFrom(c)) {
                this.eventDispatcher.registerListener((DispatcherEvent<?>) c.getDeclaredConstructor().newInstance());
                continue;
            }

            if (DispatcherPacketIn.class.isAssignableFrom(c)) {
                this.packetDispatcher.registerPacketInConverter((DispatcherPacketIn<?>) c.getDeclaredConstructor().newInstance());
                continue;
            }

            if (DispatcherPacketOut.class.isAssignableFrom(c)) {
                this.packetDispatcher.registerPacketOutConverter((DispatcherPacketOut<?>) c.getDeclaredConstructor().newInstance());
                continue;
            }

            if (DispatcherTick.class.isAssignableFrom(c)) {
                this.tickDispatcher.registerTickHandler((DispatcherTick) c.getDeclaredConstructor().newInstance());
            }
        }
    }
}
