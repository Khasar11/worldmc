package main.java.worldmc.Listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.palmergames.bukkit.towny.TownyAPI;

import main.java.worldmc.WMC;

public class EventRandomSpawn implements Listener {

	private WMC plugin;

	public EventRandomSpawn(WMC plugin) {
		this.plugin = plugin;
	}

	Random rand = new Random();
	World world = Bukkit.getServer().getWorld(WMC.plugin.Config.getString("random-spawn.world-name"));
	Boolean townyEnabled = WMC.plugin.getServer().getPluginManager().isPluginEnabled("Towny");

	// Randomly spawn the user if their town (if towny is installed) and bed spawn
	// are not set

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (plugin.getConfig().getBoolean("random-spawn.enabled")) {
			Player p = event.getPlayer();
			if (p.getBedSpawnLocation() == null) {
				String TPMode = plugin.getConfig().getString("random-spawn.teleport-mode");
				ArrayList<String> safeMats = new ArrayList<String>(
						plugin.getConfig().getStringList("random-spawn.safe-materials"));
				Location tSpawnLoc, loc = getRandomSafeLocation(p, safeMats, TPMode,
						plugin.getConfig().getInt("random-spawn.try-attempts"));
				;
				if (townyEnabled) {
					tSpawnLoc = TownyAPI.getInstance().getTownSpawnLocation(p);
				} else {
					tSpawnLoc = null;
				}
				if (tSpawnLoc == null) {
					if (TPMode.contains("ground_tp")) {
						loc.add(0, 1, 0);
						event.setRespawnLocation(loc);
						return;
					}
					event.setRespawnLocation(new Location(world, loc.getX(), 255, loc.getZ()));
					Bukkit.getScheduler().runTaskLater(plugin, () -> {
						p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 100));
					}, 3L);
				} else {
					event.setRespawnLocation(tSpawnLoc);
				}
			}
		}
	}

	// Randomly spawn on first join

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (plugin.getConfig().getBoolean("random-spawn.enabled")) {
			Player p = event.getPlayer();
			if (!p.hasPlayedBefore()) {
				String TPMode = plugin.getConfig().getString("random-spawn.teleport-mode");
				ArrayList<String> safeMats = new ArrayList<String>(
						plugin.getConfig().getStringList("random-spawn.safe-materials"));
				Location loc = getRandomSafeLocation(p, safeMats, TPMode,
						plugin.getConfig().getInt("random-spawn.try-attempts"));

				if (TPMode.contains("ground_tp")) {
					Bukkit.getScheduler().runTaskLater(plugin, () -> {
						loc.add(0, 1, 0);
						p.teleport(loc);
					}, 3L);
					return;
				}

				p.teleport(new Location(world, loc.getX(), 255, loc.getZ()));
				Bukkit.getScheduler().runTaskLater(plugin, () -> {
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 100));
				}, 3L);
			}
		}
	}

	public Location getRandomSafeLocation(Player p, ArrayList<String> safeMats, String TPMode, int tryAttempts) {
		int minX = plugin.getConfig().getInt("random-spawn.minX"),
				maxX = plugin.getConfig().getInt("random-spawn.maxX"),
				minZ = plugin.getConfig().getInt("random-spawn.minZ"),
				maxZ = plugin.getConfig().getInt("random-spawn.maxZ"), x = minX + rand.nextInt((maxX - minX) + 1),
				z = minZ + rand.nextInt((maxZ - minZ) + 1), y = world.getHighestBlockAt(x, z).getY();

		Location loc = new Location(world, x, y, z);
		Material mat = loc.getBlock().getType();
		int i = 0;
		if (TPMode.contains("ground_tp")) {
			while (!safeMats.contains(mat.toString())) {
				loc = getRandomSafeLocation(p, safeMats, TPMode, tryAttempts);
				mat = loc.getBlock().getType();
				i++;
				if (i > tryAttempts) {
					System.out.println("[WMC-RTP] Failed RTP " + plugin.getConfig().getInt("try-attempts")
							+ " times, Unsafe Location, using default");
					return null;
				}
			}
		}
		return loc;
	}
}
