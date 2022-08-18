package mc.replay.api;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCReplayAPI {

    @Getter
    private static MCReplay mcReplay;
    @Getter
    private static JavaPlugin javaPlugin;
}