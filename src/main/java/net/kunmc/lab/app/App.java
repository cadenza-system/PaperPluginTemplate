package net.kunmc.lab.app;

import java.util.Objects;
import net.kunmc.lab.app.command.MainCommand;
import net.kunmc.lab.app.config.Config;
import net.kunmc.lab.app.config.DevelopConfig;
import net.kunmc.lab.commandlib.CommandLib;
import net.kunmc.lab.configlib.ConfigCommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class App extends JavaPlugin {

    @Override
    public void onEnable() {
        Store.pluginName = this.getName();
        Store.plugin = this;
        Store.config = new Config(this);
        Store.developConfig = new DevelopConfig(this);
        CommandLib.register(this,
            new MainCommand("main", new ConfigCommandBuilder(Store.config).build()));
//        Store.queuedExecutor = new QueuedExecutor(100000);
//        Bukkit.getPluginManager().registerEvents(Store.queuedExecutor, this);
    }

    @Override
    public void onDisable() {
    }

    public static void print(Object obj) {
        if (Objects.equals(System.getProperty("plugin.env"), "DEV")) {
            System.out.printf("[%s] %s%n", App.class.getSimpleName(), obj);
        }
    }

    public static void broadcast(Object obj) {
        if (Objects.equals(System.getProperty("plugin.env"), "DEV")) {
            Bukkit.broadcastMessage(
                String.format("[%s] %s", App.class.getSimpleName(), obj));
        }
    }
}
