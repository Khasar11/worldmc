package worldmc.Listeners.Welcome;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import worldmc.WMC;

public class EventPlayerQuit implements Listener {

	private WMC plugin;

	public EventPlayerQuit(WMC plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if (plugin.toWelcome == p) {
			plugin.toWelcome = null;
		}
	}
}
