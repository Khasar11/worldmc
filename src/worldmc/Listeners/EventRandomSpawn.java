package worldmc.Listeners;

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

import worldmc.UsefulFunctions;
import worldmc.WMC;

public class EventRandomSpawn implements Listener {

	private WMC plugin;

	public EventRandomSpawn(WMC plugin) {
		this.plugin = plugin;
	}

	Random rand = new Random();
	String failString = "Tried to teleport but an error occured potentially chunk generation related";
	World world = Bukkit.getServer().getWorld(WMC.plugin.Config.getString("random-spawn.world-name"));
	Boolean townyEnabled = WMC.plugin.getServer().getPluginManager().isPluginEnabled("Towny");

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (plugin.getConfig().getBoolean("random-spawn.enabled")) {
			Player p = event.getPlayer();
			if (p.getBedSpawnLocation() == null) {
				Location tSpawnLoc, loc = getRandomSafeLocation(p);
				;
				if (townyEnabled) {
					tSpawnLoc = TownyAPI.getInstance().getTownSpawnLocation(p);
				} else {
					tSpawnLoc = null;
				}
				if (tSpawnLoc == null) {
					switch (plugin.getConfig().getString("random-spawn.teleport-mode")) {
					case "ground_tp": {
						try {
							loc.add(0, 1, 0);
							event.setRespawnLocation(loc);
						} catch (Exception e) {
							System.out.println("[WMC-RTP] " + failString);
							System.out.println(e);
						}
						break;
					}
					default: {
						event.setRespawnLocation(new Location(world, loc.getX(), 255, loc.getZ()));
						Bukkit.getScheduler().runTaskLater(plugin, () -> {
							p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 100));
						}, 3L);
						break;
					}
					}
				} else {
					event.setRespawnLocation(tSpawnLoc);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (plugin.getConfig().getBoolean("random-spawn.enabled")) {
			Player p = event.getPlayer();
			if (!p.hasPlayedBefore()) {
				Location loc = getRandomSafeLocation(p);

				switch (plugin.getConfig().getString("random-spawn.teleport-mode")) {
				case "ground_tp": {
					try {
						loc.add(0, 1, 0);
						p.teleport(loc);
					} catch (Exception e) {
						System.out.println("[WMC-RTP] " + failString);
						System.out.println(e);
					}
					break;
				}
				default: {
					p.teleport(new Location(world, loc.getX(), 255, loc.getZ()));
					Bukkit.getScheduler().runTaskLater(plugin, () -> {
						p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 100));
					}, 3L);
					break;
				}
				}
			}
		}
	}

	public Location getRandomSafeLocation(Player p) {
		int minX = plugin.getConfig().getInt("random-spawn.minX"),
				maxX = plugin.getConfig().getInt("random-spawn.maxX"),
				minZ = plugin.getConfig().getInt("random-spawn.minZ"),
				maxZ = plugin.getConfig().getInt("random-spawn.maxZ"), x = minX + rand.nextInt((maxX - minX) + 1),
				z = minZ + rand.nextInt((maxZ - minZ) + 1), y = world.getHighestBlockAt(x, z).getY();

		Location loc = new Location(world, x, y, z);
		Material mat = loc.getBlock().getType();
		int i = 0;

		while (!UsefulFunctions.matchesStringList(mat.toString(), "random-spawn.safe-materials")
				&& plugin.getConfig().getString("random-spawn.teleport-mode").contains("ground_tp")) {
			loc = getRandomSafeLocation(p);
			mat = loc.getBlock().getType();
			i++;
			if (i > plugin.getConfig().getInt("random-spawn.try-attempts")) {
				System.out.println("[WMC-RTP] Failed RTP " + plugin.getConfig().getInt("try-attempts")
						+ " times, Unsafe Location, using default");
				return null;
			}
		}
		return loc;
	}
}
