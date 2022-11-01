package net.kunmc.lab.paperplugintemplate.util.message;

import net.kunmc.lab.paperplugintemplate.util.text.Text;
import org.bukkit.Bukkit;

public class MessageUtil {

  public static void broadcast(String msg) {
    Bukkit.getOnlinePlayers().forEach(player -> {
      player.sendMessage(msg);
    });
  }

  public static void broadcast(Text msg) {
    broadcast(msg);
  }
}
