package net.kunmc.lab.app.util.timer;

public abstract class RegularProcess {

    /**
     * return: cancel true or false
     */
    public abstract boolean execute(TimerContext context);
}
