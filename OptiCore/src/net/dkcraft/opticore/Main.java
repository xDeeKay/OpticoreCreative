package net.dkcraft.opticore;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import net.dkcraft.opticore.Main;
import net.dkcraft.opticore.commands.Aliases;
import net.dkcraft.opticore.commands.Channel;
import net.dkcraft.opticore.commands.Classic;
import net.dkcraft.opticore.commands.Deafen;
import net.dkcraft.opticore.commands.Freeze;
import net.dkcraft.opticore.commands.Ipcheck;
import net.dkcraft.opticore.commands.Msg;
import net.dkcraft.opticore.commands.Paint;
import net.dkcraft.opticore.commands.PersonalWorld;
import net.dkcraft.opticore.commands.R;
import net.dkcraft.opticore.commands.Ranks;
import net.dkcraft.opticore.commands.Socialspy;
import net.dkcraft.opticore.commands.Staffchat;
import net.dkcraft.opticore.commands.Stats;
import net.dkcraft.opticore.commands.ToggleNotifications;
import net.dkcraft.opticore.listeners.BuildListener;
import net.dkcraft.opticore.listeners.ChatListener;
import net.dkcraft.opticore.listeners.FreezeListener;
import net.dkcraft.opticore.listeners.ItemDropListener;
import net.dkcraft.opticore.listeners.ItemUseListener;
import net.dkcraft.opticore.listeners.NotificationListener;
import net.dkcraft.opticore.listeners.PaintListener;
import net.dkcraft.opticore.listeners.PlayerAliasConfig;
import net.dkcraft.opticore.listeners.PlayerIPConfig;
import net.dkcraft.opticore.spleef.Methods;
import net.dkcraft.opticore.spleef.NewSpleefRunnable;
import net.dkcraft.opticore.spleef.Spleef;
import net.dkcraft.opticore.spleef.SpleefListener;
import net.dkcraft.opticore.spleef.SpleefManage;
import net.dkcraft.opticore.stats.BlockBreakHandler;
import net.dkcraft.opticore.stats.BlockPlaceHandler;
import net.dkcraft.opticore.stats.ChatHandler;
import net.dkcraft.opticore.stats.LoginStats;
import net.dkcraft.opticore.stats.QuitHandler;
import net.dkcraft.opticore.stats.SpleefLossHandler;
import net.dkcraft.opticore.stats.SpleefWinHandler;
import net.dkcraft.opticore.stats.StatsListener;
import net.dkcraft.opticore.stats.TimeOnlineHandler;
import net.dkcraft.opticore.stats.VoteStats;
import net.dkcraft.opticore.util.InventoryGUI;
import net.dkcraft.opticore.util.ListStore;
import net.dkcraft.opticore.util.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.diddiz.LogBlock.LogBlock;

public class Main extends JavaPlugin {

	public Main instance;
	public Methods methods;
	public MySQL mysql;
	public NewSpleefRunnable spleefCountDown;
	public static ListStore ranks;
	
	public HashMap<String, String> chatRepeat = new HashMap<String, String>();
	public HashMap<String, String> msg = new HashMap<String, String>();
	public ArrayList<String> classic = new ArrayList<String>();
	public ArrayList<String> deafen = new ArrayList<String>();
	public ArrayList<String> freeze = new ArrayList<String>();
	public ArrayList<String> socialSpy = new ArrayList<String>();
	public ArrayList<String> staffChat = new ArrayList<String>();
	public ArrayList<String> toggleNotifications = new ArrayList<String>();
	public ArrayList<String> globalChannel = new ArrayList<String>();
	public ArrayList<String> localChannel = new ArrayList<String>();
	public ArrayList<String> paint = new ArrayList<String>();
	
	public ConcurrentHashMap<String, Integer> statsBlockBreak = new ConcurrentHashMap<String, Integer>();
	public ConcurrentHashMap<String, Integer> statsBlockPlace = new ConcurrentHashMap<String, Integer>();
	public ConcurrentHashMap<String, Integer> statsChat = new ConcurrentHashMap<String, Integer>();
	public HashMap<String, Long> statsTimeOnline = new HashMap<String, Long>();
	
	public ConcurrentHashMap<String, Integer> spleefWins = new ConcurrentHashMap<String, Integer>();
	public ConcurrentHashMap<String, Integer> spleefLosses = new ConcurrentHashMap<String, Integer>();
	
