package mc.replay.dispatcher.tick;

import mc.replay.MCReplayPlugin;
import mc.replay.common.dispatcher.DispatcherTick;
import mc.replay.common.recordables.Recordable;
import mc.replay.dispatcher.ReplayDispatcher;
import mc.replay.nms.v1_16_5.dispatcher.tick.EntityEquipmentTickHandler;
import mc.replay.nms.v1_16_5.dispatcher.tick.EntityLocationTickHandler;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public final class ReplayTickDispatcher extends ReplayDispatcher {

    private BukkitTask task;
    private final Collection<DispatcherTick> tickHandlers = new HashSet<>();

    public ReplayTickDispatcher(MCReplayPlugin plugin) {
        super(plugin);

        this.registerTickHandler(new EntityEquipmentTickHandler());
        this.registerTickHandler(new EntityLocationTickHandler());
    }

    public void registerTickHandler(DispatcherTick tickHandler) {
        this.tickHandlers.add(tickHandler);
    }

    @Override
    public void start() {
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(MCReplayPlugin.getInstance(), () -> {
            long start = System.currentTimeMillis();

            for (DispatcherTick tickHandler : this.tickHandlers) {
                if (System.currentTimeMillis() - start >= 50) {
                    // Don't handle tick handlers when using more than 50 milliseconds
                    break;
                }

                List<Recordable> recordables = tickHandler.getRecordable(this.getCurrentTick());
                if (recordables == null || recordables.isEmpty()) continue;

                for (Recordable recordable : recordables) {
                    MCReplayPlugin.getInstance().getReplayStorage().addRecordable(recordable);
                }
            }
        }, 0, 1);
    }

    @Override
    public void stop() {
        this.task.cancel();
    }
}