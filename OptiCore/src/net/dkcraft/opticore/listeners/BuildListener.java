package net.dkcraft.opticore.listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

public class BuildListener implements Listener {

	public Main plugin;

	public BuildListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		String world = player.getWorld().getName();
		if (!player.hasPermission("opticore.build.guest")) {
			if (action == Action.LEFT_CLICK_BLOCK ||
					action == Action.RIGHT_CLICK_BLOCK ||
					action == Action.LEFT_CLICK_AIR ||
					action == Action.RIGHT_CLICK_AIR) {
				if (world.toLowerCase().startsWith("guest")) {
					player.sendMessage(Syntax.BUILD_RANK);
					event.setCancelled(true);
				}
			}
		}

		if (!player.hasPermission("opticore.build.member")) {
			if (action == Action.LEFT_CLICK_BLOCK ||
					action == Action.RIGHT_CLICK_BLOCK ||
					action == Action.LEFT_CLICK_AIR ||
					action == Action.RIGHT_CLICK_AIR) {
				if (world.toLowerCase().startsWith("member")) {
					player.sendMessage(Syntax.BUILD_RANK);
					event.setCancelled(true);
				}
			}
		}

		if (!player.hasPermission("opticore.build.recruit")) {
			if (action == Action.LEFT_CLICK_BLOCK ||
					action == Action.RIGHT_CLICK_BLOCK ||
					action == Action.LEFT_CLICK_AIR ||
					action == Action.RIGHT_CLICK_AIR) {
				if (world.toLowerCase().startsWith("recruit")) {
					player.sendMessage(Syntax.BUILD_RANK);
					event.setCancelled(true);
				}
			}
		}

		if (!player.hasPermission("opticore.build.builder")) {
			if (action == Action.LEFT_CLICK_BLOCK ||
					action == Action.RIGHT_CLICK_BLOCK ||
					action == Action.LEFT_CLICK_AIR ||
					action == Action.RIGHT_CLICK_AIR) {
				if (world.toLowerCase().startsWith("builder")) {
					player.sendMessage(Syntax.BUILD_RANK);
					event.setCancelled(true);
				}
			}
		}

		if (!player.hasPermission("opticore.build.crafter")) {
			if (action == Action.LEFT_CLICK_BLOCK ||
					action == Action.RIGHT_CLICK_BLOCK ||
					action == Action.LEFT_CLICK_AIR ||
					action == Action.RIGHT_CLICK_AIR) {
				if (world.toLowerCase().startsWith("crafter")) {
					player.sendMessage(Syntax.BUILD_RANK);
					event.setCancelled(true);
				}
			}
		}

		if (!player.hasPermission("opticore.build.operator")) {
			if (action == Action.LEFT_CLICK_BLOCK ||
					action == Action.RIGHT_CLICK_BLOCK ||
					action == Action.LEFT_CLICK_AIR ||
					action == Action.RIGHT_CLICK_AIR) {
				if (world.toLowerCase().startsWith("operator")) {
					player.sendMessage(Syntax.BUILD_RANK);
					event.setCancelled(true);
				}
			}
		}

		if (!player.hasPermission("opticore.build.admin")) {
			if (action == Action.LEFT_CLICK_BLOCK ||
					action == Action.RIGHT_CLICK_BLOCK ||
					action == Action.LEFT_CLICK_AIR ||
					action == Action.RIGHT_CLICK_AIR) {
				if (world.toLowerCase().startsWith("admin")) {
					player.sendMessage(Syntax.BUILD_RANK);
					event.setCancelled(true);
				}
			}
		}

		if (!player.hasPermission("opticore.build.owner")) {
			if (action == Action.LEFT_CLICK_BLOCK ||
					action == Action.RIGHT_CLICK_BLOCK ||
					action == Action.LEFT_CLICK_AIR ||
					action == Action.RIGHT_CLICK_AIR) {
				if (world.toLowerCase().startsWith("owner")) {
					player.sendMessage(Syntax.BUILD_RANK);
					event.setCancelled(true);
				}
			}
		}

		if (!player.hasPermission("opticore.build.lobby")) {
			if (action == Action.LEFT_CLICK_BLOCK ||
					action == Action.RIGHT_CLICK_BLOCK ||
					action == Action.LEFT_CLICK_AIR ||
					action == Action.RIGHT_CLICK_AIR) {
				if (world.toLowerCase().startsWith("lobby")) {
					player.sendMessage(Syntax.BUILD_GENERAL);
					event.setCancelled(true);
				}
			}
		}

		if (!player.hasPermission("opticore.build.terrain")) {
			if (action == Action.LEFT_CLICK_BLOCK ||
					action == Action.RIGHT_CLICK_BLOCK ||
					action == Action.LEFT_CLICK_AIR ||
					action == Action.RIGHT_CLICK_AIR) {
				if (world.toLowerCase().startsWith("terrain")) {
					player.sendMessage(Syntax.BUILD_GENERAL);
					event.setCancelled(true);
				}
			}
		}

		String playerName = player.getName().toLowerCase();
		String uuid = player.getUniqueId().toString();
		if (this.plugin.getConfig().contains("PersonalWorlds." + world.toLowerCase())) {
			if (world.toLowerCase().equals(playerName) || (player.hasPermission("opticore.personalworld.bypass"))) {
				return;
			}
			List<String> members = this.plugin.getConfig().getStringList("PersonalWorlds." + world.toLowerCase() + ".Members");
			if (!members.contains(uuid)) {
				player.sendMessage(Syntax.BUILD_GENERAL);
				event.setCancelled(true);
			}
		}
	}
}
