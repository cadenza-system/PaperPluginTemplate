package net.kunmc.lab.paperplugintemplate.util.acitonbar;

import java.util.HashSet;
import java.util.Set;
import net.kunmc.lab.paperplugintemplate.Store;
import net.kunmc.lab.paperplugintemplate.util.text.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

class ActionBar {

  private final String name;
  private BukkitTask runnable;
  private Text text;
  private Set<Player> targets = new HashSet<>();
  private boolean isBroadcast;

  public ActionBar(String name, Text text, Set<Player> targets) {
    this.name = name;
    this.text = text;
    this.targets = targets;
    this.run();
  }

  public ActionBar(String name, String text, Set<Player> targets) {
    this.name = name;
    this.text = new Text(text);
    this.targets = targets;
    this.run();
  }

  public ActionBar(String name, Text text, Player target) {
    this.name = name;
    this.text = text;
    this.targets.add(target);
    this.run();
  }

  public ActionBar(String name, String text, Player target) {
    this.name = name;
    this.text = new Text(text);
    this.targets.add(target);
    this.run();
  }

  public ActionBar(String name, Text text) {
    this.name = name;
    this.text = text;
    this.isBroadcast = true;
    this.run();
  }

  public ActionBar(String name, String text) {
    this.name = name;
    this.text = new Text(text);
    this.isBroadcast = true;
    this.run();
  }

  private void run() {
    this.runnable = new BukkitRunnable() {
      @Override
      public void run() {
        if (ActionBar.this.isBroadcast) {
          Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendActionBar(Component.text(ActionBar.this.text.toString()));
          });
        } else {
          ActionBar.this.targets.forEach(player -> {
            player.sendActionBar(Component.text(ActionBar.this.text.toString()));
          });
        }
      }
    }.runTaskTimerAsynchronously(Store.plugin, 0, 1);
  }

  public boolean matchedName(String name) {
    return this.name.equals(name);
  }

  public void addTarget(Player target) {
    if (this.isBroadcast) {
      return;
    }
    this.targets.add(target);
  }

  public void removeTarget(Player target) {
    if (this.isBroadcast) {
      return;
    }
    this.targets.remove(target);
  }

  public void setText(Text text) {
    this.text = text;
  }

  public void stop() {
    this.runnable.cancel();
  }
}
