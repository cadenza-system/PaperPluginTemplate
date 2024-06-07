package net.kunmc.lab.app.util.timer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimerManager {

    private static final Map<UUID, Timer> map = new HashMap<>();

    static void addTimer(final UUID uuid, final Timer timer) {
        map.put(uuid, timer);
    }

    public static void pause(final UUID key) {
        map.get(key).pause();
    }

    public static void resume(final UUID key) {
        map.get(key).resume();
    }

    public static void stop(final UUID key, final boolean shouldExecuteEndProcess) {
        map.get(key).stop(shouldExecuteEndProcess);
    }

    public static String getRemainingTime(final UUID key) {
        return map.get(key).remainingTime();
    }

    public static TimerStatus getStatus(final UUID key) {
        return map.get(key).status();
    }

    public static void stopAll(final boolean shouldExecuteEndProcess) {
        map.forEach((UUID uuid, Timer timer) -> {
            timer.stop(shouldExecuteEndProcess);
        });
    }

    public static void pauseAll() {
        map.forEach((UUID uuid, Timer timer) -> {
            timer.resume();
        });
    }

    public static void resumeAll() {
        map.forEach((UUID uuid, Timer timer) -> {
            timer.resume();
        });
    }
}
