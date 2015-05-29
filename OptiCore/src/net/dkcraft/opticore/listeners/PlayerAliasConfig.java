package net.dkcraft.opticore.listeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.dkcraft.opticore.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerAliasConfig implements Listener {

	public Main plugin;

	public PlayerAliasConfig(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		String IP = player.getAddress().getHostString();
		File userfile = new File(plugin.getDataFolder() + File.separator + "alias" + File.separator + IP + ".yml");
		FileConfiguration userconfig = null;
		if (!userfile.exists()) {
			try {
				userfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		userconfig = YamlConfiguration.loadConfiguration(userfile);
		if (!userconfig.contains("Usernames")) {
			userconfig.set("Usernames", new ArrayList<String>());
		}
		List<String> aliasList = userconfig.getStringList("Usernames");
		if (!aliasList.contains(playerName)) {
			aliasList.add(playerName);
			userconfig.set("Usernames", aliasList);
			try {
				userconfig.save(userfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}