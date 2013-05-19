package de.herobrine.herohardcore.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.herobrine.herohardcore.HeroHardCore;

public class FileManager {
	
	final String folder;
	final HeroHardCore plugin;
	
	
	public FileManager(HeroHardCore plugin) {
		
		this.plugin = plugin;
		this.folder = plugin.getDataFolder().getAbsolutePath();
	
	}
	
	public boolean checkFiles() {
		
		boolean success = false;
		
		File config = new File(folder, "config.yml");
	
	    if (!config.exists()) {
	        plugin.saveDefaultConfig();
	        success = true;
	        }
		return success;
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

			plugin.getLogger().warning("File not Found in readPlayerFile:");
		} catch (IOException e) {
			
			plugin.getLogger().warning("Error in readPlayerFile");
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
				
				plugin.getLogger().warning("Error while creating new Playerfile in savePlayerData:");
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
			plugin.getLogger().warning("Error in savePlayerData() while writing Data to playerFile");
			e.printStackTrace();
		}
		
	}

}
