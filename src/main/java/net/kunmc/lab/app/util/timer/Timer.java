package net.kunmc.lab.app.util.timer;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.kunmc.lab.app.Store;
import net.kunmc.lab.app.util.acitonbar.ActionBarManager;
import net.kunmc.lab.app.util.bossbar.BroadcastBossBar;
import net.kunmc.lab.app.util.message.MessageUtil;
import net.kunmc.lab.app.util.sound.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
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
    private Set<Player> showTargets = new HashSet<>();

    Timer(final double limit) {
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
            this.currentTime);
    }

    Timer setDisplayName(final String displayName) {
        this.displayName = displayName;
        return this;
    }

    Timer setDisplayType(final DisplayType displayType) {
        this.displayType = displayType;
        return this;
    }

    Timer setCountDown(final int startValue, final boolean shouldPlaySound) {
        this.countDown = startValue;
        this.shouldPlayCountDownSound = shouldPlaySound;
        return this;
    }

    Timer setRegularProcess(@NotNull final RegularProcess regularlyProcess) {
        this.regularProcess = regularlyProcess;
        return this;
    }

    Timer addShowTarget(Player player) {
        this.showTargets.add(player);
        return this;
    }

    String remainingTime() {
        return TimerUtil.limitText("", this.currentTime);
    }

    Timer setEndProcess(@NotNull final EndProcess endProcess) {
        this.endProcess = endProcess;
        return this;
    }

    void start() {
        this.status = TimerStatus.Running;
        this.task = new Task();
    }

    void pause() {
        if (Objects.nonNull(this.task)) {
            this.status = TimerStatus.Paused;
        }
    }

    void resume() {
        if (Objects.nonNull(this.task)) {
            this.status = TimerStatus.Running;
        }
    }

    TimerStatus status() {
        return this.status;
    }

    void stop(final boolean shouldExecuteEndProcess) {
        this.task.stop(shouldExecuteEndProcess);
    }

    class Task extends BukkitRunnable {

        private String actionBarName;
        private BroadcastBossBar broadcastBossBar;
        private NamespacedKey bossBarKey;

        Task() {
            if (Timer.this.displayType == DisplayType.ACTIONBAR) {
                this.actionBarName = UUID.randomUUID().toString();
                if (showTargets.size() > 0) {
                    ActionBarManager.create(this.actionBarName, "", showTargets);
                } else {
                    ActionBarManager.create(this.actionBarName, "");
                }
            }

            if (Timer.this.displayType == DisplayType.BOSSBAR) {
                this.bossBarKey = new NamespacedKey(Store.plugin, UUID.randomUUID().toString());
                this.broadcastBossBar = new BroadcastBossBar(
                    Bukkit.createBossBar(bossBarKey,
                        TimerUtil.limitText(Timer.this.displayName, Timer.this.currentTime),
                        BarColor.GREEN,
                        BarStyle.SOLID));
                this.broadcastBossBar.bossBar.setProgress(0);
                this.broadcastBossBar.bossBar.setVisible(true);
            }

            this.runTaskTimer(Store.plugin, 0, 20);
        }

        void stop(final boolean shouldExecuteEndProcess) {
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
            Timer.this.status = TimerStatus.Finished;
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

                if (showTargets.size() > 0) {
                    for (Player target : showTargets) {
                        target.sendTitle(
                            String.valueOf((int) Math.floor(Timer.this.currentTime)),
                            "",
                            5,
                            10,
                            5);
                    }
                } else {
                    MessageUtil.broadcastTitle(
                        String.valueOf((int) Math.floor(Timer.this.currentTime)),
                        "",
                        5,
                        10,
                        5);
                }

            }
            Timer.this.currentTime--;

            if (Timer.this.currentTime >= 0) {
                return;
            }

            // 終了時
            showLimit();
            this.stop(true);

        }

        private void showLimit() {
            if (Timer.this.displayType == DisplayType.ACTIONBAR) {
                ActionBarManager.setText(this.actionBarName,
                    TimerUtil.limitText(Timer.this.displayName, Timer.this.currentTime));
            }
            if (Timer.this.displayType == DisplayType.BOSSBAR) {
                final double progressRate = TimerUtil.progressRate(Timer.this.currentTime,
                    Timer.this.limit);
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
