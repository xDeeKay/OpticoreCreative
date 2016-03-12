package net.dkcraft.opticore.announcement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.dkcraft.opticore.Main;

public class AnnouncementMethods {

	public Main plugin;

	private List<String> messages = new ArrayList<String>();
	private int interval;
	
	private BukkitTask task;

	public AnnouncementMethods(Main plugin) {
		this.plugin = plugin;
	}
	
	public void loadMessages() {
		if (!plugin.getConfig().getStringList("announcement.messages").isEmpty()) {
			this.setMessages(plugin.getConfig().getStringList("announcement.messages"));
			this.setInterval(plugin.getConfig().getInt("announcement.interval"));
			startAnnouncement();
		}
	}
	
	public void startAnnouncement() {
		this.task = new BukkitRunnable() {
			public void run() {
				announceMessage();
			}
		}.runTaskTimer(plugin, 20 * getInterval(), 20 * getInterval());
	}

	public void stopAnnouncement() {
		if (this.task != null) {
			this.task.cancel();
			this.task = null;
		}
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	public List<String> getMessages() {
		return this.messages;
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getInterval() {
		return this.interval;
	}

	public void announceMessage() {
		String message = messages.get(new Random().nextInt(messages.size()));
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!plugin.toggleNotifications.contains(player.getName())) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
			}
		}
	}
}
