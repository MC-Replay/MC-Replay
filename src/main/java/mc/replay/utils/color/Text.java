package mc.replay.utils.color;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Text {

    public static String color(String msg) {
        return color(msg, false);
    }

    public static String color(String msg, boolean rgb) {
        return rgb ? RGBColorAPI.process(msg) : ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static List<String> color(List<String> list) {
        return color(list, false);
    }

    public static List<String> color(List<String> list, boolean rgb) {
        List<String> colored = new ArrayList<>();

        for (String line : list) {
            colored.add(color(line, rgb));
        }

        return colored;
    }
}