package net.dkcraft.opticore.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

public class ConvertBook implements CommandExecutor {
	
	public ConvertBook(Main plugin) {
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("convertbook")) {
			if (args.length == 0) {
				Player player = (Player) sender;
				Material handMain = player.getInventory().getItemInMainHand().getType();
				if (handMain.equals(Material.WRITTEN_BOOK)) {
					
					BookMeta book = (BookMeta) player.getInventory().getItemInMainHand().getItemMeta();
					List<String> pages = book.getPages();
					
					ItemStack newbook = new ItemStack(Material.BOOK_AND_QUILL);
					BookMeta bm = (BookMeta) newbook.getItemMeta();
					
					for (String setPages : pages) {
						bm.addPage(setPages);
					}
					
					newbook.setItemMeta(bm);
					player.getInventory().addItem(newbook);
					player.sendMessage(ChatColor.GREEN + "Your signed book was converted to a book and quill.");
					
				} else {
					sender.sendMessage(ChatColor.RED + "You must be holding a signed book to use this.");
				}
			} else {
				sender.sendMessage(Syntax.USAGE_INCORRECT + "/convertbook");
			}
		}
		return true;
	}
}
