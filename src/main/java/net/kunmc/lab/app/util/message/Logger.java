package net.kunmc.lab.app.util.message;

import net.kunmc.lab.app.Store;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;

public class Logger {

    public static void debug(String msg) {
        if (!Store.developConfig.devMode.isTrue()) {
            return;
        }
        String out = header() + "[debug] " + msg;
        Bukkit.getLogger().info(out);
        MessageUtil.broadcast(msg);
    }

    public static void debug(TextComponent msg) {
        debug(msg.content());
    }

    public static void debug(int msg) {
        debug(String.valueOf(msg));
    }

    public static void info(String msg) {
        Bukkit.getLogger().info(header() + msg);
    }

    public static void info(TextComponent msg) {
        info(msg.content());
    }

    private static String header() {
        return "[" + Store.pluginName + "] ";
    }
}
