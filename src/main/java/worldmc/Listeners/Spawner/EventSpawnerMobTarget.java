package main.java.worldmc.Listeners.Spawner;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import main.java.worldmc.WMC;

import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EventSpawnerMobTarget implements Listener {

	private WMC plugin;

	public EventSpawnerMobTarget(WMC plugin) {
		this.plugin = plugin;
	}

	// Disable targetting if a condition is met in the config

	@EventHandler
	public void onEntityTarget(EntityTargetLivingEntityEvent event) {
		if (event.getTarget() instanceof Player) {
			if (plugin.getConfig().getBoolean("no-ai.enabled")) {
				Entity entity = event.getEntity();
				NBTEntity nbtent = new NBTEntity(entity);
				String spawnRsn = nbtent.getString("Paper.SpawnReason");
				SpawnReason spawnReason = SpawnReason.valueOf(spawnRsn);
				if (spawnMethodIsDisabled(entity, spawnReason)) {
					event.setCancelled(true);
				}
			}
		}
	}

	public boolean spawnMethodIsDisabled(Entity entity, SpawnReason spawnReason) {
		return plugin.getConfig().getStringList("no-ai.mobs." + entity.getType().name() + ".spawn-types")
				.contains(spawnReason.name());
	}
}
