package worldmc;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import worldmc.Commands.ReloadCommand;
import worldmc.Listeners.EventGoldDrop;
import worldmc.Listeners.EventSpawnMobMethod;
import worldmc.Listeners.EventRandomSpawn;
import worldmc.Listeners.Spawner.EventBlockBreak;
import worldmc.Listeners.Spawner.EventBlockPlace;
import worldmc.Listeners.Spawner.EventSpawnerExplode;
import worldmc.Listeners.Spawner.EventSpawnerSpawn;

public class WMC extends JavaPlugin {

	public FileConfiguration Config = getConfig();

	public static WMC plugin;

	@Override
	public void onDisable() {
		plugin = null;
	}

	@Override
	public void onEnable() {
		// Save default configuration file
		this.saveDefaultConfig();
		getConfig();

		plugin = this;

		// Register events
		Bukkit.getPluginManager().registerEvents(new EventRandomSpawn(this), this);
		Bukkit.getPluginManager().registerEvents(new EventGoldDrop(this), this);
		Bukkit.getPluginManager().registerEvents(new EventSpawnMobMethod(this), this);
		Bukkit.getPluginManager().registerEvents(new EventBlockPlace(this), this);
		Bukkit.getPluginManager().registerEvents(new EventBlockBreak(this), this);
		Bukkit.getPluginManager().registerEvents(new EventSpawnerExplode(this), this);
		Bukkit.getPluginManager().registerEvents(new EventSpawnerSpawn(this), this);

		// Register command
		getCommand("wmcreload").setExecutor(new ReloadCommand(this));

		// Register all recipes
		if (getConfig().getBoolean("recipes.enabled")) {
			RecipeRegister.RegisterRecipes();
		}
	}
}
