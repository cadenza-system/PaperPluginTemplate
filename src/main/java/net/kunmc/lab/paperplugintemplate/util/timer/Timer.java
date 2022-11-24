package net.kunmc.lab.paperplugintemplate.util.timer;

import java.util.Objects;
import java.util.UUID;
import net.kunmc.lab.paperplugintemplate.Store;
import net.kunmc.lab.paperplugintemplate.util.acitonbar.ActionBarManager;
import net.kunmc.lab.paperplugintemplate.util.bossbar.BroadcastBossBar;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {

  private final double limit;
  private String displayName;
  private DisplayType displayType;
  private int countDown;
  private double currentTime;
  private Task task;

  private RegularProcess regularProcess;
  private EndProcess endProcess;

  public Timer(int limit) {
    this.limit = limit;
    this.currentTime = limit;
    this.displayType = DisplayType.NONE;
  }

  public Timer setDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public Timer setDisplayType(DisplayType displayType) {
    this.displayType = displayType;
    return this;
  }

  public Timer setCountDown(int startValue) {
    this.countDown = startValue;
    return this;
  }

  public Timer setRegularProcess(RegularProcess regularlyProcess) {
    this.regularProcess = regularlyProcess;
    return this;
  }

  public Timer setEndProcess(EndProcess endProcess) {
    this.endProcess = endProcess;
    return this;
  }

  public void start() {
    this.task = new Task();
  }

  public void pause() {
    if (Objects.nonNull(this.task)) {
      this.task.pause();
    }
  }

  public void resume() {
    if (Objects.nonNull(this.task)) {
      this.task.resume();
    }
  }

  class Task extends BukkitRunnable {

    private boolean isRunning = true;
    private String actionBarName;
    private BroadcastBossBar broadcastBossBar;
    private NamespacedKey bossBarKey;

    public Task() {
      if (Timer.this.displayType == DisplayType.ACTIONBAR) {
        this.actionBarName = UUID.randomUUID().toString();
        ActionBarManager.create(this.actionBarName, "");
      }

      if (Timer.this.displayType == DisplayType.BOSSBAR) {
        this.bossBarKey = Objects.requireNonNull(
            NamespacedKey.fromString(UUID.randomUUID().toString()));
        this.broadcastBossBar = new BroadcastBossBar(
            Bukkit.createBossBar(bossBarKey,
                this.limitText(), BarColor.GREEN, BarStyle.SOLID));
        this.broadcastBossBar.bossBar.setProgress(0);
        this.broadcastBossBar.bossBar.setVisible(true);
      }

      this.runTaskTimerAsynchronously(Store.plugin, 0, 20);
    }

    void pause() {
      this.isRunning = false;
    }

    void resume() {
      this.isRunning = true;
    }

    @Override
    public void run() {
      if (!this.isRunning) {
        return;
      }
      showLimit();
      if (Objects.nonNull(Timer.this.regularProcess)) {
        if (Timer.this.regularProcess.execute()) {
          this.cancel();
        }
      }

      Timer.this.currentTime--;

      if (Timer.this.currentTime >= 0) {
        return;
      }

      // 終了時
      showLimit();
      if (Timer.this.displayType == DisplayType.ACTIONBAR) {
        ActionBarManager.stop(this.actionBarName);
      }

      if (Timer.this.displayType == DisplayType.BOSSBAR) {
        this.broadcastBossBar.bossBar.setVisible(false);
        Bukkit.removeBossBar(this.bossBarKey);
      }

      if (Objects.nonNull(Timer.this.endProcess)) {
        Timer.this.endProcess.execute();
      }
      this.cancel();
    }

    private String limitText() {
      String text = "";
      if (Objects.nonNull(Timer.this.displayName)) {
        text = Timer.this.displayName;
      }
      return text
          .concat(" 残り ")
          .concat(String.valueOf((int) Math.floor(Timer.this.currentTime)))
          .concat("秒");
    }

    private void showLimit() {
      if (Timer.this.displayType == DisplayType.ACTIONBAR) {
        ActionBarManager.setText(this.actionBarName, this.limitText());
      }
      if (Timer.this.displayType == DisplayType.BOSSBAR) {
        double progressRate = this.progressRate();
        if (progressRate > 0.5) {
          this.broadcastBossBar.bossBar.setColor(BarColor.GREEN);
        } else if (progressRate > 0.2) {
          this.broadcastBossBar.bossBar.setColor(BarColor.YELLOW);
        } else {
          this.broadcastBossBar.bossBar.setColor(BarColor.RED);
        }

        this.broadcastBossBar.bossBar.setTitle(this.limitText());
        this.broadcastBossBar.bossBar.setProgress(this.progressRate());
      }
    }

    private double progressRate() {
      double rate = Timer.this.currentTime / Timer.this.limit;
      if (rate < 0) {
        rate = 0;
      }
      return rate;
    }
  }
}
