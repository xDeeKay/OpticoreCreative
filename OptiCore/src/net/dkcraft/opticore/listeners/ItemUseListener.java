package net.dkcraft.opticore.listeners;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemUseListener implements Listener {

	public Main plugin;

	public ItemUseListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Material hand = player.getItemInHand().getType();
		Action action = event.getAction();
		Block block = event.getClickedBlock();
		
		if (hand.equals(Material.FLINT_AND_STEEL) || (hand.equals(Material.FIREBALL))) {
			if (!player.hasPermission("opticore.use.fire")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hand.equals(Material.WATER) || (hand.equals(Material.WATER_BUCKET))) {
			if (!player.hasPermission("opticore.use.water")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hand.equals(Material.LAVA) || (hand.equals(Material.LAVA_BUCKET))) {
			if (!player.hasPermission("opticore.use.lava")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hand.equals(Material.REDSTONE_TORCH_ON)) {
			if (!player.hasPermission("opticore.use.redstonetorch")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hand.equals(Material.REDSTONE)) {
			if (!player.hasPermission("opticore.use.redstone")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hand.equals(Material.BOAT)
				|| hand.equals(Material.MINECART)
				|| hand.equals(Material.STORAGE_MINECART)
				|| hand.equals(Material.HOPPER_MINECART)
				|| hand.equals(Material.POWERED_MINECART)
				|| hand.equals(Material.EXPLOSIVE_MINECART)
				|| hand.equals(Material.HOPPER_MINECART)) {
			if (!player.hasPermission("opticore.use.vehicle")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}
		
		if (hand.equals(Material.INK_SACK) && player.getItemInHand().getDurability() == 15) {
			if (!player.hasPermission("opticore.use.bonemeal")) {
				if (action == Action.RIGHT_CLICK_BLOCK) {
					if (block.getType() == Material.SAPLING) {
						player.sendMessage(Syntax.ITEM_USE);
						event.setCancelled(true);
					}
				}
			}
		}
		
		if (hand.equals(Material.WRITTEN_BOOK)) {
			if (!player.hasPermission("opticore.use.book")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}
	}
}