package net.kunmc.lab.app.util.acitonbar;

import java.util.ArrayList;
import java.util.Set;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ActionBarManager {

    private static final ArrayList<ActionBar> actionBars = new ArrayList<>();

    public static void create(@NotNull String name, @NotNull Component text,
            @NotNull Set<Player> targets) {
        actionBars.add(new ActionBar(name, text, targets));
    }

    public static void create(@NotNull String name, @NotNull String text,
            @NotNull Set<Player> targets) {
        actionBars.add(new ActionBar(name, text, targets));
    }

    public static void create(@NotNull String name, @NotNull Component text,
            @NotNull Player target) {
        actionBars.add(new ActionBar(name, text, target));
    }

    public static void create(@NotNull String name, @NotNull String text,
            @NotNull Player target) {
        actionBars.add(new ActionBar(name, text, target));
    }

    public static void create(@NotNull String name, @NotNull Component text) {
        actionBars.add(new ActionBar(name, text));
    }

    public static void create(@NotNull String name, @NotNull String text) {
        actionBars.add(new ActionBar(name, text));
    }

    public static void stop(String name) {
        ActionBar actionBar = getActionBar(name);
        if (actionBar == null) {
            return;
        }
        actionBar.stop();
        actionBars.remove(actionBar);
    }

    public static void setText(String name, Component text) {
        ActionBar actionBar = getActionBar(name);
        if (actionBar == null) {
            return;
        }
        actionBar.setText(text);
    }

    public static void setText(String name, String text) {
        setText(name, Component.text(text));
    }

    public static void addTarget(String name, Player target) {
        ActionBar actionBar = getActionBar(name);
        if (actionBar == null) {
            return;
        }
        actionBar.addTarget(target);
    }

    public static void removeTarget(String name, Player target) {
        ActionBar actionBar = getActionBar(name);
        if (actionBar == null) {
            return;
        }
        actionBar.removeTarget(target);
    }

    private static ActionBar getActionBar(@NotNull String name) {
        for (ActionBar actionBar : actionBars) {
            if (actionBar.matchedName(name)) {
                return actionBar;
            }
        }
        return null;
    }

}
