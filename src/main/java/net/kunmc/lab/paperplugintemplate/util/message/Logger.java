package net.kunmc.lab.paperplugintemplate.util.message;

import net.kunmc.lab.paperplugintemplate.Store;
import net.kunmc.lab.paperplugintemplate.util.text.Text;
import org.bukkit.Bukkit;

public class Logger {

  public static void debug(String msg) {
    if (!Store.config.devMode.value()) {
      return;
    }
    String out = header() + "[debug] " + msg;
    Bukkit.getLogger().info(out);
    MessageUtil.broadcast(msg);
  }

  public static void debug(Text msg) {
    debug(msg.toString());
  }

  public static void debug(int msg) {
    debug(String.valueOf(msg));
  }

  public static void info(String msg) {
    Bukkit.getLogger().info(header() + msg);
  }

  public static void info(Text msg) {
    info(msg);
  }

  private static String header() {
    return "[" + Store.pluginName + "] ";
  }
}
