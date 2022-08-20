package mc.replay.common.dispatcher.test;

import mc.replay.common.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherTick;

public class DisTickTest implements DispatcherTick {

    @Override
    public Recordable getRecordable(Integer currentTick) {
        return null;
    }
}
