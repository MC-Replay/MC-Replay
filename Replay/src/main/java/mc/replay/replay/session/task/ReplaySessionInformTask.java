package mc.replay.replay.session.task;

import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.common.utils.color.Text;
import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import mc.replay.replay.ReplaySessionImpl;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.entity.Player;

public record ReplaySessionInformTask(ReplaySessionImpl replaySession) implements Runnable {

    @Override
    public void run() {
        long currentTime = this.replaySession.getPlayTask().getCurrentTime();
        long startTime = this.replaySession.getPlayTask().getStartTime();
        long endTime = this.replaySession.getPlayTask().getEndTime();

        String status = this.replaySession.isPaused() ? "&cPaused" : "&aPlaying";
        String time = DurationFormatUtils.formatDuration(Math.max(0, currentTime - startTime), "mm:ss");
        String duration = DurationFormatUtils.formatDuration(Math.min(endTime, endTime - startTime), "mm:ss");
        String speed = this.replaySession.getSpeed() + "x";

        for (ReplayPlayer replayPlayer : this.replaySession.getAllPlayers()) {
            Player player = replayPlayer.player();
            MinecraftPlayerNMS.sendActionbar(player, Text.color(status + "     &e" + time + " / " + duration + "     &6" + speed));
        }
    }
}