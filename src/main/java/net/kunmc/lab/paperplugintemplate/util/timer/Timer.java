package net.kunmc.lab.paperplugintemplate.util.timer;

import java.util.Objects;
import java.util.UUID;
import net.kunmc.lab.paperplugintemplate.Store;
import net.kunmc.lab.paperplugintemplate.util.acitonbar.ActionBarManager;
import net.kunmc.lab.paperplugintemplate.util.bossbar.BroadcastBossBar;
import net.kunmc.lab.paperplugintemplate.util.message.MessageUtil;
import net.kunmc.lab.paperplugintemplate.util.sound.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class Timer {

  private final double limit;
  private String displayName;
  private DisplayType displayType;
  private int countDown = -1;
  private double currentTime;
  private Task task;
  private TimerStatus status = TimerStatus.Waiting;

  private RegularProcess regularProcess;
  private EndProcess endProcess;
  private boolean shouldPlayCountDownSound;

  public Timer(int limit) {
    this.limit = limit;
    this.currentTime = limit;
    this.displayType = DisplayType.NONE;
  }

  private TimerContext buildContext() {
    return new TimerContext(
        this.limit,
        this.displayName,
        this.displayType,
        this.countDown,
        this.currentTime
    );
  }

  public Timer setDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public Timer setDisplayType(DisplayType displayType) {
    this.displayType = displayType;
    return this;
  }

  public Timer setCountDown(int startValue, boolean shouldPlaySound) {
    this.countDown = startValue;
    this.shouldPlayCountDownSound = shouldPlaySound;
    return this;
  }

  public Timer setRegularProcess(@NotNull RegularProcess regularlyProcess) {
    this.regularProcess = regularlyProcess;
    return this;
  }

  public Timer setEndProcess(@NotNull EndProcess endProcess) {
    this.endProcess = endProcess;
    return this;
  }

  public void start() {
    this.status = TimerStatus.Running;
    this.task = new Task();
  }

  public void pause() {
    if (Objects.nonNull(this.task)) {
      this.status = TimerStatus.Paused;
    }
  }

  public void resume() {
    if (Objects.nonNull(this.task)) {
      this.status = TimerStatus.Running;
    }
  }

  public TimerStatus status() {
    return this.status;
  }

  public void stop(boolean shouldExecuteEndProcess) {
    this.task.stop(shouldExecuteEndProcess);
  }

  class Task extends BukkitRunnable {

    private String actionBarName;
    private BroadcastBossBar broadcastBossBar;
    private NamespacedKey bossBarKey;

    public Task() {
      if (Timer.this.displayType == DisplayType.ACTIONBAR) {
        this.actionBarName = UUID.randomUUID().toString();
        ActionBarManager.create(this.actionBarName, "");
      }

      if (Timer.this.displayType == DisplayType.BOSSBAR) {
        this.bossBarKey = new NamespacedKey(Store.plugin, UUID.randomUUID().toString());
        this.broadcastBossBar = new BroadcastBossBar(
            Bukkit.createBossBar(bossBarKey,
                TimerUtil.limitText(Timer.this.displayName, Timer.this.currentTime), BarColor.GREEN,
                BarStyle.SOLID));
        this.broadcastBossBar.bossBar.setProgress(0);
        this.broadcastBossBar.bossBar.setVisible(true);
      }

      this.runTaskTimerAsynchronously(Store.plugin, 0, 20);
    }

    void stop(boolean shouldExecuteEndProcess) {
      if (Timer.this.displayType == DisplayType.ACTIONBAR) {
        ActionBarManager.stop(this.actionBarName);
      }

      if (Timer.this.displayType == DisplayType.BOSSBAR) {
        this.broadcastBossBar.bossBar.setVisible(false);
        Bukkit.removeBossBar(this.bossBarKey);
      }

      if (shouldExecuteEndProcess && Objects.nonNull(Timer.this.endProcess)) {
        Timer.this.endProcess.execute(Timer.this.buildContext());
      }
      this.cancel();
    }

    @Override
    public void run() {
      if (Timer.this.status != TimerStatus.Running) {
        return;
      }
      showLimit();
      if (Objects.nonNull(Timer.this.regularProcess)) {
        if (Timer.this.regularProcess.execute(Timer.this.buildContext())) {
          this.cancel();
        }
      }

      if (Timer.this.countDown >= Timer.this.currentTime) {
        if (shouldPlayCountDownSound) {
          SoundUtils.broadcastSound(Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1, 1);
        }

        MessageUtil.broadcastTitle(
            String.valueOf((int) Math.floor(Timer.this.currentTime)),
            "",
            5,
            10,
            5
        );
      }
      Timer.this.currentTime--;

      if (Timer.this.currentTime >= 0) {
        return;
      }

      // 終了時
      showLimit();
      this.stop(true);
      Timer.this.status = TimerStatus.Finished;
    }


    private void showLimit() {
      if (Timer.this.displayType == DisplayType.ACTIONBAR) {
        ActionBarManager.setText(this.actionBarName,
            TimerUtil.limitText(Timer.this.displayName, Timer.this.currentTime));
      }
      if (Timer.this.displayType == DisplayType.BOSSBAR) {
        double progressRate = TimerUtil.progressRate(Timer.this.currentTime, Timer.this.limit);
        if (progressRate > 0.5) {
          this.broadcastBossBar.bossBar.setColor(BarColor.GREEN);
        } else if (progressRate > 0.2) {
          this.broadcastBossBar.bossBar.setColor(BarColor.YELLOW);
        } else {
          this.broadcastBossBar.bossBar.setColor(BarColor.RED);
        }

        this.broadcastBossBar.bossBar.setTitle(
            TimerUtil.limitText(Timer.this.displayName, Timer.this.currentTime));
        this.broadcastBossBar.bossBar.setProgress(
            TimerUtil.progressRate(Timer.this.currentTime, Timer.this.limit));
      }
    }
  }
}
