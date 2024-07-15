package net.kunmc.lab.app.util.timer;

import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TimerBuilder {

    private final Timer timer;

    public TimerBuilder(final double limit) {
        this.timer = new Timer(limit);
    }

    public TimerBuilder setDisplayName(final String displayName) {
        this.timer.setDisplayName(displayName);
        return this;
    }

    public TimerBuilder setDisplayType(final DisplayType displayType) {
        this.timer.setDisplayType(displayType);
        return this;
    }

    public TimerBuilder setCountDown(final int startValue, final boolean shouldPlaySound) {
        this.timer.setCountDown(startValue, shouldPlaySound);
        return this;
    }

    public TimerBuilder addShowTarget(Player player) {
        this.timer.addShowTarget(player);
        return this;
    }

    public TimerBuilder addShowTargets(Set<Player> players) {
        for (Player player : players) {
            this.timer.addShowTarget(player);
        }
        return this;
    }

    public TimerBuilder setRegularProcess(@NotNull final RegularProcess regularlyProcess) {
        this.timer.setRegularProcess(regularlyProcess);
        return this;
    }

    public TimerBuilder setEndProcess(@NotNull final EndProcess endProcess) {
        this.timer.setEndProcess(endProcess);
        return this;
    }

    public UUID start() {
        final UUID uuid = UUID.randomUUID();
        this.timer.start();
        TimerManager.addTimer(uuid, timer);
        return uuid;
    }
}
