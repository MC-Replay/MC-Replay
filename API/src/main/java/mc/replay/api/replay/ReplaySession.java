package mc.replay.api.replay;

import mc.replay.api.recording.Recording;
import mc.replay.api.replay.session.ReplayPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;

public interface ReplaySession {

    @NotNull UUID getSessionUuid();

    @NotNull ReplayPlayer getNavigator();

    @NotNull Collection<ReplayPlayer> getWatchers();

    @NotNull Recording getRecording();

    void stop();

    @NotNull Collection<ReplayPlayer> getAllPlayers();

    boolean isInvalid();

    boolean isPaused();

    void setPaused(boolean paused);

    double getSpeed();

    void decreaseSpeed();

    void increaseSpeed();
}