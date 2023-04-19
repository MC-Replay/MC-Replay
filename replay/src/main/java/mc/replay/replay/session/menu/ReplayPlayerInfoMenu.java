package mc.replay.replay.session.menu;

import lombok.AllArgsConstructor;
import mc.replay.wrapper.entity.PlayerWrapper;
import nl.odalitadevelopments.menus.annotations.Menu;
import nl.odalitadevelopments.menus.contents.MenuContents;
import nl.odalitadevelopments.menus.menu.providers.PlayerMenuProvider;
import nl.odalitadevelopments.menus.menu.type.MenuType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Menu(
        title = "Player Info",
        type = MenuType.CHEST_6_ROW
)
public final class ReplayPlayerInfoMenu implements PlayerMenuProvider {

    private final PlayerWrapper entity;

    @Override
    public void onLoad(@NotNull Player player, @NotNull MenuContents contents) {
        contents.setDisplay(0, Material.OBSERVER);
    }
}