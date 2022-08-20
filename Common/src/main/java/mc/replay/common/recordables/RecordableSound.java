package mc.replay.common.recordables;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface RecordableSound extends Recordable {

    NamespacedKey soundKey();

    void play(Player viewer);

    @Override
    default boolean match(@NotNull Object object) {
        return object instanceof String string && this.soundKey().getKey().equalsIgnoreCase(string);
    }
}