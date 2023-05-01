package mc.replay.recording.dispatcher.dispatchers;

import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

public abstract class DispatcherEvent<T extends Event> extends Dispatcher<T> {

    public DispatcherEvent(DispatcherHelpers helpers) {
        super(helpers);
    }

    public EventPriority getPriority() {
        return EventPriority.NORMAL;
    }

    public boolean ignoreCancelled() {
        return true;
    }
}