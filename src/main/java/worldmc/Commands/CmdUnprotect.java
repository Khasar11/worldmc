package main.java.worldmc.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import main.java.worldmc.WMC;
import net.md_5.bungee.api.ChatColor;

public class CmdUnprotect implements CommandExecutor {

	private WMC plugin;

	public CmdUnprotect(WMC plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String t, String[] args) {
		if (plugin.protectedPlayers.contains(sender)) {
			plugin.protectedPlayers.remove(sender);
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					plugin.getConfig().getString("protection.prot-disable-msg")));
		} else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					plugin.getConfig().getString("protection.plr-not-protected-msg")));
		return true;
	}

}
