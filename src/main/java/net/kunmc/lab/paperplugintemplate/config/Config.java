package net.kunmc.lab.paperplugintemplate.config;

import net.kunmc.lab.configlib.BaseConfig;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Config extends BaseConfig {

    public Config(@NotNull Plugin plugin) {
        super(plugin);
        super.loadConfig();
    }
}
