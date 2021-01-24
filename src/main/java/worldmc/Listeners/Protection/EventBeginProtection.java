package main.java.worldmc.Listeners.Protection;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.worldmc.WMC;
import net.md_5.bungee.api.ChatColor;

public class EventBeginProtection implements Listener {

	private WMC plugin;

	public EventBeginProtection(WMC plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if (plugin.getConfig().getBoolean("protection.enabled")) {
			Player p = event.getPlayer();
			if (!p.hasPlayedBefore()) {
				plugin.protectedPlayers.add(p);
				Bukkit.getScheduler().runTaskLater(plugin, () -> {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("protection.begin-prot-msg")));
				}, 20L);
			}
			new BukkitRunnable() {
				@Override
				public void run() {
					if (plugin.protectedPlayers.contains(p)) {
						if ((p.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20) / 60 > plugin.getConfig()
								.getInt("protection.protection-time")) {
							plugin.protectedPlayers.remove(p);
							p.sendMessage(ChatColor.translateAlternateColorCodes('&',
									plugin.getConfig().getString("protection.prot-disable-msg")));
						}
					} else {
						cancel();
					}

				}
			}.runTaskTimer(plugin, 0L, 1200L);
		}
	}
}
