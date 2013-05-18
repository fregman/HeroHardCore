package Sheduler;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.herobrine.herohardcore.Listener.HeroListener;
import de.herobrine.herohardcore.Listener.LoginListener;

public class HeroTask extends BukkitRunnable{
	
	Player player;
	HeroListener listener;

	public HeroTask(LoginListener loginListener, Player player) {
		
		this.player = player;
		listener = loginListener;
			
	}

	@Override
	public void run() {
		
		if (player.isOnline()){


		ArrayList<String> userData = new ArrayList<String>();
		
		userData = listener.readPlayerFile(player);
		
		
		long diff = (listener.now() - Long.parseLong(userData.get(0)));
		int diffnew = (int) (Integer.parseInt(userData.get(1)) + diff);
		
		System.out.println("");
		
		userData.set(0, String.valueOf(listener.now()));
		userData.set(1, String.valueOf(diffnew));
		
		listener.savePlayerData(userData, player);
		} else {
			
			this.cancel();
			
		}
		
		
	}

}
