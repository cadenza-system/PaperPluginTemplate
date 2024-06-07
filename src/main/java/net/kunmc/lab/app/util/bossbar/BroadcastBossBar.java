package net.kunmc.lab.app.util.bossbar;

import net.kunmc.lab.app.Store;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BroadcastBossBar implements Listener {

    public BossBar bossBar;

    public BroadcastBossBar(BossBar bossBar) {
        this.bossBar = bossBar;
        Bukkit.getPluginManager().registerEvents(this, Store.plugin);
        Bukkit.getOnlinePlayers().forEach(player -> {
            this.bossBar.addPlayer(player);
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.bossBar.addPlayer(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.bossBar.removePlayer(event.getPlayer());
    }
}
