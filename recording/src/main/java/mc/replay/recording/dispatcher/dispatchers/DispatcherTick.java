package mc.replay.recording.dispatcher.dispatchers;

import mc.replay.api.recordables.Recordable;
import mc.replay.recording.RecordingSession;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public interface DispatcherTick extends Dispatcher<Integer> {

    @Override
    default List<Recordable> getRecordables(RecordingSession session, Integer currentTick) {
        this.onTickGlobal(currentTick);

        List<Recordable> recordables = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!entity.getWorld().isChunkLoaded(entity.getLocation().getChunk())) continue;

                List<Recordable> entityRecordables = this.getRecordables(session, currentTick, entity);
                if (entityRecordables == null || entityRecordables.isEmpty()) continue;

                recordables.addAll(entityRecordables);
            }
        }

        return recordables;
    }

    default void onTickGlobal(Integer currentTick) {
    }

    List<Recordable> getRecordables(RecordingSession session, int currentTick, Entity entity);
}