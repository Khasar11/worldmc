package main.java.worldmc.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.tr7zw.changeme.nbtapi.NBTItem;
import main.java.worldmc.WMC;

public class CmdSpawnerGive implements CommandExecutor {

	public CmdSpawnerGive(WMC plugin) {
	}

	@Override
	public boolean onCommand(CommandSender cmdSender, Command command, String label, String[] args) {
		try {
			Player p = Bukkit.getPlayer(args[0]);
			if (p != null) {
				if (args[1] != null) {
					EntityType mob = EntityType.valueOf(args[1].toUpperCase());
					int amount = 1;
					if (args[2] != null) {
						amount = Integer.parseInt(args[2]);
					}
					ItemStack item = new ItemStack(Material.SPAWNER, amount);

					ItemMeta meta = item.getItemMeta();
					String entityName = mob.name();
					entityName.replace("_", " ");
					String dName = ChatColor.GREEN + entityName.substring(0, 1);
					dName = dName + entityName.substring(1).toLowerCase() + " Spawner";
					meta.setDisplayName(dName.replace("_", " "));
					item.setItemMeta(meta);

					NBTItem nbti = new NBTItem(item);
					nbti.setString("wmc_spawn_type", mob.toString());
					item = nbti.getItem();

					p.getInventory().addItem(item);
					if (p != cmdSender) {
						p.sendMessage(ChatColor.GREEN + "You were given " + amount + " " + mob.toString().toLowerCase()
								+ " spawner(s)");
						cmdSender.sendMessage(ChatColor.GREEN + "You gave " + amount + " "
								+ mob.toString().toLowerCase() + " spawner(s) to" + p);
						return true;
					}
					p.sendMessage(ChatColor.GREEN + "Gave yourself " + amount + " " + mob.toString().toLowerCase()
							+ " spawner(s)");

				}
				return true;
			}
		} catch (Exception ignore) {
			cmdSender.sendMessage(
					ChatColor.RED + "Error, expecting at least 2 arguments but none given or wrongful mob input");
		}
		return false;
	}

}
