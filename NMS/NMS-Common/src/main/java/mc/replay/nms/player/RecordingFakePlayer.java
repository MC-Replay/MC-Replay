package mc.replay.nms.player;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface RecordingFakePlayer {

    @NotNull Player target();

    void spawn();

    void setRecording(boolean recording);
}