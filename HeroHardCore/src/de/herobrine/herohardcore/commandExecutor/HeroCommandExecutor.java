package de.herobrine.herohardcore.commandExecutor;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.herobrine.herohardcore.HeroHardCore;
import de.herobrine.herohardcore.tools.MySQL;

public class HeroCommandExecutor implements CommandExecutor {

	final HeroHardCore plugin;
	MySQL mysql;

	static String confirmCode;
	static OfflinePlayer resetPlayer;

	Server server;

	public HeroCommandExecutor(HeroHardCore plugin) {

		this.plugin = plugin;
		server = plugin.getServer();
		mysql = new MySQL(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		boolean success = false;

		if (command.getName().equals("reset")) {

			if (sender instanceof Player) {

				if (sender.hasPermission("herohardcore.reset")) {

					Player player = (Player) sender;
					resetPlayer = server.getOfflinePlayer(args[0]);

					if (args.length == 1) {

						if (resetPlayer.hasPlayedBefore()) {

							Random rdm = new Random();

							confirmCode = String
									.valueOf(rdm.nextInt(100) * 159);

							player.sendMessage(plugin.getConfig().getString(
									plugin.language + ".pleaseConfirm")
									+ resetPlayer.getName()
									+ " \n "
									+ plugin.getConfig().getString(
											plugin.language
													+ ".withresetConfirm")
									+ confirmCode);
							success = true;

						} else {

							player.sendMessage(plugin.getConfig().getString(
									plugin.language + ".playerNotOnServer"));
							success = true;
						}

					}

					if (args.length == 2 && args[0].equals("confirm")) {

						if (args[1].equals(confirmCode)) {

							if (mysql.resetPlayer(resetPlayer.getName())) {

								player.sendMessage("Reset of player "
										+ resetPlayer + " successful!");

								confirmCode = "";
								resetPlayer = null;
								success = true;
							}

						} else {

							player.sendMessage(plugin.getConfig().getString(
									plugin.language + ".confirmCodeWrong"));
							success = true;
						}

					}

				}

				sender.sendMessage(plugin.getConfig().getString(
						plugin.language + ".noPermission"));

				success = true;

			}
		}
		
		if (command.getName().equalsIgnoreCase("toplist")){
			
			ArrayList<String> toplist = new ArrayList<String>();
			
			toplist = mysql.getTopList();
			
			int size = toplist.size();
			
			if (size > 5) {size = 5;}
			
			for (int i = 0; i < size; i++){
				
				sender.sendMessage((i+1) + ": " + toplist.get(i));
				
			}
			
			success = true;
		}
			
			

		return success;

	}

}
