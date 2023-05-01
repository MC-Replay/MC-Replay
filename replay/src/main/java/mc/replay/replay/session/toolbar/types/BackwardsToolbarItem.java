package mc.replay.replay.session.toolbar.types;

import mc.replay.api.replay.time.SkipTimeType;
import mc.replay.common.utils.item.skull.SkullBuilder;
import mc.replay.replay.session.toolbar.ToolbarItem;

import java.util.concurrent.TimeUnit;

public final class BackwardsToolbarItem extends ToolbarItem {

    public BackwardsToolbarItem(int inventorySlot) {
        super(
                "backwards",
                inventorySlot,
                SkullBuilder.getSkullByURLBuilder(ToolbarItemTextures.BACKWARDS)
                        .displayName("&a10s backwards")
                        .lore(
                                "&7Click here to go 10 seconds",
                                "&7backwards."
                        )
                        .build()
        );

        this.onClick = (player) -> {
            player.replaySession().getInstance().getReplayHandler().getSkipTimeHandler().skipTime(player.replaySession(), 10, TimeUnit.SECONDS, SkipTimeType.BACKWARDS);
            player.replaySession().updateInformationBar();
        };
    }
}