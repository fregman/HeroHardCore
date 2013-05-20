package de.herobrine.herohardcore;


import org.bukkit.plugin.java.JavaPlugin;

import de.herobrine.herohardcore.commandExecutor.HeroCommandExecutor;
import de.herobrine.herohardcore.listener.DeathListener;
import de.herobrine.herohardcore.listener.LoginListener;
import de.herobrine.herohardcore.listener.QuitListener; 
import de.herobrine.herohardcore.tools.FileManager;


public class HeroHardCore extends JavaPlugin {
	
	public String language;
	public int intervall;
	public FileManager fileManager;
	public String folder;
	

	@Override
    public void onEnable(){	

		fileManager = new FileManager(this);
		fileManager.checkFiles();
		
	    this.language = getConfig().getString("language");
	    this.intervall = getConfig().getInt("saveIntervallInSeconds")*20;
		this.folder = getDataFolder().getAbsolutePath();
		
		new LoginListener(this);
		new QuitListener(this);
		new DeathListener(this);

		getCommand("reset").setExecutor(new HeroCommandExecutor(this));
		
    }
 
	@Override
    public void onDisable() {
		
		getLogger().info("Stopping HeroHardCore");

    }	
	
}

