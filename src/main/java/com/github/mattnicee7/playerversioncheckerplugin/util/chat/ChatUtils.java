package com.github.mattnicee7.playerversioncheckerplugin.util.chat;

import lombok.val;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ChatUtils {

    private static boolean HEX_COLOR_SUPPORT;

    public static final Pattern HEX_COLOR_PATTERN = Pattern.compile("&#([0-9A-Fa-f]{6})");

    private ChatUtils() {
        throw new UnsupportedOperationException("This class is not instantiable");
    }

    static {
        try {
            net.md_5.bungee.api.ChatColor.class.getDeclaredMethod("of", String.class);
            HEX_COLOR_SUPPORT = true;
        } catch (NoSuchMethodException e) {
            HEX_COLOR_SUPPORT = false;
        }
    }

    /**
     * It takes a string, and replaces all hex color codes with their corresponding ChatColor
     *
     * @param text
     *        The text to colorize
     *
     * @return The colored text.
     */
    public static String colorize(String text) {
        var coloredText = text;
        if (HEX_COLOR_SUPPORT) {
            val matcher = HEX_COLOR_PATTERN.matcher(coloredText);
            val buffer = new StringBuffer();

            while (matcher.find())
                matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of("#" + matcher.group(1)).toString());

            coloredText = matcher.appendTail(buffer).toString();
        }
        return ChatColor.translateAlternateColorCodes('&', coloredText);
    }

    public static List<String> colorizeMessages(List<String> messages) {
        return messages.stream().map(ChatUtils::colorize).collect(Collectors.toList());
    }

    public static boolean supportHexColor() {
        return HEX_COLOR_SUPPORT;
    }

}
