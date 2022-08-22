package mc.replay.api.recording.contestant;

import mc.replay.api.recording.contestant.def.EverythingRecordingContestant;
import mc.replay.api.recording.contestant.def.MultiPlayerRecordingContestant;
import mc.replay.api.recording.contestant.def.OnePlayerRecordingContestant;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface RecordingContestant {

    static @NotNull RecordingContestant everything() {
        return new EverythingRecordingContestant();
    }

    static @NotNull RecordingContestant player(@NotNull Player player) {
        return new OnePlayerRecordingContestant(player);
    }

    static @NotNull RecordingContestant players(@NotNull Collection<@NotNull Player> players) {
        return new MultiPlayerRecordingContestant(players);
    }

    @NotNull Collection<@NotNull Player> players();
}