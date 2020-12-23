package worldmc.Listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import worldmc.WMC;

public class EventSpawnMobMethod implements Listener {

	private WMC plugin;

	public EventSpawnMobMethod(WMC plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event) {
		if (plugin.getConfig().getBoolean("no-ai.enabled")) {
			LivingEntity entity = event.getEntity();
			SpawnReason spawnReason = event.getSpawnReason();
			if (spawnMethodIsDisabled(entity, spawnReason)) {
				entity.setAI(false);
			}
		}
	}

	public boolean spawnMethodIsDisabled(LivingEntity entity, SpawnReason spawnReason) {
		return plugin.getConfig().getStringList("no-ai.mobs." + entity.getType().name() + ".spawn-types")
				.contains(spawnReason.name());
	}
}
