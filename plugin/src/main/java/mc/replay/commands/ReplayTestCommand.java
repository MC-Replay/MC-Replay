package mc.replay.commands;

import mc.replay.MCReplayPlugin;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.recording.Recording;
import mc.replay.api.recording.RecordingSession;
import mc.replay.api.replay.ReplaySession;
import mc.replay.classgenerator.ClassGenerator;
import mc.replay.classgenerator.objects.IRecordingFakePlayer;
import mc.replay.common.utils.text.Text;
import org.bukkit.ChatColor;
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

        if (!player.hasPermission("mc.replay.test")) {
            player.sendMessage(ChatColor.RED + "No permissions!");
            return false;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Please use: /replaytest <start/stop> <player>");
            return false;
        }

        if (args[0].equalsIgnoreCase("start")) {
            if (this.session != null) {
                player.sendMessage(ChatColor.RED + "A recording session is already running!");
                return false;
            }

            if (this.fakePlayer == null) {
                player.sendMessage(ChatColor.RED + "Create fakeplayer first.");
                return false;
            }

            this.fakePlayer.setRecording(true);
            this.session = MCReplayAPI.getRecordingHandler().createRecordingSession()
                    .world(player.getWorld())
                    .startRecording();

            player.sendMessage(ChatColor.GREEN + "Recording started for everything.");
            return true;
        }

        if (args[0].equalsIgnoreCase("spawn")) {
            try {
                this.fakePlayer = ClassGenerator.createFakePlayer(MCReplayPlugin.getInstance().getFakePlayerHandler(), player);
                this.fakePlayer.spawn();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            player.sendMessage(ChatColor.GREEN + "Entity spawned.");
            return true;
        }

        if (args[0].equalsIgnoreCase("stop")) {
            if (this.session == null) {
                player.sendMessage(ChatColor.RED + "No recording session is running!");
                return false;
            }

            this.recording = this.session.stopRecording();
            this.fakePlayer.setRecording(false);
            this.fakePlayer.remove();
            player.sendMessage(ChatColor.GREEN + "Recording stopped.");
            return true;
        }

        if (args[0].equalsIgnoreCase("play")) {
            if (this.recording == null) {
                player.sendMessage(ChatColor.RED + "No recording found!");
                return false;
            }

            this.replaySession = MCReplayAPI.getReplayHandler().startReplay(this.recording, player);
            player.sendMessage(ChatColor.GREEN + "Replay session started.");
            return true;
        }

        if (args[0].equalsIgnoreCase("quit")) {
            if (this.replaySession != null) {
                this.replaySession.stop();
                this.session = null;
                this.recording = null;
                player.sendMessage(ChatColor.GREEN + "Replay stopped.");
                return true;
            }

            player.sendMessage(Text.color("&cNo active replay found."));
            return true;
        }

        if (args[0].equalsIgnoreCase("load")) {
            if (args.length == 2) {
                this.recording = MCReplayPlugin.getInstance().getRecordingHandler().getFileProcessor().loadRecording(new File(MCReplayPlugin.getInstance().getDataFolder() + "/recordings", args[1] + ".mcrr"));
                player.sendMessage(Text.color("&aLoaded recording."));
                return true;
            }

            if (this.recording == null) {
                player.sendMessage(Text.color("&cNo recording found."));
                return true;
            }

            this.recording = MCReplayPlugin.getInstance().getRecordingHandler().getFileProcessor().loadRecording(new File(MCReplayPlugin.getInstance().getDataFolder() + "/recordings", this.recording.id() + ".mcrr"));
            player.sendMessage(Text.color("&aLoaded recording."));
            return true;
        }

        return true;
    }

}