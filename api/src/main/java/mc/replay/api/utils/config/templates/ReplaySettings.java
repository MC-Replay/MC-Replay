package mc.replay.api.utils.config.templates;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReplaySettings implements IReplayConfigStructure {

    ENABLE_DEBUG("settings.debug-enabled", false);

    private final String path;
    private final Object defaultValue;

}
