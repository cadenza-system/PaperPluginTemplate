package net.kunmc.lab.app.command;

import net.kunmc.lab.commandlib.Command;
import net.kunmc.lab.configlib.ConfigCommand;
import org.jetbrains.annotations.NotNull;

public class MainCommand extends Command {

    public MainCommand(@NotNull String name, ConfigCommand configCommand) {
        super(name);
        addChildren(configCommand);
    }
}
