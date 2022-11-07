package net.kunmc.lab.paperplugintemplate.util.acitonbar;

import java.util.ArrayList;
import java.util.Set;
import net.kunmc.lab.paperplugintemplate.util.text.Text;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ActionBarManager {

  private static ArrayList<ActionBar> actionBars = new ArrayList<>();

  public static void create(@NotNull String name, @NotNull Text text,
      @NotNull Set<Player> targets) {
    actionBars.add(new ActionBar(name, text, targets));
  }

  public static void create(@NotNull String name, @NotNull String text,
      @NotNull Set<Player> targets) {
    actionBars.add(new ActionBar(name, text, targets));
  }

  public static void create(@NotNull String name, @NotNull Text text,
      @NotNull Player target) {
    actionBars.add(new ActionBar(name, text, target));
  }

  public static void create(@NotNull String name, @NotNull String text,
      @NotNull Player target) {
    actionBars.add(new ActionBar(name, text, target));
  }

  public static void create(@NotNull String name, @NotNull Text text) {
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

  public static void setText(String name, Text text) {
    ActionBar actionBar = getActionBar(name);
    if (actionBar == null) {
      return;
    }
    actionBar.setText(text);
  }

  public static void setText(String name, String text) {
    setText(name, new Text(text));
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
