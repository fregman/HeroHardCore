package de.herobrine.herohardcore.Listener;

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
		
	
		long diff = (now() - Long.parseLong(userData.get(0)));
		int diffnew = (int) (Integer.parseInt(userData.get(1)) + diff);
		
		userData.set(1, String.valueOf(diffnew));
		
		savePlayerData(userData, player);
		
	}
	
	
}
