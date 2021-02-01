package main.java.worldmc.Listeners.Spawner;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBTItem;
import main.java.worldmc.WMC;

public class EventSpawnerPlace implements Listener {

	private WMC plugin;

	public EventSpawnerPlace(WMC plugin) {
		this.plugin = plugin;
	}

	// Insert the right mob into spawners when placed

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		Block b = event.getBlock();
		if (b.getType() == Material.SPAWNER) {
			Player p = event.getPlayer();
			if (plugin.getConfig().getBoolean("spawners.enabled")) {
				CreatureSpawner cs = (CreatureSpawner) b.getState();
				ItemStack item = event.getItemInHand();
				NBTItem nbti = new NBTItem(item);
				String mobType = nbti.getString("wmc_spawn_type");
				ArrayList<String> legals = new ArrayList<String>(plugin.getConfig().getStringList("spawners.legals"));
				if (mobType != "")
					cs.setSpawnedType(EntityType.valueOf(mobType));
				else
					cs.setSpawnedType(EntityType.PIG);
				if (!legals.contains(mobType) && mobType != "") {
					if (plugin.getConfig().getBoolean("spawners.prevent-illegal-placement")) {
						p.sendMessage(WMC.formatColors(plugin.getConfig().getString("spawners.illegal-placed")));
						event.setCancelled(true);
						return;
					}
				}
				cs.update();
				if (plugin.getConfig().getBoolean("spawners.notify-when-placed")) {
					plugin.getServer().broadcast(
							WMC.formatColors(plugin.getConfig().getString("spawners.spawner-place-notify")
									.replace("{USERNAME}", p.getName().replace("{TYPE}", mobType.toString()))),
							"wmc.notifyspawners");
				}
			} else {
				p.sendMessage(WMC.formatColors(plugin.getConfig().getString("spawners.spawner-disabled")));
				event.setCancelled(true);
			}
		}
	}
}
