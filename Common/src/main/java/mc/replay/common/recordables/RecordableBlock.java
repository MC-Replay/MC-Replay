package mc.replay.common.recordables;

import mc.replay.api.recording.recordables.Recordable;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public interface RecordableBlock extends Recordable {

    Vector blockPosition();

    void play(Player viewer);

    @Override
    default boolean match(@NotNull Object object) {
        Vector vector = null;

        if (object instanceof Location location) {
            vector = location.toVector();
        } else if (object instanceof Vector objectVector) {
            vector = objectVector;
        }

        return vector != null
                && vector.getBlockX() == this.blockPosition().getBlockX()
                && vector.getBlockY() == this.blockPosition().getBlockY()
                && vector.getBlockZ() == this.blockPosition().getBlockZ();
    }
}