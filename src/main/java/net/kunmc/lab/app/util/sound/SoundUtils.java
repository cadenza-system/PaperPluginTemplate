package net.kunmc.lab.app.util.sound;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class SoundUtils {

    public static void broadcastSound(Sound sound, float volume, float pitch) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getWorld().playSound(player.getEyeLocation(), sound, volume, pitch);
        });
    }
}
