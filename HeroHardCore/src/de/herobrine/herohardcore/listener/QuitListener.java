package de.herobrine.herohardcore.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import de.herobrine.herohardcore.HeroHardCore;
import de.herobrine.herohardcore.tools.MySQL;

public class QuitListener extends HeroListener {

	public QuitListener(HeroHardCore plugin) {
		super(plugin);
		mysql = new MySQL(plugin);

	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {

		Player player = event.getPlayer();

		mysql.updatePlayer(player.getName());

	}

}
