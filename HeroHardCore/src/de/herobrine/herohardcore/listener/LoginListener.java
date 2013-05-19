package de.herobrine.herohardcore.listener;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import de.herobrine.herohardcore.HeroHardCore;
import de.herobrine.herohardcore.timer.HeroTask;

public class LoginListener extends HeroListener{
	
	public LoginListener(HeroHardCore plugin) {
		super(plugin);
		
	}

	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		
		Player player = event.getPlayer();
		ArrayList<String> userData = new ArrayList<String>();
		File playerFile = new File(folder + "/" + player.getName());

		if(!player.isBanned()){
			
			if (playerFile.exists()) {

				userData = fileManager.readPlayerFile(player);
				
				userData.set(0, String.valueOf(now()));
	
				fileManager.savePlayerData(userData, player);

			} else {
				
				userData.add(0, String.valueOf(now()));
				userData.add(1, "0");
				
				fileManager.savePlayerData(userData, player);
				
			}
			
			
			new HeroTask(this, fileManager, player).runTaskTimer(plugin, plugin.intervall, plugin.intervall);
						
		}		
	   
	}
	
}
