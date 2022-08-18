package mc.replay.dispatcher.event;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

public interface ReplayEventListener<T extends Event> {

    Class<T> eventClass();

    void listen(T event);

    default EventPriority getPriority() {
        return EventPriority.NORMAL;
    }

    default boolean ignoreCancelled() {
        return true;
    }
}