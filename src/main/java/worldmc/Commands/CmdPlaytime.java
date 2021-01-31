package main.java.worldmc.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.worldmc.WMC;
import net.md_5.bungee.api.ChatColor;

public class CmdPlaytime implements CommandExecutor {

	private WMC plugin;

	public CmdPlaytime(WMC plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String arg2, String[] args) {
		if (s instanceof Player) {
			Player p = (Player) s;
			if (args.length > 0) {
				Player other = Bukkit.getPlayer(args[0]);
				if (other != null) {
					p.sendMessage(ticksToFormat(other.getStatistic(Statistic.PLAY_ONE_MINUTE), p, other));
				} else {
					p.sendMessage(ChatColor.GREEN + "Wrong input");
				}
			} else {
				p.sendMessage(ticksToFormat(p.getStatistic(Statistic.PLAY_ONE_MINUTE), p, p));
			}
		}
		return true;
	}

	@SuppressWarnings("unused")
	public String ticksToFormat(int ticks, Player sender, Player other) {
		int s = 0, m = 0, h = 0, d = 0;
		String format = plugin.getConfig().getString("playtime.format");
		for (int i = ticks; ticks > 1728000; d++, ticks = ticks - 1728000);
		for (int i = ticks; ticks > 72000; h++, ticks = ticks - 72000);
		for (int i = ticks; ticks > 1200; m++, ticks = ticks - 1200);
		for (int i = ticks; ticks > 20; s++, ticks = ticks - 20);
		if (sender != other)
			format = plugin.getConfig().getString("playtime.format-other").replace("{USERNAME}", other.getName());
		format = format.replace("{D}", d + "").replace("{H}", h + "").replace("{M}", m + "").replace("{S}", s + "");
		return ChatColor.translateAlternateColorCodes('&', format);
	}

}
