package de.herobrine.herohardcore.listener;

import org.bukkit.event.Listener;

import de.herobrine.herohardcore.HeroHardCore;
import de.herobrine.herohardcore.tools.FileManager;

public class HeroListener implements Listener{
	
	final String folder;
	final HeroHardCore plugin;
	final FileManager fileManager;
	
	
	public HeroListener(HeroHardCore plugin) {
		
		this.plugin = plugin;
	    this.folder = plugin.folder;
	    this.fileManager = new FileManager(plugin);

		   
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

	}
	
	
	public long now(){
		
		return System.currentTimeMillis();
		
	}

	
	public String getConfigString(String string) {
		
		String configString = plugin.getConfig().getString(plugin.language + "." + string);
		
		return configString;
		
	}
	
	
	
}
