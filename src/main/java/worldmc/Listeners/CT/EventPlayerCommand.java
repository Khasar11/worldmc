package main.java.worldmc.Listeners.CT;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import main.java.worldmc.WMC;
import net.md_5.bungee.api.ChatColor;

public class EventPlayerCommand implements Listener {

	private WMC plugin;

	public EventPlayerCommand(WMC plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		if (plugin.getConfig().getStringList("combat.disabled-commands").size() > 0) {
			ArrayList<String> cmds = new ArrayList<>(plugin.getConfig().getStringList("combat.disabled-commands"));
			if (cmds.contains(event.getMessage().toLowerCase())) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("combat.denied-command-msg")));
			}
		}
	}
}
