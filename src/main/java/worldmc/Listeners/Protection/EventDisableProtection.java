package main.java.worldmc.Listeners.Protection;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;

import main.java.worldmc.WMC;
import net.md_5.bungee.api.ChatColor;

public class EventDisableProtection implements Listener {

	private WMC plugin;

	public EventDisableProtection(WMC plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCraft(CraftItemEvent event) {
		if (plugin.getConfig().getBoolean("protection.enabled")
				&& plugin.getConfig().getBoolean("protection.disable-conditions.enabled")) {
			ArrayList<String> dc = new ArrayList<>(
					plugin.getConfig().getStringList("protection.disable-conditions.crafting"));
			String toMatch = event.getRecipe().getResult().getType().toString();
			if (dc.contains(toMatch) || (dc.contains("SWORD") && toMatch.contains("SWORD"))
					|| (dc.contains("AXE") && toMatch.contains("AXE"))) {
				Player p = (Player) event.getWhoClicked();
				plugin.protectedPlayers.remove(p);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',
						plugin.getConfig().getString("protection.prot-disable-msg")));
			}
		}
	}

	@EventHandler
	public void onPickup(EntityPickupItemEvent event) {
		if (plugin.getConfig().getBoolean("protection.enabled")
				&& plugin.getConfig().getBoolean("protection.disable-conditions.enabled")
				&& event.getEntity() instanceof Player) {
			ArrayList<String> dc = new ArrayList<>(
					plugin.getConfig().getStringList("protection.disable-conditions.pickup"));
			String toMatch = event.getItem().getItemStack().getType().toString();
			if (dc.contains(toMatch) || (dc.contains("SWORD") && toMatch.contains("SWORD"))
					|| (dc.contains("AXE") && toMatch.contains("AXE"))) {
				if (event.getEntity() instanceof Player) {
					Player p = (Player) event.getEntity();
					plugin.protectedPlayers.remove(p);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("protection.prot-disable-msg")));
				}
			}
		}
	}
}
