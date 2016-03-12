package net.dkcraft.opticore.stats;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.spleef.SpleefMethods;
import net.dkcraft.opticore.stats.handlers.BlockBreakHandler;
import net.dkcraft.opticore.stats.handlers.BlockPlaceHandler;
import net.dkcraft.opticore.stats.handlers.ChatHandler;
import net.dkcraft.opticore.stats.handlers.QuitHandler;
import net.dkcraft.opticore.stats.handlers.SpleefLossHandler;
import net.dkcraft.opticore.stats.handlers.SpleefWinHandler;
import net.dkcraft.opticore.stats.handlers.TimeOnlineHandler;

public class StatsListener implements Listener {

	public Main plugin;
	private SpleefMethods spleef;

	public StatsListener(Main plugin) {
		this.plugin = plugin;
		this.spleef = this.plugin.spleef;
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		String playerName = event.getPlayer().getName();

		if (!plugin.statsBlockBreak.containsKey(playerName)) {
			plugin.statsBlockBreak.put(playerName, 0);
		}

		if (!plugin.statsBlockPlace.containsKey(playerName)) {
			plugin.statsBlockPlace.put(playerName, 0);
		}

		if (!plugin.statsChat.containsKey(playerName)) {
			plugin.statsChat.put(playerName, 0);
		}

		if (!plugin.statsTimeOnline.containsKey(playerName)) {
			plugin.statsTimeOnline.put(playerName, System.currentTimeMillis());
		}

		if (!plugin.spleefWins.containsKey(playerName)) {
			plugin.spleefWins.put(playerName, 0);
		}

		if (!plugin.spleefLosses.containsKey(playerName)) {
			plugin.spleefLosses.put(playerName, 0);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		String playerName = event.getPlayer().getName();
		if (plugin.statsBlockBreak.containsKey(playerName)) {
			plugin.statsBlockBreak.put(playerName, plugin.statsBlockBreak.get(playerName) + 1);
		} else {
			plugin.statsBlockBreak.put(playerName, 1);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		String playerName = event.getPlayer().getName();
		if (plugin.statsBlockPlace.containsKey(playerName)) {
			plugin.statsBlockPlace.put(playerName, plugin.statsBlockPlace.get(playerName) + 1);
		} else {
			plugin.statsBlockPlace.put(playerName, 1);
		}
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		String playerName = event.getPlayer().getName();
		if (plugin.statsChat.containsKey(playerName)) {
			plugin.statsChat.put(playerName, plugin.statsChat.get(playerName) + 1);
		} else {
			plugin.statsChat.put(playerName, 1);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		final String playerName = player.getName();
		final String uuid = event.getPlayer().getUniqueId().toString();

		new BlockBreakHandler(this.plugin, playerName, uuid).runTaskAsynchronously(this.plugin);
		new BlockPlaceHandler(this.plugin, playerName, uuid).runTaskAsynchronously(this.plugin);
		new ChatHandler(this.plugin, playerName, uuid).runTaskAsynchronously(this.plugin);
		new QuitHandler(this.plugin, uuid).runTaskAsynchronously(this.plugin);
		new TimeOnlineHandler(this.plugin, playerName, uuid).runTaskAsynchronously(this.plugin);

		// +1 to spleef_losses
		if (spleef.isInGame(player)) {
			if (plugin.spleefLosses.containsKey(playerName)) {
				plugin.spleefLosses.put(playerName, plugin.spleefLosses.get(playerName) + 1);
			} else {
				plugin.spleefLosses.put(playerName, 1);
			}
		}

		new SpleefWinHandler(this.plugin, playerName, uuid).runTaskAsynchronously(this.plugin);
		new SpleefLossHandler(this.plugin, playerName, uuid).runTaskAsynchronously(this.plugin);
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		final String playerName = event.getPlayer().getName();
		final String uuid = event.getPlayer().getUniqueId().toString();

		new BlockBreakHandler(this.plugin, playerName, uuid).runTaskAsynchronously(this.plugin);
		new BlockPlaceHandler(this.plugin, playerName, uuid).runTaskAsynchronously(this.plugin);
		new ChatHandler(this.plugin, playerName, uuid).runTaskAsynchronously(this.plugin);
		new TimeOnlineHandler(this.plugin, playerName, uuid).runTaskAsynchronously(this.plugin);

		new SpleefWinHandler(this.plugin, playerName, uuid).runTaskAsynchronously(this.plugin);
		new SpleefLossHandler(this.plugin, playerName, uuid).runTaskAsynchronously(this.plugin);
	}
}
