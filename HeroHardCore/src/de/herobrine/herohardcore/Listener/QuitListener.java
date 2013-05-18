package de.herobrine.herohardcore.Listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import de.herobrine.herohardcore.HeroHardCore;

public class QuitListener extends HeroListener{

	public QuitListener(HeroHardCore plugin) {
		super(plugin);
	}

	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event){
		
		Player player = event.getPlayer();

		ArrayList<String> userData = new ArrayList<String>();

		
		userData = readPlayerFile(player);
		System.out.println("[DEBUG] ArrayList Größe in QuitEvent:" + userData.size());
		
		
		//try {
		long diff = (now() - Long.parseLong(userData.get(0)));
		System.out.println("differenz " + diff);
		int diffnew = (int) (Integer.parseInt(userData.get(1)) + diff);
		System.out.println("differenz neu " + diffnew);

		
		userData.set(1, String.valueOf(diffnew));
		try {
			
			File playerFile = new File(folder + "/" + player.getName());
			FileWriter write = new FileWriter(playerFile);
			
			for (int i = 0;i<userData.size();i++){
				
				write.write(userData.get(i));
				write.write(System.getProperty("line.separator"));
							
			}
			write.close();
			} catch (IOException e) {
	
				e.printStackTrace();
			}
		

		
	}
	
	
}
