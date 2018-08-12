package com.hispanogamers.bomblovers.utils;

import org.bukkit.ChatColor;

public class Chat {
    public static String format(String formatted) {
        return ChatColor.translateAlternateColorCodes('&', formatted);
    }
}
