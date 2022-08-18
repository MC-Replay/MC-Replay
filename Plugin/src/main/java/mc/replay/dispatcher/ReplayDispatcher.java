package mc.replay.dispatcher;

import mc.replay.utils.reflection.nms.MinecraftNMS;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ReplayDispatcher {

    protected final JavaPlugin javaPlugin;

    public ReplayDispatcher(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public abstract void start();

    public abstract void stop();

    public int getCurrentTick() {
        return MinecraftNMS.getCurrentServerTick();
    }
}