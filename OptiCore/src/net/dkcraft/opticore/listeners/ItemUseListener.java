package net.dkcraft.opticore.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

public class ItemUseListener implements Listener {

	public Main plugin;

	public ItemUseListener(Main plugin) {
		this.plugin = plugin;
	}
	
	public boolean hands(Player player, Material material) {
		
		Material handMain = player.getInventory().getItemInMainHand().getType();
		Material handOff = player.getInventory().getItemInOffHand().getType();
		
		if (handMain.equals(material) || handOff.equals(material)) {
			return true;
		} else {
			return false;
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		Block block = event.getClickedBlock();
		
		if (hands(player, Material.FLINT_AND_STEEL) || (hands(player, Material.FIREBALL))) {
			if (!player.hasPermission("opticore.use.fire")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hands(player, Material.WATER) || (hands(player, Material.WATER_BUCKET))) {
			if (!player.hasPermission("opticore.use.water")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hands(player, Material.LAVA) || (hands(player, Material.LAVA_BUCKET))) {
			if (!player.hasPermission("opticore.use.lava")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hands(player, Material.REDSTONE_TORCH_ON)) {
			if (!player.hasPermission("opticore.use.redstonetorch")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hands(player, Material.REDSTONE)) {
			if (!player.hasPermission("opticore.use.redstone")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hands(player, Material.BOAT)
				|| hands(player, Material.MINECART)
				|| hands(player, Material.STORAGE_MINECART)
				|| hands(player, Material.HOPPER_MINECART)
				|| hands(player, Material.POWERED_MINECART)
				|| hands(player, Material.EXPLOSIVE_MINECART)
				|| hands(player, Material.HOPPER_MINECART)) {
			if (!player.hasPermission("opticore.use.vehicle")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hands(player, Material.INK_SACK) && player.getInventory().getItemInMainHand().getDurability() == 15
				|| player.getInventory().getItemInOffHand().getDurability() == 15) {
			if (!player.hasPermission("opticore.use.bonemeal")) {
				if (action == Action.RIGHT_CLICK_BLOCK) {
					if (block.getType() == Material.SAPLING) {
						player.sendMessage(Syntax.ITEM_USE);
						event.setCancelled(true);
					}
				}
			}
		}

		if (hands(player, Material.BOOK_AND_QUILL)) {
			if (!player.hasPermission("opticore.use.book")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(ChatColor.RED + "Warning: signing this book will prevent you from opening it.");
					player.sendMessage(ChatColor.RED + "If you accidently sign it, use /convertbook to convert it back.");
				}
			}
		}

		if (hands(player, Material.WRITTEN_BOOK)) {
			if (!player.hasPermission("opticore.use.book")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hands(player, Material.COMMAND) || hands(player, Material.COMMAND_MINECART)) {
			if (!player.hasPermission("opticore.use.commandblock")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hands(player, Material.BARRIER)) {
			if (!player.hasPermission("opticore.use.barrier")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}

		if (hands(player, Material.DRAGON_EGG)) {
			if (!player.hasPermission("opticore.use.dragonegg")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}
		
		if (hands(player, Material.DIODE)) {
			if (!player.hasPermission("opticore.use.repeater")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}
		
		if (hands(player, Material.REDSTONE_COMPARATOR)) {
			if (!player.hasPermission("opticore.use.comparator")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}
		
		if (hands(player, Material.PISTON_BASE) || hands(player, Material.PISTON_STICKY_BASE)) {
			if (!player.hasPermission("opticore.use.piston")) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(Syntax.ITEM_USE);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.getEntity().getType().equals(EntityType.ENDER_CRYSTAL)) {
			event.blockList().clear();
		}
	}
}
