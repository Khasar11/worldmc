package main.java.worldmc.Listeners.Welcome;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import main.java.worldmc.WMC;

public class EventPlayerFirstJoin implements Listener {

	private WMC plugin;

	public EventPlayerFirstJoin(WMC plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerFirstJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if (!p.hasPlayedBefore()) {
			plugin.toWelcome = p;
			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				if (plugin.toWelcome == p) {
					plugin.toWelcome = null;
					plugin.welcomed.clear();
				}
			}, plugin.getConfig().getInt("welcome.reward-time") * 20);
		}
	}
}
