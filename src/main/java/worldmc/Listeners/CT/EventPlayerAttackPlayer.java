package main.java.worldmc.Listeners.CT;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import main.java.worldmc.WMC;
import net.md_5.bungee.api.ChatColor;

public class EventPlayerAttackPlayer implements Listener {

	private WMC plugin;

	public EventPlayerAttackPlayer(WMC plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent event) {
		if (plugin.getConfig().getBoolean("combat.enabled")) {
			if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
				Player v = (Player) event.getEntity(), a = (Player) event.getDamager();

				if (v != a) {
					if (!event.isCancelled()) {
						handleTags(a);
						handleTags(v);
					}
				}
			}
		}
	}

	public void handleTags(Player p) {
		if (!p.hasPermission("wmc.combattag.ignore")) {
			if (!plugin.tagged.contains(p))
				p.sendMessage(
						ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("combat.tag-msg")));
			plugin.tagged.add(p);

			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				plugin.tagged.remove(p);
				if (!plugin.tagged.contains(p)) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("combat.no-longer-in-combat-msg")));
				}
			}, plugin.getConfig().getInt("combat.combat-tag-time") * 20);
		}
	}
}
