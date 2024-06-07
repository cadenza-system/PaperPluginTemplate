package net.kunmc.lab.app.util.timer;

public class TimerContext {

    private final double limit;
    private final String displayName;
    private final DisplayType displayType;
    private final int countDown;
    private final double currentTime;
    private final double progressLate;

    public TimerContext(double limit, String displayName,
            DisplayType displayType, int countDown, double currentTime) {
        this.limit = limit;
        this.displayName = displayName;
        this.displayType = displayType;
        this.countDown = countDown;
        this.currentTime = currentTime;
        this.progressLate = TimerUtil.progressRate(currentTime, limit);
    }

    public double limit() {
        return limit;
    }

    public String displayName() {
        return displayName;
    }

    public DisplayType displayType() {
        return displayType;
    }

    public int countDown() {
        return countDown;
    }

    public double currentTime() {
        return currentTime;
    }

    public double progressLate() {
        return progressLate;
    }
}