	public HashMap<String, Location> spleefLocation = new HashMap<String, Location>();
	public HashMap<String, ItemStack[]> spleefInventory = new HashMap<String, ItemStack[]>();
	public HashMap<String, ItemStack[]> spleefArmor = new HashMap<String, ItemStack[]>();
	public HashMap<String, String> spleefPitManage = new HashMap<String, String>();
	public HashMap<String, String> spleefFloorManage = new HashMap<String, String>();
	public ArrayList<String> spleefQueue = new ArrayList<String>();
	public ArrayList<String> spleefGame = new ArrayList<String>();
	
	public void loadConfiguration() {
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}

	public void onEnable() {
		
		this.instance = this;
		
		methods = new Methods(this);
		mysql = new MySQL(this);
		spleefCountDown = new NewSpleefRunnable(this);

		final PluginManager pm = getServer().getPluginManager();
		Plugin plugin = pm.getPlugin("LogBlock");
		if (plugin == null) {
			pm.disablePlugin(this);
			return;
		}

		String pluginFolder = this.getDataFolder().getAbsolutePath();
		File aliasDirectory = new File(Bukkit.getPluginManager().getPlugin("Opticore").getDataFolder(), "alias");
		File ipDirectory = new File(Bukkit.getPluginManager().getPlugin("Opticore").getDataFolder(), "ip");
		aliasDirectory.mkdirs();
		ipDirectory.mkdirs();

		(new File(pluginFolder)).mkdirs();

		Main.ranks = new ListStore(new File(pluginFolder + File.separator + "ranks.txt"));

		Main.ranks.load();
		loadConfiguration();
		reloadConfig();

		this.getCommand("aliases").setExecutor(new Aliases(this));
		this.getCommand("channel").setExecutor(new Channel(this));
		this.getCommand("classic").setExecutor(new Classic(this));
		this.getCommand("deafen").setExecutor(new Deafen(this));
		this.getCommand("freeze").setExecutor(new Freeze(this));
		this.getCommand("ipcheck").setExecutor(new Ipcheck(this));
		this.getCommand("msg").setExecutor(new Msg(this));
		this.getCommand("paint").setExecutor(new Paint(this));
		this.getCommand("personalworld").setExecutor(new PersonalWorld(this));
		this.getCommand("r").setExecutor(new R(this));
		this.getCommand("ranks").setExecutor(new Ranks(this));
		this.getCommand("socialspy").setExecutor(new Socialspy(this));
		this.getCommand("staffchat").setExecutor(new Staffchat(this));
		this.getCommand("stats").setExecutor(new Stats(this));
		this.getCommand("togglenotifications").setExecutor(new ToggleNotifications(this));
		
		this.getCommand("spleef").setExecutor(new Spleef(this));
		this.getCommand("spleefmanage").setExecutor(new SpleefManage(this));
		
		pm.registerEvents(new BuildListener(this), this);
		pm.registerEvents(new ChatListener(this), this);
		pm.registerEvents(new FreezeListener(this), this);
		pm.registerEvents(new InventoryGUI(this), this);
		pm.registerEvents(new ItemDropListener(this), this);
		pm.registerEvents(new ItemUseListener(this), this);
		pm.registerEvents(new NotificationListener(this), this);
		pm.registerEvents(new PlayerAliasConfig(this), this);
		pm.registerEvents(new PlayerIPConfig(this), this);

		pm.registerEvents(new PlayerIPConfig(this), this);
		pm.registerEvents(new PaintListener(this, (LogBlock)plugin), this);

		pm.registerEvents(new StatsListener(this), this);
		pm.registerEvents(new LoginStats(this), this);
		pm.registerEvents(new VoteStats(this), this);
		
		pm.registerEvents(new SpleefListener(this), this);

		mysql.openConnection();
	}

	public void onDisable() {
		Main.ranks.save();

		for (Player player : Bukkit.getOnlinePlayers()) {
			String playerName = player.getName();
			String uuid = player.getUniqueId().toString();
			new BlockBreakHandler(this, playerName, uuid).run();
			new BlockPlaceHandler(this, playerName, uuid).run();
			new ChatHandler(this, playerName, uuid).run();
			new QuitHandler(this, uuid).run();
			new TimeOnlineHandler(this, playerName, uuid).run();
			
			new SpleefWinHandler(this, playerName, uuid).run();
			new SpleefLossHandler(this, playerName, uuid).run();
		}

		try {
			if (mysql.connection != null && !mysql.connection.isClosed())
				mysql.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}