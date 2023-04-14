package mc.replay.api.utils.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReplayConfigurationType {

    CONFIG("config.yml"),
    MESSAGES("messages.yml");

    private final String fileName;

}
