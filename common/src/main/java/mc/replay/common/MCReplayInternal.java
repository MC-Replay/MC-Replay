package mc.replay.common;

import mc.replay.api.MCReplay;
import nl.odalitadevelopments.menus.OdalitaMenus;
import org.jetbrains.annotations.NotNull;

public interface MCReplayInternal extends MCReplay {

    @NotNull OdalitaMenus getMenuHandler();
}