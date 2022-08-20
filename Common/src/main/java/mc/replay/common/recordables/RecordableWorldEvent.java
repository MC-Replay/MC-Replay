package mc.replay.common.recordables;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface RecordableWorldEvent extends Recordable {

    int effectId();

    void play(Player viewer);

    @Override
    default boolean match(@NotNull Object object) {
        return object instanceof Integer integer && this.effectId() == integer;
    }
}