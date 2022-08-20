package mc.replay.common.dispatcher.test;

import mc.replay.api.recordable.Recordable;
import mc.replay.common.dispatcher.DispatcherTick;
import org.bukkit.event.entity.EntitySpawnEvent;

public class DisTickTest implements DispatcherTick {

    @Override
    public Recordable getRecordable(Integer currentTick) {
        return null;
    }
}
