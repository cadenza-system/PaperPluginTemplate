package net.kunmc.lab.app.util.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;

public class MessageUtil {

    public static void broadcast(String msg) {
        broadcast(Component.text(msg));
    }

    public static void broadcast(Component msg) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(msg);
        });
    }

    public static void broadcast(TextComponent msg) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(msg);
        });
    }

    public static void broadcastTitle(String title, String subTitle, int fadeIn, int stay,
            int fadeOut) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
        });
    }

    public static void broadcastTitle(TextComponent title, TextComponent subTitle, int fadeIn,
            int stay,
            int fadeOut) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendTitle(title.content(), subTitle.content(), fadeIn, stay, fadeOut);
        });
    }
}
