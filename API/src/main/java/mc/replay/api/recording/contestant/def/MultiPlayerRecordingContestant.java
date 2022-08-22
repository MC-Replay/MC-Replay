package mc.replay.api.recording.contestant.def;

import mc.replay.api.recording.contestant.RecordingContestant;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public record MultiPlayerRecordingContestant(
        @NotNull Collection<@NotNull Player> players) implements RecordingContestant {
}