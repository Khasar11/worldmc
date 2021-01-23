package main.java.worldmc.Listeners.CT;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import main.java.worldmc.WMC;

public class EventPlayerCombatLog implements Listener {

	private WMC plugin;

	public EventPlayerCombatLog(WMC plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		if (plugin.getConfig().getBoolean("combat.enabled")) {
			Player p = event.getPlayer();
			if (plugin.tagged.contains(p)) {
				if (plugin.getConfig().getBoolean("combat.kill-on-combat-log")) {
					if (plugin.isEnabled()) p.setHealth(0.0);
				}
				if (plugin.getConfig().getBoolean("combat.execute-commands")) {
					ArrayList<String> cmds = new ArrayList<>(
							plugin.getConfig().getStringList("combat.combat-log-commands"));
					for (String cmd : cmds) {
						Bukkit.dispatchCommand(plugin.getServer().getConsoleSender(),
								cmd.replace("{USERNAME}", p.getName()));
					}
				}
			}
		}
	}
}
