package main.java.worldmc.Listeners.CT;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import main.java.worldmc.WMC;
import net.md_5.bungee.api.ChatColor;

public class EventPlayerTP implements Listener {

	private WMC plugin;

	public EventPlayerTP(WMC plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		if (plugin.getConfig().getBoolean("combat.enabled") && plugin.getConfig().getBoolean("combat.tag-cancel-tp")) {
			Player p = event.getPlayer();
			if (plugin.tagged.contains(p)) {
				if (!plugin.getConfig().getStringList("combat.cancel-tp-excemptions")
						.contains(event.getCause().toString())) {
					event.setCancelled(true);
				}
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("combat.teleport-attempt-msg")));
			}
		}
	}
}
