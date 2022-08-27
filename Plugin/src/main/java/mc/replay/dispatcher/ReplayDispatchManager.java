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
import mc.replay.nms.NMSCore;
import mc.replay.nms.v1_16_R3.NMSCoreImpl;

@Getter
public final class ReplayDispatchManager {

    private final MCReplayPlugin plugin;

    private final ReplayEventDispatcher eventDispatcher;
    private final ReplayPacketDispatcher packetDispatcher;
    private final ReplayTickDispatcher tickDispatcher;

    private final NMSCore nmsCore; // TODO

    public ReplayDispatchManager(MCReplayPlugin plugin) {
        this.plugin = plugin;

        this.nmsCore = new NMSCoreImpl();

        this.eventDispatcher = new ReplayEventDispatcher(plugin);
        this.packetDispatcher = new ReplayPacketDispatcher(plugin, this);
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

    @SuppressWarnings("unchecked")
    private void loadDispatchers(String version) throws Exception {
        for (Class<?> clazz : ReplayNMSLoader.getNMSClasses(this.plugin, "mc.replay.nms." + version)) {
            if (DispatcherEvent.class.isAssignableFrom(clazz)) {
                this.eventDispatcher.registerListener((DispatcherEvent<?>) clazz.getDeclaredConstructor().newInstance());
                continue;
            }

            if (DispatcherPacketIn.class.isAssignableFrom(clazz)) {
                this.packetDispatcher.registerPacketInConverter((DispatcherPacketIn<Object>) clazz.getDeclaredConstructor().newInstance());
                continue;
            }

            if (DispatcherPacketOut.class.isAssignableFrom(clazz)) {
                this.packetDispatcher.registerPacketOutConverter((DispatcherPacketOut<Object>) clazz.getDeclaredConstructor().newInstance());
                continue;
            }

            if (DispatcherTick.class.isAssignableFrom(clazz)) {
                this.tickDispatcher.registerTickHandler((DispatcherTick) clazz.getDeclaredConstructor().newInstance());
            }
        }
    }
}
