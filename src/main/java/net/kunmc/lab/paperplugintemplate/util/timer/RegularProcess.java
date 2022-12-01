package net.kunmc.lab.paperplugintemplate.util.timer;

public abstract class RegularProcess {

  /**
   * return: cancel true or false
   */
  public abstract boolean execute(TimerContext context);
}
