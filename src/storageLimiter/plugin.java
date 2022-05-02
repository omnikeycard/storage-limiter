package storageLimiter;

import org.bukkit.plugin.java.JavaPlugin;

public class plugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventsListener(), this);
	}
	
}
