package storageLimiter;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class EventsListener implements Listener {
	
	Limiter item_limiter = new Limiter();
	ArrayList<Byte> required_columns = new ArrayList<>();
	
	public EventsListener() {
		required_columns.add((byte) 0);
		required_columns.add((byte) 1);
		required_columns.add((byte) 7);
		required_columns.add((byte) 8);
	}
	
	@EventHandler
	public void InventoryOpen(InventoryOpenEvent e) {
		
		Inventory inventory = e.getInventory();
		
		if(inventory.getType() == InventoryType.CHEST) {
			byte rows_in_storage = (byte) (inventory.getSize()/9);
			for(byte column = 0; column<9; column++) {
				if(required_columns.contains(column)) {
					byte row_counter = 0;
					for(byte i = column; row_counter < rows_in_storage; i+=9) {
						inventory.setItem(i, item_limiter);
						row_counter++;
					}
				}
			}
			
		}
		
		else if(inventory.getType() == InventoryType.BARREL) {
			
			for(byte index = 0; index < 9; index++) {
				inventory.setItem(index, item_limiter);
			}
			for(byte index = 18; index < 27; index++) {
				inventory.setItem(index, item_limiter);
			}
		}
	
	}
	
	@EventHandler
	public void InventoryClick(InventoryClickEvent e) {
		if(Limiter.isLimiter(e.getCurrentItem())) e.setCancelled(true);
	}
	
	
	@EventHandler
	public void BlockBreak(BlockBreakEvent e) {
		Block block = e.getBlock();
		Material material = block.getBlockData().getMaterial();
		if(material == Material.BARREL | material == Material.CHEST) {
			InventoryHolder storage_inventoryHolder = (InventoryHolder) block.getState();
            Inventory storage = storage_inventoryHolder.getInventory();
			for(ItemStack item : storage) {
				if(Limiter.isLimiter(item)) storage.remove(item);
			}
		}
	}
	
	@EventHandler
	public void InventoryMoveItem(InventoryMoveItemEvent e) {
		if(Limiter.isLimiter(e.getItem())) e.setCancelled(true);
	}
	
}
