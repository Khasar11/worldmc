package main.java.worldmc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import main.java.worldmc.Commands.ReloadCommand;
import main.java.worldmc.Listeners.*;
import main.java.worldmc.Listeners.Spawner.*;
import main.java.worldmc.Listeners.Welcome.*;

public class WMC extends JavaPlugin {

	public FileConfiguration Config = getConfig();

	public static WMC plugin;
	
	// Welcome reward player list
	public Player toWelcome = null; 
	public ArrayList<Player> welcomed = new ArrayList<>();
	
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
		Bukkit.getPluginManager().registerEvents(new EventSpawnerMobTarget(this), this);
		Bukkit.getPluginManager().registerEvents(new EventSpawnerPlace(this), this);
		Bukkit.getPluginManager().registerEvents(new EventSpawnerBreak(this), this);
		Bukkit.getPluginManager().registerEvents(new EventSpawnerExplode(this), this);
		Bukkit.getPluginManager().registerEvents(new EventSpawnerSpawn(this), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerChat(this), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerQuit(this), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerFirstJoin(this), this);

		// Register command
		getCommand("wmcreload").setExecutor(new ReloadCommand(this));

		// Register all recipes
		if (getConfig().getBoolean("recipes.enabled")) {
			RecipeRegister.RegisterRecipes();
		}
		
		Initializers.initNBT();
	}
}
