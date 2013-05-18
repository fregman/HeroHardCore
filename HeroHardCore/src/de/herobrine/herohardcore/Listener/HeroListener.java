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
	    this.folder = plugin.getDataFolder().getAbsolutePath();
		   
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

	}
	
	
	public long now(){
		
		return System.currentTimeMillis();
		
	}

	
	public ArrayList<String> readPlayerFile(Player player) {
		
		ArrayList<String> userData = new ArrayList<String>();
		File playerFile = new File(folder + "/" + player.getName());
		
		String line = "";

		try {
			
			BufferedReader rd = new BufferedReader(new FileReader(playerFile));
			
			line = rd.readLine();
			
			while(line != null) {
			
				userData.add(line);
				line = rd.readLine();
				
			}

			rd.close();
			
		} catch (FileNotFoundException e) {

			System.out.println("File not Found in readPlayerFile()");
		} catch (IOException e) {
			
			System.out.println("Error in readPlayerFile():");
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
				
				System.out.println("Error in savePlayerData() while createNewFile:");
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
			
			System.out.println("Error in savePlayerData() while writing Data to playerFile");
			e.printStackTrace();
		}
		
	}

}
