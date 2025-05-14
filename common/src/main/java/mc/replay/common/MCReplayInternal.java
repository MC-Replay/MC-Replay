package mc.replay.common;

import mc.replay.api.MCReplay;
import nl.odalitadevelopments.menus.OdalitaMenus;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public interface MCReplayInternal extends MCReplay {

    @NotNull OdalitaMenus getMenuHandler();

    @NotNull Logger getLogger();
}