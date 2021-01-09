package main.java.worldmc.Listeners.Welcome;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import main.java.worldmc.WMC;

public class EventPlayerChat implements Listener {

	private WMC plugin;

	public EventPlayerChat(WMC plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		if (plugin.toWelcome != null && plugin.toWelcome != p) {
			if (!plugin.welcomed.contains(p)) {
				String m = event.getMessage();
				String wMsg = plugin.getConfig().getString("welcome.message");
				Float volume = (float) plugin.getConfig().getInt("welcome.volume"),
						pitch = (float) plugin.getConfig().getInt("welcome.pitch");
				m = m.replace("?", "").replace("!", "").replace(" ", "").toLowerCase();
				if (m.contains(wMsg)) {
					plugin.welcomed.add(p);
					ArrayList<String> toExecute = new ArrayList<String>(
							plugin.getConfig().getStringList("welcome.reward-commands"));
					int commandAmount = toExecute.size();
					Bukkit.getScheduler().runTaskLater(plugin, () -> {
						if (!event.isCancelled()) {
							for (int i = 0; i < commandAmount; i++) {
								Bukkit.dispatchCommand(plugin.getServer().getConsoleSender(),
										toExecute.get(i).replace("{USERNAME}", p.getName()));
							}
							p.playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("welcome.sound")),
									volume, pitch);
						}
					}, 2L);
				}
			}
		}
	}
}
