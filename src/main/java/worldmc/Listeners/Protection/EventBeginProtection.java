package main.java.worldmc.Listeners.Protection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.worldmc.WMC;

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
					p.sendMessage(WMC.formatColors(plugin.getConfig().getString("protection.begin-prot-msg")));
				}, 20L);
			}
			new BukkitRunnable() {
				int mpl = 0;

				@Override
				public void run() {
					if (plugin.protectedPlayers.contains(p)) {
						if (mpl > plugin.getConfig().getInt("protection.protection-time")) {
							plugin.protectedPlayers.remove(p);
							p.sendMessage(
									WMC.formatColors(plugin.getConfig().getString("protection.prot-disable-msg")));
						}
						mpl++;
					}
				}
			}.runTaskTimer(plugin, 0L, 1200L);
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player p = event.getPlayer();
		plugin.protectedPlayers.add(p);
		p.sendMessage(WMC.formatColors(plugin.getConfig().getString("protection.begin-prot-msg")));
	}
}
