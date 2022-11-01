package net.kunmc.lab.paperplugintemplate;

import net.kunmc.lab.commandlib.CommandLib;
import net.kunmc.lab.configlib.ConfigCommandBuilder;
import net.kunmc.lab.paperplugintemplate.command.MainCommand;
import net.kunmc.lab.paperplugintemplate.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperPluginTemplate extends JavaPlugin {

  @Override
  public void onEnable() {
    Store.pluginName = this.getName();
    Store.plugin = this;
    Store.config = new Config(this);
    CommandLib.register(this,
        new MainCommand("main", new ConfigCommandBuilder(Store.config).build()));
  }

  @Override
  public void onDisable() {
  }
}
