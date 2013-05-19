package de.herobrine.herohardcore.timer;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import de.herobrine.herohardcore.listener.LoginListener;
import de.herobrine.herohardcore.tools.FileManager;

public class HeroTask extends BukkitRunnable{
	
	Player player;
	FileManager fileManager;
	LoginListener loginListener;

	public HeroTask(LoginListener loginListener, FileManager fileManager, Player player) {
		
		this.player = player;
		this.fileManager = fileManager;
		this.loginListener = loginListener;
			
	}

	@Override
	public void run() {
		
		if (player.isOnline()){

		ArrayList<String> userData = new ArrayList<String>();
		
		userData = fileManager.readPlayerFile(player);
				
		long diff = (loginListener.now() - Long.parseLong(userData.get(0)));
		int diffnew = (int) (Integer.parseInt(userData.get(1)) + diff);
		
		userData.set(0, String.valueOf(loginListener.now()));
		userData.set(1, String.valueOf(diffnew));
		
		fileManager.savePlayerData(userData, player);
		
		} else {
			
			this.cancel();
			
		}
		
		
	}



}
