package de.herobrine.herohardcore.commandExecutor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.herobrine.herohardcore.HeroHardCore;

public class HeroCommandExecutor implements CommandExecutor{
	
	final HeroHardCore plugin;
	
	static String confirmCode;
	static String resetPlayer;
	Server server;

	public HeroCommandExecutor(HeroHardCore plugin) {
		
		this.plugin = plugin;
		server = plugin.getServer();
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args){
		
		
		boolean success = false;
		
			if (command.getName().equals("reset")){
				
				
		
				if (sender instanceof Player) {
					
					if (sender.hasPermission("herohardcore.reset")){
					
						Player player = (Player) sender;
						
						if (args.length == 1) {
							
							if(checkIfPlayerWasOn(args[0])) {
							
								Random rdm = new Random();
								
								confirmCode = String.valueOf(rdm.nextInt(100)*159);
								resetPlayer = args[0];
								player.sendMessage(plugin.getConfig().getString(plugin.language + ".pleaseConfirm") + resetPlayer + " \n " + plugin.getConfig().getString(plugin.language + ".withresetConfirm") + confirmCode);
								success = true;
								
							} else {
								
								player.sendMessage(plugin.getConfig().getString(plugin.language + ".playerNotOnServer"));
								success = true;
							}
							
						} 
						
						
						if (args.length == 2 && args[0].equals("confirm")) {
							
							if (args[1].equals(confirmCode)){
								
								if (resetPlayer(resetPlayer)) {
									
									player.sendMessage("Reset of player " + resetPlayer + " successful!");
									confirmCode = "";
									resetPlayer = "";
									success = true;
								} 
								
							} else {
							
								player.sendMessage(plugin.getConfig().getString(plugin.language + ".confirmCodeWrong"));
								success = true;
							}
							 
						}
						
					}
					
					sender.sendMessage(plugin.getConfig().getString(plugin.language + ".noPermission"));
					
					success = true;
					
				}
		}

		return success;
			
	}

	private boolean resetPlayer(String playername) {
		
		boolean success = false;
		File playerFile = new File(plugin.getDataFolder().getAbsolutePath() + "/" + playername);
		
		if(playerFile.delete()) {
			
			try {
				if(playerFile.createNewFile()) {
					
					FileWriter write = new FileWriter(playerFile);
					
					write.write(String.valueOf(System.currentTimeMillis()));
					write.write(System.getProperty("line.separator"));
					write.write("0");
					
					write.close();
				}
				
					success = true;
			} catch (IOException e) {
				
				System.out.println("Error in resetPlayer while file creation/deletion!");
				e.printStackTrace();
				
			}
			
		}
		
		return success;
		
	}
	

	private boolean checkIfPlayerWasOn(String playername) {
		
		OfflinePlayer[] offlinePlayers = server.getOfflinePlayers();
		boolean found = false;
		
		for (OfflinePlayer players: offlinePlayers) {
			
			if (players.getName().equalsIgnoreCase(playername)) found = true;
			
		}
		
		return found;	
		
	}



}
