package main.java.worldmc.Listeners.Protection;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import main.java.worldmc.WMC;
import net.md_5.bungee.api.ChatColor;

public class EventCancelDamage implements Listener {

	private WMC plugin;

	public EventCancelDamage(WMC plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (plugin.getConfig().getBoolean("protection.enabled")) {
			if (plugin.getConfig().getStringList("protection.prevented-causes").contains(event.getCause().toString())
					&& event.getEntity() instanceof Player) {
				if (plugin.protectedPlayers.contains(event.getEntity())) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void plrAttackPlr(EntityDamageByEntityEvent event) {
		if (plugin.getConfig().getBoolean("protection.enabled")) {
			if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
				Player v = (Player) event.getEntity();
				Player a = (Player) event.getDamager();
				if (plugin.protectedPlayers.contains(a)) {
					event.setCancelled(true);
					a.sendMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("protection.attack-msg")));
					return;
				}
				if (plugin.protectedPlayers.contains(v)) {
					a.sendMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("protection.plr-protected-msg")));
				}
			}
		}
	}
}
