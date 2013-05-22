package de.herobrine.herohardcore.timer;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import de.herobrine.herohardcore.tools.MySQL;

public class HeroTask extends BukkitRunnable {

	Player player;
	private MySQL mysql;

	public HeroTask(MySQL mysql, Player player) {

		this.player = player;
		this.mysql = mysql;

	}

	@Override
	public void run() {

		if (player.isOnline()) {

			mysql.updatePlayer(player.getName());

		} else {

			this.cancel();

		}

	}

}
