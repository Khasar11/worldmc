package worldmc.Listeners.Spawner;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;
import worldmc.WMC;
import worldmc.InvHelper;

public class EventSpawnerBreak implements Listener {

	private WMC plugin;

	public EventSpawnerBreak(WMC plugin) {
		this.plugin = plugin;
	}

	// Drop the correct item when a spawner is broken

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block b = event.getBlock();
		Player p = event.getPlayer();
		if (b.getType() == Material.SPAWNER) {
			if (p.getGameMode() != GameMode.CREATIVE) {
				CreatureSpawner cs = (CreatureSpawner) b.getState();
				ItemStack ptool = InvHelper.getMainItem(p), newItem = new ItemStack(Material.SPAWNER, 1);
				if (plugin.getConfig().getBoolean("spawners.enabled")
						&& ptool.containsEnchantment(Enchantment.SILK_TOUCH)) {
					EntityType entity = cs.getSpawnedType();
					ItemMeta meta = newItem.getItemMeta();
					String entityName = entity.name();
					entityName.replace("_", " ");
					meta.setDisplayName(ChatColor.GREEN + entityName + " SPAWNER");
					newItem.setItemMeta(meta);
					NBTItem nbti;
					event.setExpToDrop(0);
					ArrayList<String> allowedTools = new ArrayList<String>(
							plugin.getConfig().getStringList("spawners.allowed-tools"));
					ArrayList<String> legals = new ArrayList<String>(
							plugin.getConfig().getStringList("spawners.legals"));

					if (allowedTools.contains(ptool.getType().toString())) {
						if (legals.contains(entity.name())) {

							nbti = new NBTItem(newItem);
							nbti.setString("wmc_spawn_type", entity.name());
							newItem = nbti.getItem();
							if (plugin.getConfig().getBoolean("spawners.place-spawner-in-inventory")
									&& InvHelper.hasEmptySlot(p)) {
								p.getInventory().addItem(newItem);
							} else {
								b.getWorld().dropItem(b.getLocation(), newItem);
							}
						} else {
							if (plugin.getConfig().getBoolean("spawners.delete-illegals")) {
								if (plugin.getConfig().getBoolean("spawners.reward-breaking-illegals")) {
									p.giveExp(plugin.getConfig().getInt("spawners.reward-amount"));
									p.sendMessage(ChatColor.GREEN + "Illegal spawner mined, you were compensated");
								}
								p.sendMessage(ChatColor.GREEN + "Illegal spawner mined, it was destroyed");
							} else {
								event.setCancelled(true);
								p.sendMessage(ChatColor.GREEN + "Illegal spawner mined, cancelled breaking");
							}
						}
						return;
					}
				}
				p.sendMessage(ChatColor.GREEN + "Illegal tool used");
				event.setCancelled(true);
			}
		}
	}
}
