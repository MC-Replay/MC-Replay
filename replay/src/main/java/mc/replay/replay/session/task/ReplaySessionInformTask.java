package mc.replay.replay.session.task;

import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.api.utils.config.IReplayConfigProcessor;
import mc.replay.api.utils.config.templates.ReplayMessages;
import mc.replay.common.utils.text.TextFormatter;
import mc.replay.replay.ReplaySession;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.entity.Player;

public record ReplaySessionInformTask(ReplaySession replaySession) implements Runnable {

    @Override
    public void run() {
        long currentTime = this.replaySession.getPlayTask().getCurrentTime();
        long startTime = this.replaySession.getPlayTask().getStartTime();
        long endTime = this.replaySession.getPlayTask().getEndTime();

        IReplayConfigProcessor<ReplayMessages> messagesProcessor = this.replaySession.getInstance().getMessagesProcessor();

        String timeFormat = messagesProcessor.getString(ReplayMessages.REPLAY_ACTIONBAR_TIME_FORMAT);

        String status = messagesProcessor.getString(this.replaySession.isPaused() ? ReplayMessages.REPLAY_ACTIONBAR_STATUS_PAUSED : ReplayMessages.REPLAY_ACTIONBAR_STATUS_PLAYING);
        String time = DurationFormatUtils.formatDuration(Math.max(0, currentTime - startTime), timeFormat);
        String duration = DurationFormatUtils.formatDuration(Math.min(endTime, endTime - startTime), timeFormat);
        String speed = Double.toString(this.replaySession.getSpeed());

        String bar = TextFormatter.of(messagesProcessor.getString(ReplayMessages.REPLAY_ACTIONBAR_DISPLAY))
                .replace("status", status)
                .replace("time", time)
                .replace("duration", duration)
                .replace("speed", speed)
                .getSingleLine();

        for (IReplayPlayer replayPlayer : this.replaySession.getAllPlayers()) {
            Player player = replayPlayer.player();
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(bar));
        }
    }
}