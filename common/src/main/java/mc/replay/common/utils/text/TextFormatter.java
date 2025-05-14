package mc.replay.common.utils.text;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TextFormatter {

    public static final String PLACEHOLDER_PREFIX = "%";

    private List<String> lines;

    private TextFormatter(@NotNull List<String> lines) {
        this.lines = Text.color(lines);

        this.replace("prefix", ChatColor.GOLD + "[MC-Replay]");
    }

    public static TextFormatter of(@NotNull List<String> lines) {
        return new TextFormatter(lines);
    }

    public static TextFormatter of(@NotNull String line) {
        if (line.equalsIgnoreCase("")) {
            return of(new ArrayList<>());
        }

        return of(new ArrayList<>(List.of(line)));
    }

    public TextFormatter color() {
        this.lines = Text.color(this.lines);
        return this;
    }

    public TextFormatter replace(String placeholder, String replacement) {
        List<String> replaced = new ArrayList<>();

        for (String line : this.lines) {
            replaced.add(line.replaceAll(PLACEHOLDER_PREFIX + placeholder + PLACEHOLDER_PREFIX, Text.color(replacement)));
        }

        this.lines = replaced;
        return this;
    }

    public List<String> get() {
        return this.lines;
    }

    public String getSingleLine() {
        try {
            return this.lines.get(0);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
    }

    public void send(List<Player> players) {
        if (!this.lines.isEmpty()) {
            for (String line : this.lines) {
                for (Player player : players) {
                    player.sendMessage(line);
                }
            }
        }
    }

    public void send(Player... players) {
        this.send(List.of(players));
    }

    public void broadcast() {
        if (!this.lines.isEmpty()) {
            for (String line : this.lines) {
                Bukkit.broadcastMessage(line);
            }
        }
    }
}
