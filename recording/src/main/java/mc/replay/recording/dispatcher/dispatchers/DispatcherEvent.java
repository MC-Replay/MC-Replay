package mc.replay.recording.dispatcher.dispatchers;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

public interface DispatcherEvent<T extends Event> extends Dispatcher<T> {

    default EventPriority getPriority() {
        return EventPriority.NORMAL;
    }

    default boolean ignoreCancelled() {
        return true;
    }
}