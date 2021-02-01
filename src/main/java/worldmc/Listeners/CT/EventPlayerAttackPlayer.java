package main.java.worldmc.Listeners.CT;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import main.java.worldmc.WMC;

public class EventPlayerAttackPlayer implements Listener {

	private WMC plugin;

	public EventPlayerAttackPlayer(WMC plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
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
				p.sendMessage(WMC.formatColors(plugin.getConfig().getString("combat.tag-msg")));
			plugin.tagged.add(p);
			if (plugin.getConfig().getBoolean("combat.disable-flight")) {
				p.setFlying(false);
				p.setAllowFlight(false);
			}

			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				plugin.tagged.remove(p);
				if (!plugin.tagged.contains(p)) {
					p.sendMessage(WMC.formatColors(plugin.getConfig().getString("combat.no-longer-in-combat-msg")));
				}
			}, plugin.getConfig().getInt("combat.combat-tag-time") * 20);
		}
	}
}
