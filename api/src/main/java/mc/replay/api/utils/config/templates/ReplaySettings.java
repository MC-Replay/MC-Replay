package mc.replay.api.utils.config.templates;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReplaySettings implements IReplayConfigStructure {

    ENABLE_DEBUG("settings.debug-enabled", false),

    REPLAY_PLAYER_MOVEMENT_BLOCK_CHANGE("settings.replay.player.movement.move-event-block-change", true);

    private final String path;
    private final Object defaultValue;
}