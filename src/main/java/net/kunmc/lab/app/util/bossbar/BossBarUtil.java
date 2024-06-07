package net.kunmc.lab.app.util.bossbar;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.KeyedBossBar;

public class BossBarUtil {

    public static void removeBossBars() {
        Iterator<KeyedBossBar> bossBars = Bukkit.getBossBars();
        while (bossBars.hasNext()) {
            NamespacedKey key = bossBars.next().getKey();
            removeBossBar(key.getNamespace(), key.getKey());
        }
    }

    public static void removeBossBars(String nameSpace) {
        Iterator<KeyedBossBar> bossBars = Bukkit.getBossBars();
        while (bossBars.hasNext()) {
            NamespacedKey key = bossBars.next().getKey();
            if (key.getNamespace().equals(nameSpace)) {
                removeBossBar(key.getNamespace(), key.getKey());
            }
        }
    }

    private static void removeBossBar(String nameSpace, String key) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "bossbar remove "
                        .concat(nameSpace)
                        .concat(":")
                        .concat(key));
    }
}
