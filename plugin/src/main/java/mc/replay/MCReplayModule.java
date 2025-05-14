package mc.replay;

import mc.replay.common.new2.injection.MCReplayInjectionModule;
import mc.replay.replay.new2.ReplayModule;
import org.bukkit.plugin.java.JavaPlugin;

final class MCReplayModule extends MCReplayInjectionModule {

    private final MCReplayPlugin plugin;

    MCReplayModule(MCReplayPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void bindings() {
        bind(MCReplayPlugin.class).toInstance(this.plugin).asEagerSingleton();
        bind(JavaPlugin.class).toInstance(this.plugin).asEagerSingleton();

        bind(ReplayModule.class).asSubModule();
    }
}