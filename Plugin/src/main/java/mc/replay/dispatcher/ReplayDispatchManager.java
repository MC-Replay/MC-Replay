package mc.replay.dispatcher;

import com.google.common.reflect.ClassPath;
import lombok.Getter;
import mc.replay.MCReplayPlugin;
import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.dispatcher.DispatcherPacketIn;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.common.dispatcher.DispatcherTick;
import mc.replay.dispatcher.event.ReplayEventDispatcher;
import mc.replay.dispatcher.packet.ReplayPacketDispatcher;
import mc.replay.dispatcher.tick.ReplayTickDispatcher;
import mc.replay.nms.NMSCore;
import mc.replay.nms.v1_16_R3.NMSCoreImpl;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import mc.replay.packetlib.network.packet.ServerboundPacket;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class ReplayDispatchManager {

    private final MCReplayPlugin plugin;

    private final ReplayEventDispatcher eventDispatcher;
    private final ReplayPacketDispatcher packetDispatcher;
    private final ReplayTickDispatcher tickDispatcher;

    private final NMSCore nmsCore;

    public ReplayDispatchManager(MCReplayPlugin plugin) {
        this.plugin = plugin;

        this.nmsCore = new NMSCoreImpl();

        this.eventDispatcher = new ReplayEventDispatcher(plugin);
        this.packetDispatcher = new ReplayPacketDispatcher(plugin);
        this.tickDispatcher = new ReplayTickDispatcher(plugin);

        try {
            this.loadDispatchers();
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
    private void loadDispatchers() throws Exception {
        for (Class<?> clazz : this.getClasses(this.plugin, "mc.replay.common.dispatcher")) {
            if (clazz.isInterface()) continue;

            if (DispatcherEvent.class.isAssignableFrom(clazz)) {
                this.eventDispatcher.registerListener((DispatcherEvent<?>) clazz.getDeclaredConstructor().newInstance());
                continue;
            }

            if (DispatcherPacketIn.class.isAssignableFrom(clazz)) {
                this.packetDispatcher.registerPacketInConverter((DispatcherPacketIn<ServerboundPacket>) clazz.getDeclaredConstructor().newInstance());
                continue;
            }

            if (DispatcherPacketOut.class.isAssignableFrom(clazz)) {
                this.packetDispatcher.registerPacketOutConverter((DispatcherPacketOut<ClientboundPacket>) clazz.getDeclaredConstructor().newInstance());
                continue;
            }

            if (DispatcherTick.class.isAssignableFrom(clazz)) {
                this.tickDispatcher.registerTickHandler((DispatcherTick) clazz.getDeclaredConstructor().newInstance());
            }
        }
    }

    @SuppressWarnings("all")
    private List<Class<?>> getClasses(JavaPlugin plugin, String packagePath) throws Exception {
        List<Class<?>> classes = new ArrayList<>();

        for (ClassPath.ClassInfo classInfo : ClassPath.from(plugin.getClass().getClassLoader()).getTopLevelClassesRecursive(packagePath)) {
            classes.add(Class.forName(classInfo.getName()));
        }

        return classes;
    }
}
