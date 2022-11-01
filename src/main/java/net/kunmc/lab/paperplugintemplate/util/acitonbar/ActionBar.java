package net.kunmc.lab.paperplugintemplate.util.acitonbar;

import java.util.HashSet;
import java.util.Set;
import net.kunmc.lab.paperplugintemplate.Store;
import net.kunmc.lab.paperplugintemplate.util.text.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

class ActionBar {

  private String name;
  private Runnable runnable;

  public ActionBar(String name, Text text, Set<Player> targets) {
    this.name = name;
    this.runnable = new Runnable(text, targets);
    this.run();
  }

  public ActionBar(String name, String text, Set<Player> targets) {
    this.runnable = new Runnable(new Text(text), targets);
    this.run();
  }

  public ActionBar(String name, Text text, Player target) {
    Set<Player> targets = new HashSet<>();
    targets.add(target);
    this.runnable = new Runnable(text, targets);
    this.run();
  }

  public ActionBar(String name, String text, Player target) {
    Set<Player> targets = new HashSet<>();
    targets.add(target);
    this.runnable = new Runnable(new Text(text), targets);
    this.run();
  }

  private void run() {
    this.runnable.runTaskTimerAsynchronously(Store.plugin, 0, 1);
  }

  public boolean matchedName(String name) {
    return this.name.equals(name);
  }

  public void addTarget(Player target) {
    this.runnable.addTarget(target);
  }

  public void removeTarget(Player target) {
    this.runnable.removeTarget(target);
  }

  public void setText(Text text) {
    this.runnable.setText(text);
  }

  public void stop() {
    this.runnable.cancel();
  }

  class Runnable extends BukkitRunnable {

    private Text text;
    private final Set<Player> targets;

    public Runnable(Text text, Set<Player> targets) {
      this.text = text;
      this.targets = targets;
    }

    public void addTarget(Player target) {
      this.targets.add(target);
    }

    public void removeTarget(Player target) {
      this.targets.remove(target);
    }

    public void setText(Text text) {
      this.text = text;
    }

    @Override
    public void run() {
      this.targets.forEach(player -> {
        player.sendActionBar(Component.text(this.text.toString()));
      });
    }
  }
}
