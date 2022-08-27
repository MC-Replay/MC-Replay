package mc.replay.api.recording.contestant.def;

import mc.replay.api.recording.contestant.RecordingContestant;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

public final class EverythingRecordingContestant implements RecordingContestant {

    @Override
    public @NotNull Collection<@NotNull Player> players() {
        return new HashSet<>(Bukkit.getOnlinePlayers());
    }
}