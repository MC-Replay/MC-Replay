package mc.replay.common.dispatcher.test;

import mc.replay.common.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class DisEventTest implements DispatcherEvent<EntitySpawnEvent> {

    @Override
    public Recordable getRecordable(EntitySpawnEvent obj) {
        return null;
    }
}
