package main.java.worldmc.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import main.java.worldmc.WMC;

public class CmdUnprotect implements CommandExecutor {

	private WMC plugin;

	public CmdUnprotect(WMC plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String t, String[] args) {
		if (plugin.protectedPlayers.contains(s)) {
			plugin.protectedPlayers.remove(s);
			s.sendMessage(WMC.formatColors(plugin.getConfig().getString("protection.prot-disable-msg")));
		} else
			s.sendMessage(WMC.formatColors(plugin.getConfig().getString("protection.plr-not-protected-msg")));
		return true;
	}

}
