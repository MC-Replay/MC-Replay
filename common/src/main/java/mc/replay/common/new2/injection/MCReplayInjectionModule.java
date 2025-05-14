package mc.replay.common.new2.injection;

import mc.replay.common.new2.injection.binder.ListenerBinding;
import nl.tritewolf.tritejection.module.TriteJectionModule;
import nl.tritewolf.tritejection.multibinder.TriteJectionMultiBinder;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public abstract class MCReplayInjectionModule extends TriteJectionModule {

    protected final JavaPlugin plugin;

    protected MCReplayInjectionModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public final List<TriteJectionMultiBinder> registerMultiBindings() {
        return List.of(
                new ListenerBinding(this.plugin)
        );
    }

    @SafeVarargs
    protected final void bindListeners(@NotNull Class<? extends Listener> @NotNull ... listenerClasses) {
        Arrays.stream(listenerClasses).forEach((clazz) -> super.bind(clazz).toMultiBinder(Listener.class).asEagerSingleton());
    }
}