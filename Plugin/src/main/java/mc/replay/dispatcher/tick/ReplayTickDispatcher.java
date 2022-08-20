package mc.replay.dispatcher.tick;

import mc.replay.MCReplayPlugin;
import mc.replay.dispatcher.ReplayDispatcher;
import mc.replay.nms.v1_16_5.dispatcher.tick.EntityEquipmentTickHandler;
import mc.replay.nms.v1_16_5.dispatcher.tick.EntityLocationTickHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashSet;

public final class ReplayTickDispatcher extends ReplayDispatcher {

    private BukkitTask task;
    private final Collection<ReplayTickHandler> tickHandlers = new HashSet<>();

    public ReplayTickDispatcher(JavaPlugin javaPlugin) {
        super(javaPlugin);

        this.registerTickHandler(new EntityEquipmentTickHandler());
        this.registerTickHandler(new EntityLocationTickHandler());
    }

    public void registerTickHandler(ReplayTickHandler tickHandler) {
        this.tickHandlers.add(tickHandler);
    }

    @Override
    public void start() {
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(MCReplayPlugin.getInstance(), () -> {
            long start = System.currentTimeMillis();

            for (ReplayTickHandler tickHandler : this.tickHandlers) {
                if (System.currentTimeMillis() - start >= 50) {
                    // Don't handle tick handlers when using more than 50 milliseconds
                    break;
                }

                tickHandler.handle(this.getCurrentTick());
            }
        }, 0, 1);
    }

    @Override
    public void stop() {
        this.task.cancel();
    }
}