package net.dkcraft.opticore.label;

import net.dkcraft.opticore.Main;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Label implements CommandExecutor {

	public Main plugin;
	private LabelMethods label;

	public Label(Main plugin) {
		this.plugin = plugin;
		this.label = this.plugin.label;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("label")) {

			Player player = (Player) sender;

			if (args.length == 0) {
				label.sendHelpMenu(player);

			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("deletenear") || args[0].equalsIgnoreCase("dn")) {
					label.deleteNear(player);
					player.sendMessage(ChatColor.GREEN + "Deleted nearby labels.");
				} else {
					label.sendHelpMenu(player);
				}

			} else if (args.length == 2) {

				if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")) {
					if (player.hasPermission("opticore.label.list")) {
						if (args[1].equalsIgnoreCase("all") || args[1].equalsIgnoreCase("a")) {
							if (player.hasPermission("opticore.label.list.all")) {
								if (!label.labelsIsEmpty()) {
									label.listAllLabels(player);
								} else {
									player.sendMessage(ChatColor.GREEN + "There are " + ChatColor.YELLOW + "0" + ChatColor.GREEN + " total labels.");
								}
							}
						}
					}
				}

			} else if (args.length >= 3) {

				if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")) {
					if (player.hasPermission("opticore.label.list")) {

						if (args[1].equalsIgnoreCase("player") || args[1].equalsIgnoreCase("p")) {
							if (player.hasPermission("opticore.label.list.player")) {
								if (args.length == 3) {
									String owner = args[2];
									if (!label.labelsIsEmpty()) {
										if (label.ownerHasLabels(owner)) {
											label.listPlayerLabels(player, owner);
										} else {
											player.sendMessage(ChatColor.RED + "This player has no labels.");
										}
									} else {
										player.sendMessage(ChatColor.RED + "This player has no labels.");
									}
								} else {
									player.sendMessage("too many args");
								}
							}

						} else if (args[1].equalsIgnoreCase("world") || args[1].equalsIgnoreCase("w")) {
							if (player.hasPermission("opticore.label.list.world")) {
								if (args.length == 3) {
									String world = args[2];
									label.listWorldLabels(player, world);
								} else {
									player.sendMessage("too many args");
								}
							}
						}
					}

				} else if (args[0].equalsIgnoreCase("move") || args[0].equalsIgnoreCase("m")) {
					if (player.hasPermission("opticore.label.move")) {
						if (args.length == 3) {
							String owner = args[1];
							String name = args[2];
							if (label.ownerHasLabels(owner)) {
								if (label.ownerLabelExists(owner, name)) {
									
									for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
										if (entity.getType().equals(EntityType.ARMOR_STAND)) {
											if (entity.getCustomName() != null) {
												player.sendMessage(ChatColor.RED + "Your label can not be placed within 5 blocks of another label.");
												return true;
											}
										}
									}
									
									label.moveLabel(player, owner, name);
									player.sendMessage(ChatColor.GREEN + "Moved label: " + ChatColor.YELLOW + name + ChatColor.GREEN + " by " + ChatColor.YELLOW + owner);
								} else {
									player.sendMessage(ChatColor.RED + "A label by " + owner + " with the name " + name + " doesn't exist.");
								}
							} else {
								player.sendMessage(ChatColor.RED + "This player has no labels.");
							}
						} else {
							player.sendMessage("too many args");
						}
					}

				} else if (args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) {
					if (player.hasPermission("opticore.label.teleport")) {
						if (args.length == 3) {
							String owner = args[1];
							String name = args[2];
							if (label.ownerHasLabels(owner)) {
								if (label.ownerLabelExists(owner, name)) {
									label.teleportLabel(player, owner, name);
									player.sendMessage(ChatColor.GREEN + "Teleported to label: " + ChatColor.YELLOW + name + ChatColor.GREEN + " by " + ChatColor.YELLOW + owner);
								} else {
									player.sendMessage(ChatColor.RED + "A label by " + owner + " with the name " + name + " doesn't exist.");
								}
							} else {
								player.sendMessage(ChatColor.RED + "This player has no labels.");
							}
						} else {
							player.sendMessage("too many args");
						}
					}

				} else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("d")) {
					if (player.hasPermission("opticore.label.delete")) {
						if (args.length == 3) {
							String owner = args[1];
							String name = args[2];

							if (label.ownerHasLabels(owner)) {
								if (label.ownerLabelExists(owner, name)) {
									label.deleteLabel(owner, name);
									player.sendMessage(ChatColor.GREEN + "Deleted label: " + ChatColor.YELLOW + name + ChatColor.GREEN + " by " + ChatColor.YELLOW + owner);
								} else {
									player.sendMessage(ChatColor.RED + "A label by " + owner + " with the name " + name + " doesn't exist.");
								}
							} else {
								player.sendMessage(ChatColor.RED + "This player has no labels.");
							}
						} else {
							player.sendMessage("too many args");
						}
					}

				} else if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {
					if (player.hasPermission("opticore.label.create")) {
						String name = args[1];
						String text = StringUtils.join(args, ' ', 2, args.length);
						if (!label.playerLabelExists(player, name)) {
							if (Integer.valueOf(text.length()) <= 40) {
								
								for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
									if (entity.getType().equals(EntityType.ARMOR_STAND)) {
										if (entity.getCustomName() != null) {
											player.sendMessage(ChatColor.RED + "Your label can not be placed within 5 blocks of another label.");
											return true;
										}
									}
								}
								
								label.createLabel(player, name, text);
								player.sendMessage(ChatColor.GREEN + "Created label: " + ChatColor.YELLOW + name);
							} else {
								player.sendMessage(ChatColor.RED + "Your label can not exceed 40 characters.");
							}
						} else {
							player.sendMessage(ChatColor.RED + "A label by you with the name " + name + " already exists.");
						}
					}

				} else if (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("e")) {
					if (player.hasPermission("opticore.label.edit")) {
						if (args.length >= 4) {
							String owner = args[1];
							String name = args[2];
							String text = StringUtils.join(args, ' ', 3, args.length);

							if (label.ownerHasLabels(owner)) {
								if (label.ownerLabelExists(owner, name)) {
									if (Integer.valueOf(text.length()) <= 40) {
										label.editLabel(player, owner, name, text);
										player.sendMessage(ChatColor.GREEN + "Edited label: " + ChatColor.YELLOW + name + ChatColor.GREEN + " by " + ChatColor.YELLOW + owner);
									} else {
										player.sendMessage(ChatColor.RED + "Your label can not exceed 40 characters.");
									}
								} else {
									player.sendMessage(ChatColor.RED + "A label by " + owner + " with the name " + name + " doesn't exist.");
								}
							} else {
								player.sendMessage(ChatColor.RED + "This player has no labels.");
							}
						} else {
							player.sendMessage("not enough args");
						}
					}
				}
			} 
		}
		return true;
	}
}