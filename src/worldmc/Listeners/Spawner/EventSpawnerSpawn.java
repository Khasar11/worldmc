package worldmc.Listeners.Spawner;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

import worldmc.WMC;

public class EventSpawnerSpawn implements Listener {

	private WMC plugin;

	public EventSpawnerSpawn(WMC plugin) {
		this.plugin = plugin;
	}

	// Disable illegal spawners from spawning mobs

	@EventHandler
	public void onSpawnerSpawn(SpawnerSpawnEvent event) {
		if (plugin.getConfig().getBoolean("spawners.delete-illegals")) {
			if (plugin.getConfig().getBoolean("spawners.enabled")) {
				EntityType e = event.getEntityType();
				ArrayList<String> legals = new ArrayList<String>(plugin.getConfig().getStringList("spawners.legals"));
				if (!legals.contains(e.toString())) {
					if (plugin.getConfig().getBoolean("spawners.delete-illegals")) {
						event.getSpawner().getBlock().setType(Material.AIR);
						event.getEntity().remove();
					}
				}
			}
		}
	}

}