package mc.replay.replay.session.menu;

import lombok.AllArgsConstructor;
import mc.replay.common.utils.item.skull.SkullBuilder;
import mc.replay.packetlib.data.Pos;
import mc.replay.replay.ReplaySession;
import mc.replay.replay.session.entity.ReplayNPC;
import mc.replay.replay.session.entity.ReplaySessionEntityHandler;
import nl.odalitadevelopments.menus.annotations.Menu;
import nl.odalitadevelopments.menus.contents.MenuContents;
import nl.odalitadevelopments.menus.items.ClickableItem;
import nl.odalitadevelopments.menus.menu.providers.PlayerMenuProvider;
import nl.odalitadevelopments.menus.menu.type.MenuType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Menu(
        title = "Teleport",
        type = MenuType.CHEST_6_ROW
)
public final class ReplayTeleportMenu implements PlayerMenuProvider {

    private final ReplaySession replaySession;

    @Override
    public void onLoad(@NotNull Player player, @NotNull MenuContents contents) {
        ReplaySessionEntityHandler entityCache = this.replaySession.getPlayTask().getEntityCache();

        for (ReplayNPC npc : entityCache.getNPCs()) {
            contents.add(ClickableItem.of(SkullBuilder.getSkullByTextureBuilder(npc.getSkinTexture() == null ? "" : npc.getSkinTexture().value())
                    .displayName("&aTeleport to " + npc.getName())
                    .build(), (event) -> {
                // TODO check if npc is still alive

                Pos position = npc.getEntity().getPosition();
                Location location = new Location(this.replaySession.getReplayWorld(), position.x(), position.y(), position.z(), position.yaw(), position.pitch());

                player.teleport(location);
            }));
        }
    }
}