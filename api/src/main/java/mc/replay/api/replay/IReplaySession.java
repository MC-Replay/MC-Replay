package mc.replay.api.replay;

import mc.replay.api.recording.IRecording;
import mc.replay.api.replay.session.IReplayPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;

public interface IReplaySession {

    @NotNull UUID getSessionUuid();

    @NotNull IReplayPlayer getNavigator();

    @NotNull Collection<IReplayPlayer> getWatchers();

    @NotNull IRecording getRecording();

    void stop();

    @NotNull Collection<IReplayPlayer> getAllPlayers();

    boolean isInvalid();

    boolean isPaused();

    void setPaused(boolean paused);

    double getSpeed();

    boolean increaseSpeed();

    boolean decreaseSpeed();

    void updateInformationBar();
}