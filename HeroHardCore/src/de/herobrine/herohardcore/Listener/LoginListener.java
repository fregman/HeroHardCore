package de.herobrine.herohardcore.Listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

import de.herobrine.herohardcore.HeroHardCore;

public class LoginListener extends HeroListener{

	public LoginListener(HeroHardCore plugin) {
		super(plugin);
	}

	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		
		Player player = event.getPlayer();
		System.out.println("Playername: " + player.getName());
		
		ArrayList<String> userData = new ArrayList<String>();
		
		File playerFile = new File(folder + "/" + player.getName());
		
		

		
		if(!player.isBanned()){
			
			
			

			
			if (playerFile.exists()) {

				userData = readPlayerFile(player);
				userData.set(0, String.valueOf(now()));

				try {
				
					FileWriter write = new FileWriter(playerFile);
					
				for (int i = 0;i<userData.size();i++){
					
					write.write(userData.get(i));
					write.write(System.getProperty("line.separator"));
					System.out.println(userData.get(i));
					
						
				}
				
				write.close();
				
				} catch (IOException e) {
			
					e.printStackTrace();
				}
			
				
			} else {
				
				
				try {
					System.out.println("Writing Playerfile");
					playerFile.createNewFile();
					FileWriter write = new FileWriter(playerFile);
					
					
					
					write.write(String.valueOf(now()));
					System.out.println(now());
					write.write(System.getProperty("line.separator"));
					write.write("0");

					write.close();

				} catch (IOException e) {

					e.printStackTrace();
				}
				
			}
						
		}		
	   
	}
	
}
