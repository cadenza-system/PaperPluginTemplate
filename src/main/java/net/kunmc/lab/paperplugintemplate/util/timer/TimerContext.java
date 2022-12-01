package net.kunmc.lab.paperplugintemplate.util.timer;

public class TimerContext {

  private double limit;
  private String displayName;
  private DisplayType displayType;
  private int countDown;
  private double currentTime;
  private double progressLate;

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
