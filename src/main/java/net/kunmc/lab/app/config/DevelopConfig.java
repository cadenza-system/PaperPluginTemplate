package net.kunmc.lab.app.config;

import net.kunmc.lab.configlib.BaseConfig;
import net.kunmc.lab.configlib.value.BooleanValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class DevelopConfig extends BaseConfig {

    public final BooleanValue devMode = new BooleanValue(false);

    public DevelopConfig(@NotNull Plugin plugin) {
        super(plugin);
        super.loadConfig();
    }
}
