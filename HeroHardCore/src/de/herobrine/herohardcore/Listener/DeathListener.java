package de.herobrine.herohardcore.Listener;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.herobrine.herohardcore.HeroHardCore;

public class DeathListener extends HeroListener{

	public DeathListener(HeroHardCore plugin) {
		super(plugin);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {

		Player player = event.getEntity();
		StringBuilder kickMsg = new StringBuilder();
		ArrayList<String> userData = new ArrayList<String>();
		userData = readPlayerFile(player);
		
		long diff = (now() - Long.parseLong(userData.get(0)));
		int diffnew = (int) (Integer.parseInt(userData.get(1)) + diff);		
		int playTime = diffnew;
		
		int sec 	= playTime/1000 % 60;
		int min 	= playTime/1000/60 % 60;
		int hour 	= playTime/1000/60/60 % 24;
		int day		= playTime/1000/60/60/24;
		
		String tag = "Tage";
		String stunde = "Stunden";
		String minute = "Minuten";
		String sekunde = "Sekunden";
		
		if (day == 1) tag = "Tag";
		if (hour == 1) stunde = "Stunde";
		if (min == 1) minute = "Minute";
		if (sec == 1) sekunde = "Sekunde";
		
		kickMsg.append("Du bist gestorben.\n").append("Du hast ")
			   .append(day).append(" ").append(tag).append(", ")
			   .append(hour).append(" ").append(stunde).append(", ")
			   .append(min).append(" ").append(minute).append(", ")
			   .append(sec).append(" ").append(sekunde).append(" durchgehalten\n")
			   .append("Viel Glück beim nächsten Mal!");

		player.kickPlayer(kickMsg.toString());
		//player.setBanned(true);
		
	}

}
