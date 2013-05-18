package de.herobrine.herohardcore.Listener;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitTask;

import Sheduler.HeroTask;
import de.herobrine.herohardcore.HeroHardCore;

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

				userData = readPlayerFile(player);
				userData.set(0, String.valueOf(now()));
				
				System.out.println("File vorhanden, Daten:");
				for (int i = 0;i<userData.size();i++) System.out.println(userData.get(i));
				
				savePlayerData(userData, player);


			} else {
				
				userData.add(0, String.valueOf(now()));
				userData.add(1, "0");
				
				System.out.println("File nicht vorhanden, Daten:");
				for (int i = 0;i<userData.size();i++) System.out.println(userData.get(i));
				
				savePlayerData(userData, player);
				
			}
			
			BukkitTask task = new HeroTask(this, player).runTaskTimer(plugin, 200, 200);
						
		}		
	   
	}
	
}
