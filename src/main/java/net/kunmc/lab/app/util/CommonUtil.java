package net.kunmc.lab.app.util;

import java.util.UUID;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class CommonUtil {

    public static String getNameSpace(JavaPlugin plugin) {
        return new NamespacedKey(plugin, UUID.randomUUID().toString()).getNamespace();
    }
}
