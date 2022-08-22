package mc.replay.replay;

import lombok.Getter;
import mc.replay.api.recording.Recording;
import mc.replay.api.replay.ReplaySession;
import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.replay.session.ReplayPlayerImpl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public final class ReplaySessionImpl implements ReplaySession {

    private final UUID sessionUuid;
    private final ReplayPlayer navigator;
    private final Collection<ReplayPlayer> watchers;
    private final Recording recording;

    private boolean invalid = false;

    ReplaySessionImpl(Player navigator, Collection<Player> watchers, Recording recording) {
        this.sessionUuid = UUID.randomUUID();
        this.recording = recording;
        this.watchers = new HashSet<>();

        this.navigator = new ReplayPlayerImpl(navigator, this);
        for (Player watcher : watchers) {
            this.watchers.add(new ReplayPlayerImpl(watcher, this));
        }
    }

    @Override
    public void stop() {
        this.invalid = true;
    }

    @Override
    public @NotNull Collection<ReplayPlayer> getAllPlayers() {
        Set<ReplayPlayer> allPlayers = new HashSet<>(this.watchers);
        allPlayers.add(this.navigator);
        return allPlayers;
    }
}