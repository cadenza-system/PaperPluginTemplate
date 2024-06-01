package net.kunmc.lab.paperplugintemplate;


import java.util.Objects;
import net.kunmc.lab.commandlib.CommandLib;
import net.kunmc.lab.configlib.ConfigCommandBuilder;
import net.kunmc.lab.paperplugintemplate.command.MainCommand;
import net.kunmc.lab.paperplugintemplate.config.Config;
import net.kunmc.lab.paperplugintemplate.config.DevelopConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperPluginTemplate extends JavaPlugin {
    
    @Override
    public void onEnable() {
        Store.pluginName = this.getName();
        Store.plugin = this;
        Store.config = new Config(this);
        Store.developConfig = new DevelopConfig(this);
        CommandLib.register(this,
            new MainCommand("main", new ConfigCommandBuilder(Store.config).build()));
    }

    @Override
    public void onDisable() {
    }

    public static void print(Object obj) {
        if (Objects.equals(System.getProperty("plugin.env"), "DEV")) {
            System.out.printf("[%s] %s%n", PaperPluginTemplate.class.getSimpleName(), obj);
        }
    }

    public static void broadcast(Object obj) {
        if (Objects.equals(System.getProperty("plugin.env"), "DEV")) {
            Bukkit.broadcastMessage(
                String.format("[%s] %s", PaperPluginTemplate.class.getSimpleName(), obj));
        }
    }
}
