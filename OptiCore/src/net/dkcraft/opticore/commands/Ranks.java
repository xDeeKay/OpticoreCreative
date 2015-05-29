package net.dkcraft.opticore.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.util.Syntax;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Ranks implements CommandExecutor {

	private Main plugin;

	public Ranks(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ranks")) {
			if (args.length == 0) {
				File file = new File (plugin.getDataFolder(), "ranks.txt");
				BufferedReader in = null;
				try {
					in = new BufferedReader(new FileReader(file));
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				String line;
				try {
					while ((line = in.readLine()) != null) {
						cs.sendMessage(ChatColor.translateAlternateColorCodes ('&', line));
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				cs.sendMessage(Syntax.USAGE_INCORRECT + "/ranks");
			}
		}
		return true;
	}
}