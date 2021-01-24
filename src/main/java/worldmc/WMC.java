package main.java.worldmc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import main.java.worldmc.Commands.*;
import main.java.worldmc.Listeners.*;
import main.java.worldmc.Listeners.CT.*;
import main.java.worldmc.Listeners.Protection.*;
import main.java.worldmc.Listeners.Spawner.*;
import main.java.worldmc.Listeners.Welcome.*;

public class WMC extends JavaPlugin {

	public FileConfiguration Config = getConfig();

	public static WMC plugin;

	// Welcome reward player list
	public Player toWelcome = null;
	public ArrayList<Player> welcomed = new ArrayList<>();
	public ArrayList<Player> tagged = new ArrayList<>();
	public ArrayList<Player> protectedPlayers = new ArrayList<>();

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
		
		Bukkit.getPluginManager().registerEvents(new EventPlayerAttackPlayer(this), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerTP(this), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerCombatLog(this), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerCommand(this), this);
		
		Bukkit.getPluginManager().registerEvents(new EventBeginProtection(this), this);
		Bukkit.getPluginManager().registerEvents(new EventCancelDamage(this), this);
		Bukkit.getPluginManager().registerEvents(new EventDisableProtection(this), this);
		
		// Initialize NBTApi
		Initializers.initNBT();
		
		// Register command
		getCommand("wmcreload").setExecutor(new CmdReload(this));
		getCommand("wmcgivespawner").setExecutor(new CmdSpawnerGive(this));
		getCommand("unprotect").setExecutor(new CmdUnprotect(this));

		// Register all recipes
		if (getConfig().getBoolean("recipes.enabled")) {
			RecipeRegister.RegisterRecipes();
		}
	}
}
