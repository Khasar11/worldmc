package worldmc.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import worldmc.WMC;

public class ReloadCommand implements CommandExecutor {

	private WMC plugin;

	public ReloadCommand(WMC plugin) {
		this.plugin = plugin;
	}

	// Simple reload command
	
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender.hasPermission("wmc.reload")) {
			plugin.reloadConfig();
			commandSender.sendMessage(ChatColor.YELLOW + "Reloaded the config");
		} else {
			commandSender.sendMessage(ChatColor.RED + "No Permission");
		}
		return true;
	}
}
