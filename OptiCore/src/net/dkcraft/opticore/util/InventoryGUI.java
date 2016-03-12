package net.dkcraft.opticore.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.dkcraft.opticore.Main;

public class InventoryGUI implements Listener {

	private Main plugin;

	public InventoryGUI(Main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	public static void openInventoryGUI(Player player) {
		Inventory inv = Bukkit.createInventory(null, 45, "Classic Inventory");

		ItemStack stone = new ItemStack (Material.STONE);
		ItemStack cobblestone = new ItemStack (Material.COBBLESTONE);
		ItemStack brick = new ItemStack (Material.BRICK);
		ItemStack dirt = new ItemStack (Material.DIRT);
		ItemStack plank = new ItemStack (Material.WOOD);
		ItemStack log = new ItemStack (Material.LOG);
		ItemStack leaf = new ItemStack (Material.LEAVES);
		ItemStack glass = new ItemStack (Material.GLASS);
		ItemStack slab = new ItemStack (Material.STEP);
		ItemStack mossycobblestone = new ItemStack (Material.MOSSY_COBBLESTONE);
		ItemStack sapling = new ItemStack (Material.SAPLING);
		ItemStack yellowflower = new ItemStack (Material.YELLOW_FLOWER);
		ItemStack redflower = new ItemStack (Material.RED_ROSE);
		ItemStack brownmushroom = new ItemStack (Material.BROWN_MUSHROOM);
		ItemStack redmushroom = new ItemStack (Material.RED_MUSHROOM);
		ItemStack sand = new ItemStack (Material.SAND);
		ItemStack gravel = new ItemStack (Material.GRAVEL);
		ItemStack sponge = new ItemStack (Material.SPONGE);
		ItemStack redwool = new ItemStack (Material.WOOL, 1, DyeColor.RED.getData());
		ItemStack orangewool = new ItemStack (Material.WOOL, 1, DyeColor.ORANGE.getData());
		ItemStack yellowwool = new ItemStack (Material.WOOL, 1, DyeColor.YELLOW.getData());
		ItemStack limewool = new ItemStack (Material.WOOL, 1, DyeColor.LIME.getData());
		ItemStack greenwool = new ItemStack (Material.WOOL, 1, DyeColor.GREEN.getData());
		ItemStack lightbluewool = new ItemStack (Material.WOOL, 1, DyeColor.LIGHT_BLUE.getData());
		ItemStack cyanwool = new ItemStack (Material.WOOL, 1, DyeColor.CYAN.getData());
		ItemStack bluewool = new ItemStack (Material.WOOL, 1, DyeColor.BLUE.getData());
		ItemStack brownwool = new ItemStack (Material.WOOL, 1, DyeColor.BROWN.getData());
		ItemStack purplewool = new ItemStack (Material.WOOL, 1, DyeColor.PURPLE.getData());
		ItemStack magentawool = new ItemStack (Material.WOOL, 1, DyeColor.MAGENTA.getData());
		ItemStack pinkwool = new ItemStack (Material.WOOL, 1, DyeColor.PINK.getData());
		ItemStack blackwool = new ItemStack (Material.WOOL, 1, DyeColor.BLACK.getData());
		ItemStack graywool = new ItemStack (Material.WOOL, 1, DyeColor.GRAY.getData());
		ItemStack lightgraywool = new ItemStack (Material.WOOL, 1, DyeColor.SILVER.getData());
		ItemStack whitewool = new ItemStack (Material.WOOL, 1, DyeColor.WHITE.getData());
		ItemStack coalore = new ItemStack (Material.COAL_ORE);
		ItemStack ironore = new ItemStack (Material.IRON_ORE);
		ItemStack goldore = new ItemStack (Material.GOLD_ORE);
		ItemStack ironblock = new ItemStack (Material.IRON_BLOCK);
		ItemStack goldblock = new ItemStack (Material.GOLD_BLOCK);
		ItemStack bookshelf = new ItemStack (Material.BOOKSHELF);
		ItemStack tnt = new ItemStack (Material.TNT);
		ItemStack obsidian = new ItemStack (Material.OBSIDIAN);

		inv.setItem(0, stone);
		inv.setItem(1, cobblestone);
		inv.setItem(2, brick);
		inv.setItem(3, dirt);
		inv.setItem(4, plank);
		inv.setItem(5, log);
		inv.setItem(6, leaf);
		inv.setItem(7, glass);
		inv.setItem(8, slab);
		inv.setItem(9, mossycobblestone);
		inv.setItem(10, sapling);
		inv.setItem(11, yellowflower);
		inv.setItem(12, redflower);
		inv.setItem(13, brownmushroom);
		inv.setItem(14, redmushroom);
		inv.setItem(15, sand);
		inv.setItem(16, gravel);
		inv.setItem(17, sponge);
		inv.setItem(18, redwool);
		inv.setItem(19, orangewool);
		inv.setItem(20, yellowwool);
		inv.setItem(21, limewool);
		inv.setItem(22, greenwool);
		inv.setItem(23, lightbluewool);
		inv.setItem(24, cyanwool);
		inv.setItem(25, bluewool);
		inv.setItem(26, brownwool);
		inv.setItem(27, purplewool);
		inv.setItem(28, magentawool);
		inv.setItem(29, pinkwool);
		inv.setItem(30, blackwool);
		inv.setItem(31, graywool);
		inv.setItem(32, lightgraywool);
		inv.setItem(33, whitewool);
		inv.setItem(34, coalore);
		inv.setItem(35, ironore);
		inv.setItem(36, goldore);
		inv.setItem(37, ironblock);
		inv.setItem(38, goldblock);
		inv.setItem(39, bookshelf);
		inv.setItem(40, tnt);
		inv.setItem(41, obsidian);

		player.openInventory(inv);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack hand = player.getItemInHand();
		Action action = event.getAction();
		if (plugin.classic.contains(player.getName())) {
			if (action.equals(Action.RIGHT_CLICK_AIR)) {
				if (hand != null) {
					openInventoryGUI(player);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Classic Inventory")) {
			if (event.getRawSlot() < event.getInventory().getSize()) {
				Player player = (Player) event.getWhoClicked();
				ItemStack stack = event.getCurrentItem();
				ItemStack hand = player.getItemInHand();
				if (stack != null && (!stack.getType().equals(Material.AIR))) {
					if (!hand.getType().equals(Material.AIR)) {
						event.setCancelled(true);
						player.getInventory().removeItem(hand);
						player.getInventory().setItemInHand(stack);
						player.updateInventory();
						player.closeInventory();
					}
				}
			}
		}
	}
}
