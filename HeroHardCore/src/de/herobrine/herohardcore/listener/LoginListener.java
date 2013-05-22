package de.herobrine.herohardcore.listener;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import de.herobrine.herohardcore.HeroHardCore;
import de.herobrine.herohardcore.timer.HeroTask;
import de.herobrine.herohardcore.tools.MySQL;

public class LoginListener extends HeroListener {

	public LoginListener(HeroHardCore plugin) {
		super(plugin);

	}

	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {

		mysql = new MySQL(plugin);

		Player player = event.getPlayer();
		String playerName = player.getName();

		if (!player.isBanned()) {

			if (mysql.checkPlayer(playerName)) {

				mysql.insertTime(playerName);

			} else {

				mysql.createPlayer(playerName);

				setSpawnLocation(player);
			}

			new HeroTask(mysql, player).runTaskTimer(plugin, plugin.intervall,
					plugin.intervall);

		}

	}

	private void setSpawnLocation(Player player) {

		int maxy = 100;
		int y;
		Random rdm = new Random();
		int randomNumber = rdm.nextInt(50) + 250;
		int playerCount = player.getServer().getOfflinePlayers().length;
		World world = player.getWorld();

		if (playerCount != 0) {

			playerCount = playerCount * randomNumber;

		} else {

			playerCount = randomNumber;
		}

		while (player.getWorld().getBiome(playerCount, playerCount).toString()
				.equalsIgnoreCase("ocean")) {

			playerCount += 50;

		}

		y = checkTop(player, playerCount, maxy);

		while (world.getBlockAt(playerCount, y, playerCount).getType()
				.equals(Material.WATER)
				|| world.getBlockAt(playerCount, y, playerCount).getType()
						.equals(Material.STATIONARY_WATER)
				|| world.getBlockAt(playerCount, y, playerCount).getType()
						.equals(Material.LAVA)
				|| world.getBlockAt(playerCount, y, playerCount).getType()
						.equals(Material.STATIONARY_LAVA)
				|| world.getBlockAt(playerCount, y, playerCount).getType()
						.equals(Material.ICE)
				|| world.getBlockAt(playerCount, y, playerCount).getType()
						.equals(Material.LEAVES)
				|| world.getBlockAt(playerCount, y, playerCount).getType()
						.equals(Material.VINE)
				|| !world.getBlockAt(playerCount, y + 1, playerCount).getType()
						.equals(Material.AIR)
				|| !world.getBlockAt(playerCount, y + 2, playerCount).getType()
						.equals(Material.AIR)
				|| !world.getBlockAt(playerCount, y + 3, playerCount).getType()
						.equals(Material.AIR)) {

			playerCount += 10;

			y = checkTop(player, playerCount, maxy);
		}

		player.teleport(new Location(world, playerCount, (y + 3), playerCount));
		world.setTime(0);

	}

	private int checkTop(Player player, int playerCount, int y) {

		while (player.getWorld().getBlockAt(playerCount, y, playerCount)
				.getType().equals(Material.AIR)) {

			y--;

		}

		return y;

	}
}
