package de.herobrine.herohardcore;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import de.herobrine.herohardcore.Listener.DeathListener;
import de.herobrine.herohardcore.Listener.LoginListener;
import de.herobrine.herohardcore.Listener.QuitListener;


public class HeroHardCore extends JavaPlugin {
	

	@Override
    public void onEnable(){	
	
		checkFolder();
		
		getLogger().info("starting HeroHardCore");
		new LoginListener(this);
		new QuitListener(this);
		new DeathListener(this);
       
    }
 


	@Override
    public void onDisable() {
		
		getLogger().info("Stopping HeroHardCore");

    }	

	

	public void checkFolder() {
		
    	File dir = new File(this.getDataFolder().getAbsolutePath());
	
    	if (dir.mkdir()) {
    				
    		getLogger().info("Pluginfolder created");
    			
	    } else {
	    
			getLogger().info("Creation of pluginfolder failed");
    	}
    		
	}
	
}

