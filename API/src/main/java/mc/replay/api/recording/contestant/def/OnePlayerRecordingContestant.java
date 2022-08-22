package mc.replay.api.recording.contestant.def;

import mc.replay.api.recording.contestant.RecordingContestant;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

public record OnePlayerRecordingContestant(@NotNull Player player) implements RecordingContestant {

    @Override
    public @NotNull Collection<@NotNull Player> players() {
        return Set.of(this.player);
    }
}