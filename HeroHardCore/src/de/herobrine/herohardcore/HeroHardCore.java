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
		

		getLogger().info("HeroHardCore gestartet");
		new LoginListener(this);
		new QuitListener(this);
		new DeathListener(this);
       
    }
 


	@Override
    public void onDisable() {
		getLogger().info("HeroHardCore gestoppt");

    }	
    
    public void checkFolder() {
		
    	

    	
    	File dir = new File(this.getDataFolder().getAbsolutePath());

    	
    	if (dir.exists()){
    		
    		System.out.println("Verzeichnis vorhanden");
    		System.out.println(dir);
    		
    	} else {
    		
    		if (dir.mkdir()) {
    			
    			
    			System.out.println("Verzeichnis erstellt");
    			
    		} else {
    			System.out.println(dir);
    			System.out.println("Verzeichnis konnte nicht erstellt werden");
    		}
    		
    	}
		
	}
    
    

}

