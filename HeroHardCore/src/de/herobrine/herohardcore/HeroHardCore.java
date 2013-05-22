package de.herobrine.herohardcore;

import org.bukkit.plugin.java.JavaPlugin;

import de.herobrine.herohardcore.commandExecutor.HeroCommandExecutor;
import de.herobrine.herohardcore.listener.DeathListener;
import de.herobrine.herohardcore.listener.LoginListener;
import de.herobrine.herohardcore.listener.QuitListener;
import de.herobrine.herohardcore.tools.MySQL;

public class HeroHardCore extends JavaPlugin {

	public String language;
	public int intervall;

	@Override
	public void onEnable() {

		if (getConfig().getBoolean("enableMySQL")) {

			MySQL mysql = new MySQL(this);

			if (!mysql.checkTable()) {

				mysql.createTable();

				if (mysql.checkTable()) {

					getLogger().info("MySQL table created");

				} else {

					getLogger().warning("Was not able to create MySQL table");

				}
			}

		}

		this.language = getConfig().getString("language");
		this.intervall = getConfig().getInt("saveIntervallInSeconds") * 20;

		new LoginListener(this);
		new QuitListener(this);
		new DeathListener(this);

		getCommand("reset").setExecutor(new HeroCommandExecutor(this));

	}

	@Override
	public void onDisable() {

		getLogger().info("Stopping HeroHardCore");

	}

}
