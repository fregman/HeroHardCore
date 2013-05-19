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
		
		String tag = plugin.getConfig().getString(language + ".days");
		String stunde = plugin.getConfig().getString(language + ".hours");
		String minute = plugin.getConfig().getString(language + ".minutes");
		String sekunde = plugin.getConfig().getString(language + ".seconds");
		
		if (day == 1) tag = plugin.getConfig().getString(language + ".day");
		if (hour == 1) stunde = plugin.getConfig().getString(language + ".hour");
		if (min == 1) minute = plugin.getConfig().getString(language + ".minute");
		if (sec == 1) sekunde = plugin.getConfig().getString(language + ".second");
		
		kickMsg.append(plugin.getConfig().getString(language + ".playerDies") + "\n")
		       .append(plugin.getConfig().getString(language + ".playTime"))
			   .append(day).append(" ").append(tag).append(", ")
			   .append(hour).append(" ").append(stunde).append(", ")
			   .append(min).append(" ").append(minute).append(", ")
			   .append(sec).append(" ").append(sekunde)
			   .append(" \n" + plugin.getConfig().getString(language + ".luck"));

		player.kickPlayer(kickMsg.toString());
		
		
		
		if (plugin.getConfig().getBoolean("banOnDeath")) player.setBanned(true);

		
	}

}
