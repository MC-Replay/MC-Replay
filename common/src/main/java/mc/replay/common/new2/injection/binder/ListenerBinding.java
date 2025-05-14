package mc.replay.common.new2.injection.binder;

import nl.tritewolf.tritejection.multibinder.TriteJectionMultiBinder;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ListenerBinding implements TriteJectionMultiBinder {

    private final JavaPlugin plugin;

    public ListenerBinding(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Class<?> getMultiBindingClass() {
        return Listener.class;
    }

    public void handleMultiBinding(Object listenerObject) {
        if (listenerObject instanceof Listener listener) {
            Bukkit.getPluginManager().registerEvents(listener, this.plugin);
        } else {
            throw new IllegalArgumentException("listenerObject must be an instance of Listener!");
        }
    }
}
