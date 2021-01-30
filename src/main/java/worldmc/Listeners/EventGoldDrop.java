package main.java.worldmc.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import main.java.worldmc.WMC;

public class EventGoldDrop implements Listener {

	private WMC plugin;

	public EventGoldDrop(WMC plugin) {
		this.plugin = plugin;
	}

	// Remove gold drops

	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		if (plugin.getConfig().getBoolean("no-gold-drops.enabled")) {
			if (!plugin.getConfig().getStringList("no-gold-drops.entities")
					.contains(event.getEntity().getType().toString())) {
				return;
			}

			for (int i = 0; i < event.getDrops().size(); i++) {
				if (event.getDrops().get(i).getType().equals(Material.GOLD_INGOT)
						|| event.getDrops().get(i).getType().equals(Material.GOLD_NUGGET)
						|| event.getDrops().get(i).getType().equals(Material.GOLDEN_SWORD)) {
					event.getDrops().remove(i);
					i--;
				}
			}
		}
	}
}
