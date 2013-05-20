package de.herobrine.herohardcore.listener;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.herobrine.herohardcore.HeroHardCore;

public class DeathListener extends HeroListener{
	
	String language;

	public DeathListener(HeroHardCore plugin) {
		super(plugin);
		language = plugin.language;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {

		Player player = event.getEntity();
		StringBuilder kickMsg = new StringBuilder();
		ArrayList<String> userData = new ArrayList<String>();
		userData = fileManager.readPlayerFile(player);
		
		long diff = (now() - Long.parseLong(userData.get(0)));
		int diffnew = (int) (Integer.parseInt(userData.get(1)) + diff);		
		int playTime = diffnew;
		
		int sec 	= playTime/1000 % 60;
		int min 	= playTime/1000/60 % 60;
		int hour 	= playTime/1000/60/60 % 24;
		int day		= playTime/1000/60/60/24;
		
		String dayString = getConfigString("days");
		String hourString = getConfigString("hours");
		String minString = getConfigString("minutes");
		String secString = getConfigString("seconds");
		
		if (day == 1) dayString = getConfigString("days");
		if (hour == 1) hourString = getConfigString("hour");
		if (min == 1) minString = getConfigString("minute");
		if (sec == 1) secString = getConfigString("second");
		
		kickMsg.append(getConfigString("playerDies") + "\n")
		       .append(getConfigString("playTime"))
			   .append(day).append(" ").append(dayString).append(", ")
			   .append(hour).append(" ").append(hourString).append(", ")
			   .append(min).append(" ").append(minString).append(", ")
			   .append(sec).append(" ").append(secString)
			   .append(" \n" + getConfigString("luck"));

		player.kickPlayer(kickMsg.toString());
		
		
		
		if (plugin.getConfig().getBoolean("banOnDeath")) player.setBanned(true);

		
	}



}
