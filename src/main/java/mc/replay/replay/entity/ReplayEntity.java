package mc.replay.replay.entity;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class ReplayEntity<T extends ReplayEntity<T>> {

    protected final int originalEntityId;
    protected final Map<Player, Object> viewers = new HashMap<>();

    public ReplayEntity(int originalEntityId, Collection<Player> viewers) {
        this.originalEntityId = originalEntityId;

        for (Player viewer : viewers) {
            this.viewers.put(viewer, null);
        }
    }

    public abstract void spawn(Player viewer);

    @SuppressWarnings("unchecked")
    public final T spawn() {
        for (Player viewer : this.viewers.keySet()) {
            this.spawn(viewer);
        }

        return (T) this;
    }

    public abstract void destroy(Player viewer);

    @SuppressWarnings("unchecked")
    public final T destroy() {
        for (Player viewer : this.viewers.keySet()) {
            this.destroy(viewer);
        }

        return (T) this;
    }

    public final void addViewers(Collection<Player> viewers) {
        for (Player viewer : viewers) {
            this.addViewer(viewer);
        }
    }

    public final void addViewer(Player player) {
        this.viewers.put(player, null);
    }

    public final void removeViewer(Player player) {
        if (this.viewers.remove(player) != null) {
            this.destroy(player);
        }
    }
}