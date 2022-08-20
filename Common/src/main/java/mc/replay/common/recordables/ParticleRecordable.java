package mc.replay.common.recordables;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface ParticleRecordable extends Recordable {

    Object particleParam();

    void play(Player viewer);

    @Override
    default boolean match(@NotNull Object object) {
        return this.particleParam().equals(object);
    }
}