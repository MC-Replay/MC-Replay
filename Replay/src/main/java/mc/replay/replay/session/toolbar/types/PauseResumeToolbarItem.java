package mc.replay.replay.session.toolbar.types;

import mc.replay.common.utils.item.ItemBuilder;
import mc.replay.replay.session.toolbar.ToolbarItem;
import org.bukkit.Material;

public final class PauseResumeToolbarItem extends ToolbarItem {

    public PauseResumeToolbarItem(int inventorySlot) {
        super(
                "pause_resume",
                inventorySlot,
                (player) -> {
                    boolean paused = player.replaySession().isPaused();

                    return new ItemBuilder((paused) ? Material.WHITE_DYE : Material.LIME_DYE)
                            .displayName((paused) ? "&aResume" : "&aPause")
                            .lore((lore) -> {
                                if (paused) {
                                    lore.add("&7Click here to resume");
                                } else {
                                    lore.add("&7Click here to pause");
                                }

                                lore.add("&7the replay.");
                                return lore;
                            })
                            .build();
                }
        );

        this.onClick = (player) -> {
            player.replaySession().setPaused(!player.replaySession().isPaused());
            this.give(player);
        };
    }
}