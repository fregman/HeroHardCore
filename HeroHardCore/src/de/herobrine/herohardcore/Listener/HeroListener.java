package de.herobrine.herohardcore.Listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.herobrine.herohardcore.HeroHardCore;

public class HeroListener implements Listener{
	
	 final String folder;
	 final HeroHardCore plugin;
	
	public HeroListener(HeroHardCore plugin) {
		
		this.plugin = plugin;
		   
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	    folder = plugin.getDataFolder().getAbsolutePath();
	    
	        
	}
	
	
	public long now(){
		
		return System.currentTimeMillis();
		
	}

	public ArrayList<String> readPlayerFile(Player player) {

		
		ArrayList<String> userData = new ArrayList<String>();
		File playerFile = new File(folder + "/" + player.getName());
		
		String zeile = "";

		try {
			
			BufferedReader rd = new BufferedReader(new FileReader(playerFile));
			
			zeile = rd.readLine();
			
			while(zeile != null) {
			
				userData.add(zeile);
				zeile = rd.readLine();
				
			}

			rd.close();
			
		} catch (NumberFormatException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userData;
		
	}
	
	public void savePlayerData(ArrayList<String> userData, Player player){
		
		File playerFile = new File(folder + "/" + player.getName());
		
		if (!playerFile.exists()) {
			
			try {
				playerFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
				
		try {
			
			FileWriter write = new FileWriter(playerFile);
			
		for (int i = 0;i<userData.size();i++){
			
			write.write(userData.get(i));
			write.write(System.getProperty("line.separator"));
					
		}
		
		write.close();
		
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		System.out.println("Daten gespeichert");
		
		for (int i = 0;i<userData.size();i++) System.out.println(userData.get(i));
		
		
	}

	
	
	

}
