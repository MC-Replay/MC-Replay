package mc.replay.recording.dispatcher.dispatchers;

import mc.replay.api.recordables.Recordable;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class DispatcherTick extends Dispatcher<Integer> {

    public DispatcherTick(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public final List<Recordable> getRecordables(RecordingSession session, Integer currentTick) {
        this.onTickGlobal(currentTick);

        List<Recordable> recordables = new ArrayList<>();

        for (Entity entity : session.getWorld().getEntities()) {
            if (!entity.getWorld().isChunkLoaded(entity.getLocation().getChunk())) continue;

            List<Recordable> entityRecordables = this.getRecordables(session, currentTick, entity);
            if (entityRecordables == null || entityRecordables.isEmpty()) continue;

            recordables.addAll(entityRecordables);
        }

        return recordables;
    }

    public void onTickGlobal(Integer currentTick) {
    }

    public abstract List<Recordable> getRecordables(RecordingSession session, int currentTick, Entity entity);
}