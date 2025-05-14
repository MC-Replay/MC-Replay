package mc.replay.recording.dispatcher;

import com.google.common.reflect.ClassPath;
import lombok.Getter;
import mc.replay.common.MCReplayInternal;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.serverbound.ServerboundPacket;
import mc.replay.recording.dispatcher.dispatchers.DispatcherEvent;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketIn;
import mc.replay.recording.dispatcher.dispatchers.DispatcherPacketOut;
import mc.replay.recording.dispatcher.dispatchers.DispatcherTick;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;
import mc.replay.recording.dispatcher.types.RecordingEventDispatcher;
import mc.replay.recording.dispatcher.types.RecordingPacketDispatcher;
import mc.replay.recording.dispatcher.types.RecordingTickDispatcher;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@Getter
public final class RecordingDispatcherManager {

    private final MCReplayInternal plugin;

    private final DispatcherHelpers dispatcherHelpers;

    private final RecordingEventDispatcher eventDispatcher;
    private final RecordingPacketDispatcher packetDispatcher;
    private final RecordingTickDispatcher tickDispatcher;

    public RecordingDispatcherManager(MCReplayInternal plugin) {
        this.plugin = plugin;

        this.dispatcherHelpers = new DispatcherHelpers();

        this.eventDispatcher = new RecordingEventDispatcher(plugin);
        this.packetDispatcher = new RecordingPacketDispatcher(plugin);
        this.tickDispatcher = new RecordingTickDispatcher(plugin);

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
        for (Class<?> clazz : this.getClasses(this.plugin.getJavaPlugin(), "mc.replay.recording.dispatcher.dispatchers")) {
            if (Modifier.isAbstract(clazz.getModifiers())) continue;

            Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(DispatcherHelpers.class);
            declaredConstructor.setAccessible(true);

            if (DispatcherEvent.class.isAssignableFrom(clazz)) {
                this.eventDispatcher.registerEvent((DispatcherEvent<?>) declaredConstructor.newInstance(this.dispatcherHelpers));
                continue;
            }

            if (DispatcherPacketIn.class.isAssignableFrom(clazz)) {
                this.packetDispatcher.registerPacketInConverter((DispatcherPacketIn<ServerboundPacket>) declaredConstructor.newInstance(this.dispatcherHelpers));
                continue;
            }

            if (DispatcherPacketOut.class.isAssignableFrom(clazz)) {
                this.packetDispatcher.registerPacketOutConverter((DispatcherPacketOut<ClientboundPacket>) declaredConstructor.newInstance(this.dispatcherHelpers));
                continue;
            }

            if (DispatcherTick.class.isAssignableFrom(clazz)) {
                this.tickDispatcher.registerTickHandler((DispatcherTick) declaredConstructor.newInstance(this.dispatcherHelpers));
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
