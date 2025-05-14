package mc.replay.replay.new2;

import mc.replay.api.replay.IReplayController;
import mc.replay.api.replay.session.toolbar.IToolbarController;
import mc.replay.common.new2.injection.MCReplayInjectionModule;
import mc.replay.replay.new2.toolbar.ToolbarController;
import nl.tritewolf.tritejection.annotations.TriteJect;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReplayModule extends MCReplayInjectionModule {

    @TriteJect
    ReplayModule(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void bindings() {
        bind(IReplayController.class).to(ReplayController.class).asEagerSingleton();
        bind(IToolbarController.class).to(ToolbarController.class).asEagerSingleton();
    }
}