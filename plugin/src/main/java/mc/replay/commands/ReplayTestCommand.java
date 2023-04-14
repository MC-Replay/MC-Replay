package mc.replay.commands;

import mc.replay.MCReplayPlugin;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.recording.Recording;
import mc.replay.api.recording.RecordingSession;
import mc.replay.api.replay.ReplaySession;
import mc.replay.api.utils.config.IReplayConfigProcessor;
import mc.replay.api.utils.config.templates.ReplayMessages;
import mc.replay.classgenerator.ClassGenerator;
import mc.replay.classgenerator.objects.IRecordingFakePlayer;
import mc.replay.common.utils.text.TextFormatter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ReplayTestCommand implements CommandExecutor {

    private RecordingSession session;
    private Recording recording;
    private ReplaySession replaySession;
    private IRecordingFakePlayer fakePlayer;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        IReplayConfigProcessor<ReplayMessages> messagesProcessor = MCReplayPlugin.getInstance().getMessagesProcessor();

        if (!player.hasPermission("mc.replay.test")) {
            TextFormatter.of(messagesProcessor.getString(ReplayMessages.REPLAY_COMMAND_NO_PERMISSION)).send(player);
            return false;
        }

        if (args.length < 1) {
            TextFormatter.of("%prefix% &cPlease use: /replaytest <start/stop> <player>").send(player);
            return false;
        }

        if (args[0].equalsIgnoreCase("start")) {
            if (this.session != null) {
                TextFormatter.of("%prefix% &cA recording session is already running!").send(player);
                return false;
            }

            if (this.fakePlayer == null) {
                TextFormatter.of("%prefix% &cCreate fakeplayer first.").send(player);
                return false;
            }

            this.fakePlayer.setRecording(true);
            this.session = MCReplayAPI.getRecordingHandler().createRecordingSession()
                    .world(player.getWorld())
                    .startRecording();

            TextFormatter.of("%prefix% &aRecording started for everything.").send(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("spawn")) {
            try {
                this.fakePlayer = ClassGenerator.createFakePlayer(MCReplayPlugin.getInstance().getFakePlayerHandler(), player);
                this.fakePlayer.spawn();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            TextFormatter.of("%prefix% &aEntity spawned.").send(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("stop")) {
            if (this.session == null) {
                TextFormatter.of("%prefix% &cNo recording session is running!").send(player);
                return false;
            }

            this.recording = this.session.stopRecording();
            this.fakePlayer.setRecording(false);
            this.fakePlayer.remove();
            TextFormatter.of("%prefix% &aRecording stopped.").send(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("play")) {
            if (this.recording == null) {
                TextFormatter.of("%prefix% &cNo recording found!").send(player);
                return false;
            }

            this.replaySession = MCReplayAPI.getReplayHandler().startReplay(this.recording, player);
            TextFormatter.of("%prefix% &aReplay session started.").send(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("quit")) {
            if (this.replaySession != null) {
                this.replaySession.stop();
                this.session = null;
                this.recording = null;
                TextFormatter.of("%prefix% &aReplay stopped.").send(player);
                return true;
            }

            TextFormatter.of("%prefix% &cNo active replay found.").send(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("load")) {
            if (args.length == 2) {
                this.recording = MCReplayPlugin.getInstance().getRecordingHandler().getFileProcessor().loadRecording(new File(MCReplayPlugin.getInstance().getDataFolder() + "/recordings", args[1] + ".mcrr"));
                TextFormatter.of("%prefix% &aLoaded recording.").send(player);
                return true;
            }

            if (this.recording == null) {
                TextFormatter.of("%prefix% &cNo recording found.").send(player);
                return true;
            }

            this.recording = MCReplayPlugin.getInstance().getRecordingHandler().getFileProcessor().loadRecording(new File(MCReplayPlugin.getInstance().getDataFolder() + "/recordings", this.recording.id() + ".mcrr"));
            TextFormatter.of("%prefix% &aLoaded recording.").send(player);
            return true;
        }

        return true;
    }

}