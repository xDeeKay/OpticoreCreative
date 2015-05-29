package net.dkcraft.opticore.listeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.dkcraft.opticore.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerIPConfig implements Listener {

	public Main plugin;

	public PlayerIPConfig(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		String IP = player.getAddress().getHostString();
		File userfile = new File(plugin.getDataFolder() + File.separator + "ip" + File.separator + uuid.toString() + ".yml");
		FileConfiguration userconfig = null;
		if (!userfile.exists()) {
			try {
				userfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		userconfig = YamlConfiguration.loadConfiguration(userfile);
		if (!userconfig.contains("CurrentIP")) {
			userconfig.set("CurrentIP", IP);
		}
		if (userconfig.getString("CurrentIP") != IP) {
			userconfig.set("CurrentIP", IP);
		}
		if (!userconfig.contains("IP")) {
			userconfig.set("IP", new ArrayList<String>());
		}
		List<String> ipList = userconfig.getStringList("IP");
		if (!ipList.contains(IP)) {
			ipList.add(IP);
			userconfig.set("IP", ipList);
			try {
				userconfig.save(userfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}