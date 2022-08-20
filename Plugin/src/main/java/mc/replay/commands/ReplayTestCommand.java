package mc.replay.commands;

import mc.replay.MCReplayPlugin;
import mc.replay.common.recordables.Recordable;
import mc.replay.recordables.entity.EntityRecordable;
import mc.replay.replay.session.ReplaySession;
import mc.replay.utils.color.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.NavigableMap;

public class ReplayTestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (!player.hasPermission("mc.replay.test")) {
            player.sendMessage(ChatColor.RED + "No permissions!");
            return false;
        }

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Please use: /replaytest <start/stop> <player>");
            return false;
        }

        if (args[0].equalsIgnoreCase("start")) {
            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "No player found with name " + args[1]);
                return false;
            }

            NavigableMap<Long, List<Recordable>> recordables;
            recordables = MCReplayPlugin.getInstance().getReplayStorage()
                    .getTypeRecordables(EntityRecordable.class, target.getUniqueId());

            MCReplayPlugin.getInstance().getSessions().put(player, new ReplaySession(recordables, List.of(player)));

            player.sendMessage(ChatColor.GREEN + "Replay started for player " + target.getName());
            return true;
        }

        if (args[0].equalsIgnoreCase("stop")) {
            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "No player found with name " + args[1]);
                return false;
            }

            ReplaySession replaySession = MCReplayPlugin.getInstance().getSessions().get(player);
            if (replaySession != null) {
                replaySession.stop();
                player.sendMessage(ChatColor.GREEN + "Replay stopped for player " + target.getName());
                return true;
            }

            player.sendMessage(Text.color("&cNo active replay found for player " + target.getName()));
            return false;
        }

        if (args[0].equalsIgnoreCase("save")) {
            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "No player found with name " + args[1]);
                return false;
            }

            MCReplayPlugin.getInstance().createReplay(player);
            player.sendMessage(ChatColor.GREEN + "Replay saved for player " + target.getName());
            return true;
        }

        return true;
    }

}