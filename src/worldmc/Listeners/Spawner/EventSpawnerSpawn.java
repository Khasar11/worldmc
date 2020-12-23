package worldmc.Listeners.Spawner;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import worldmc.UsefulFunctions;
import worldmc.WMC;

public class EventSpawnerSpawn implements Listener {

	private WMC plugin;

	public EventSpawnerSpawn(WMC plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event) {
		if (plugin.getConfig().getBoolean("spawners.enabled")) {
			EntityType e = event.getEntityType();
			if (!UsefulFunctions.matchesStringList(e.toString(), "spawners.legals")) {
				if (event.getSpawnReason() == SpawnReason.SPAWNER) {
					event.setCancelled(true);
				}
			}
		}
	}
}