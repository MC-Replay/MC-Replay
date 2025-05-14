package mc.replay.replay.new2.toolbar;

import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.api.replay.session.toolbar.IToolbarController;
import mc.replay.replay.new2.ReplayController;
import nl.tritewolf.tritejection.annotations.TriteJect;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ToolbarController implements IToolbarController {

    private final ToolbarService service;

    @TriteJect
    ToolbarController(JavaPlugin plugin, ReplayController replayController) {
        this.service = new ToolbarService(plugin, replayController);
    }

    @Override
    public void giveItems(@NotNull IReplayPlayer replayPlayer) {
        this.service.giveItems(replayPlayer);
    }
}