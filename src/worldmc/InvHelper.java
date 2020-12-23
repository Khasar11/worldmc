package worldmc;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InvHelper {
	
	public static ItemStack getMainItem(Player player) {
		return player.getInventory().getItemInMainHand();
	}

	public static ItemStack getOffhandItem(Player player) {
		return player.getInventory().getItemInOffHand();
	}

	public static Boolean hasEmptySlot(Player player) {
		if (player.getInventory().firstEmpty() > 0)
			return true;
		return false;
	}

}
