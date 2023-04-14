package mc.replay.api.utils.config.templates;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReplayMessages implements IReplayConfigStructure {

    REPLAY_STARTED("messages.replay.started", "%prefix% &aReplay session started."),
    REPLAY_STOPPED("messages.replay.stopped", "%prefix% &aReplay session stopped."),

    REPLAY_ACTIONBAR_STATUS_PAUSED("messages.replay.action-bar.status.paused", "&cPaused"),
    REPLAY_ACTIONBAR_STATUS_PLAYING("messages.replay.action-bar.status.playing", "&aPlaying"),
    REPLAY_ACTIONBAR_TIME_FORMAT("messages.replay.action-bar.time-format", "mm:ss"),
    REPLAY_ACTIONBAR_DISPLAY("messages.replay.action-bar.display", "%status%     &e%time% / %duration%     &6%speed%x");

    private final String path;
    private final String defaultValue;
}
