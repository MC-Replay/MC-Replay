package mc.replay.common.utils.text;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public final class Text {

    private Text() {
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static List<String> color(List<String> list) {
        List<String> colored = new ArrayList<>();

        for (String line : list) {
            colored.add(color(line));
        }

        return colored;
    }
}