package storageLimiter;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Limiter extends ItemStack {

	public Limiter() {
		super(Material.BARRIER);
		ItemMeta item_meta = this.getItemMeta();
		item_meta.setDisplayName(" ");
		this.setItemMeta(item_meta);
	}
	
	public static boolean isLimiter(ItemStack item) {
		if(item != null && item.getType() == Material.BARRIER && item.getItemMeta().getDisplayName().equals(" ")) {
			return true;
		}
		return false;
	}
	
}
