package main.java.worldmc;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeRegister {

	// Register/Deregister all recipes if enabled

	public static void RegisterRecipes() {

		List<String> tweaked = WMC.plugin.getConfig().getStringList("recipes.tweaked");
		Iterator<Recipe> it = WMC.plugin.getServer().recipeIterator();
		Recipe recipe;
		int amountRecipes = tweaked.size(), tweakedRecipes = 0;

		while (it.hasNext()) {
			recipe = it.next();
			for (int i = 0; i < amountRecipes; i++) {
				if (recipe != null && recipe.getResult().getType() == Material.matchMaterial(tweaked.get(i))) {
					it.remove();
				}
			}
		}

		if (tweaked.contains("POWERED_RAIL")) {
			final ShapedRecipe rail = new ShapedRecipe(new NamespacedKey(WMC.plugin, "wmc_powered_rail"),
					new ItemStack(Material.POWERED_RAIL, 8));
			rail.shape("I I", "ISI", "IRI");
			rail.setIngredient('I', Material.IRON_INGOT);
			rail.setIngredient('S', Material.STICK);
			rail.setIngredient('R', Material.REDSTONE);
			Bukkit.addRecipe(rail);
			tweakedRecipes++;
		}
		if (tweaked.contains("CLOCK")) {
			final ShapedRecipe clock = new ShapedRecipe(new NamespacedKey(WMC.plugin, "wmc_clock"),
					new ItemStack(Material.CLOCK, 1));
			clock.shape("YIY", "IRI", "YIY");
			clock.setIngredient('Y', Material.YELLOW_DYE);
			clock.setIngredient('I', Material.IRON_INGOT);
			clock.setIngredient('R', Material.REDSTONE);
			Bukkit.addRecipe(clock);
			tweakedRecipes++;
		}
		if (tweaked.contains("LIGHT_WEIGHTED_PRESSURE_PLATE")) {
			final ShapelessRecipe wmc_l_w_p_p = new ShapelessRecipe(new NamespacedKey(WMC.plugin, "wmc_l_w_p_p"),
					new ItemStack(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, 1));
			wmc_l_w_p_p.addIngredient(1, Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
			wmc_l_w_p_p.addIngredient(1, Material.YELLOW_DYE);
			Bukkit.addRecipe(wmc_l_w_p_p);
			tweakedRecipes++;
		}
		if (tweaked.contains("GLISTERING_MELON_SLICE")) {
			final ShapelessRecipe wmc_g_melon = new ShapelessRecipe(new NamespacedKey(WMC.plugin, "wmc_g_melon"),
					new ItemStack(Material.GLISTERING_MELON_SLICE, 1));
			wmc_g_melon.addIngredient(8, Material.SUGAR);
			wmc_g_melon.addIngredient(1, Material.MELON_SLICE);
			Bukkit.addRecipe(wmc_g_melon);
			tweakedRecipes++;
		}
		if (tweaked.contains("NETHERITE_INGOT")) {
			final ShapelessRecipe netherite_ingot = new ShapelessRecipe(
					new NamespacedKey(WMC.plugin, "wmc_netherite_ingot"), new ItemStack(Material.NETHERITE_INGOT, 1));
			netherite_ingot.addIngredient(4, Material.NETHERITE_SCRAP);
			netherite_ingot.addIngredient(4, Material.DIAMOND);
			Bukkit.addRecipe(netherite_ingot);
			tweakedRecipes++;
		}
		if (WMC.plugin.getConfig().getBoolean("recipes.add-dark-prismarine")) {
			final ShapelessRecipe dark_prismarine = new ShapelessRecipe(
					new NamespacedKey(WMC.plugin, "wmc_dark_prismarine"), new ItemStack(Material.DARK_PRISMARINE, 8));
			dark_prismarine.addIngredient(8, Material.PRISMARINE);
			dark_prismarine.addIngredient(1, Material.BLACK_DYE);
			Bukkit.addRecipe(dark_prismarine);
			tweakedRecipes++;
		}
		if (WMC.plugin.getConfig().getBoolean("recipes.add-cobweb")) {
			final ShapelessRecipe cobweb = new ShapelessRecipe(new NamespacedKey(WMC.plugin, "wmc_cobweb"),
					new ItemStack(Material.COBWEB, 1));
			cobweb.addIngredient(9, Material.STRING);
			Bukkit.addRecipe(cobweb);
			tweakedRecipes++;
		}
		System.out.println("[WMC-Recipe-Tweaker] Tweaked or added " + tweakedRecipes + " Recipes");
	}
}
