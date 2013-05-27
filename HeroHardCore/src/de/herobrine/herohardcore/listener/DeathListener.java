package de.herobrine.herohardcore.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.herobrine.herohardcore.HeroHardCore;
import de.herobrine.herohardcore.tools.MySQL;

public class DeathListener extends HeroListener {

	String language;

	public DeathListener(HeroHardCore plugin) {
		super(plugin);
		language = plugin.language;
		mysql = new MySQL(plugin);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {

		Player player = event.getEntity();
		StringBuilder kickMsg = new StringBuilder();

		int playTime = mysql.getPlayTime(player.getName());

		int sec = playTime % 60;
		int min = playTime / 60 % 60;
		int hour = playTime / 60 / 60 % 24;
		int day = playTime / 60 / 60 / 24;

		String dayString = getConfigString("days");
		String hourString = getConfigString("hours");
		String minString = getConfigString("minutes");
		String secString = getConfigString("seconds");

		if (day == 1)
			dayString = getConfigString("days");
		if (hour == 1)
			hourString = getConfigString("hour");
		if (min == 1)
			minString = getConfigString("minute");
		if (sec == 1)
			secString = getConfigString("second");

		if (plugin.getConfig().getBoolean("banOnDeath")) {
			if (plugin.getConfig().getBoolean("secondChance")) {
				if (!(playTime < plugin.getConfig().getInt("secondChanceTime"))) {

					kickMsg.append(getConfigString("playerDies") + "\n")
							.append(getConfigString("playTime")).append(day)
							.append(" ").append(dayString).append(", ").append(hour)
							.append(" ").append(hourString)
							.append(", ").append(min)
							.append(" ").append(minString)
							.append(", ").append(sec)
							.append(" ").append(secString)
							.append(" \n" + getConfigString("luck"));

					player.setBanned(true);
					mysql.setDeathtrue(player.getName());

				} else {

					kickMsg.append(getConfigString("playerDies") + "\n")
						.append(getConfigString("playTime")).append(day)
						.append(" ").append(dayString).append(", ").append(hour)
						.append(" ").append(hourString)
						.append(", ").append(min)
						.append(" ").append(minString)
						.append(", ").append(sec)
						.append(" ").append(secString)
						.append(" \n" + getConfigString("secondChance"));

				}

			}
		}

		player.kickPlayer(kickMsg.toString());
		
	}

}
