package worldmc.Listeners.Spawner;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;
import worldmc.WMC;

public class EventBlockPlace implements Listener {

	private WMC plugin;

	public EventBlockPlace(WMC plugin) {
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
				if (mobType != "") cs.setSpawnedType(EntityType.valueOf(mobType));
				else cs.setSpawnedType(EntityType.PIG);
				cs.update();
				if (plugin.getConfig().getBoolean("spawners.notify-when-placed")) {
					plugin.getServer().broadcast(
							ChatColor.YELLOW + "[WMC-Spawners] " + p + " Just placed a " + mobType + " Spawner",
							"wmc.notifyspawners");
				}
			} else {
				p.sendMessage(ChatColor.GREEN + "Spawners are currently disabled");
				event.setCancelled(true);
			}
		}
	}
}
