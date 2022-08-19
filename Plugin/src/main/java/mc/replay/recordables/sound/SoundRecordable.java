package mc.replay.recordables.sound;

import mc.replay.api.recordable.Recordable;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface SoundRecordable extends Recordable {

    NamespacedKey soundKey();

    void play(Player viewer);

    @Override
    default boolean match(@NotNull Object object) {
        return object instanceof String string && this.soundKey().getKey().equalsIgnoreCase(string);
    }
}