package main.java.worldmc.Listeners.CT;

import java.util.ArrayList;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import main.java.worldmc.WMC;
import net.md_5.bungee.api.ChatColor;

public class EventPlayerCommand implements Listener {

	private WMC plugin;

	public EventPlayerCommand(WMC plugin) {
		this.plugin = plugin;
	}

	public void onCommand(PlayerCommandPreprocessEvent event) {
		if (plugin.getConfig().getBoolean("combat.prevent-specific-commandss")) {
			ArrayList<String> cmds = new ArrayList<>(plugin.getConfig().getStringList("combat.prevented-commands"));
			if (cmds.contains("/" + event.getMessage().toLowerCase())) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("combat.denied-command-msg")));
			}
		}
	}
}
