package com.minelogic.economy.core;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.minelogic.economy.commands.Commands;
import com.minelogic.economy.misc.Permissions;
import com.minelogic.economy.player.PlayerDisconnect;
import com.minelogic.economy.player.PlayerLogin;
import com.minelogic.economy.sql.MySQL;

public class Main extends JavaPlugin{
	
	Main plugin;
	PluginManager pm = Bukkit.getServer().getPluginManager();
	BukkitScheduler task = Bukkit.getScheduler();
	
	PlayerLogin      playerLogin      = new PlayerLogin(this);
	PlayerDisconnect playerDisconnect = new PlayerDisconnect(this);
	
	public void getPlugin(Main plug){
		this.plugin = plug;
	}

	public void onEnable(){
		registerConfig();
		MySQL.registerSQL();
		reloadServer();
		this.getCommand("money").setExecutor(new Commands(this));
		this.getCommand("economy").setExecutor(new Commands(this));
		this.getCommand("tokens").setExecutor(new Commands(this));
		Permissions.registerPermissions(pm);
		registerTasks();
		registerEvents();
	}
	
	/**
	 * Manage players HashMaps on server restart
	 */
	private void reloadServer(){
		for(World w : Bukkit.getWorlds()){
			for(Player p : w.getPlayers()){
				if(w.getPlayers() != null){
					MySQL.getDataFromDatabase(p);
				}
			}
		}
	}
	
	/**
	 * Register listeners
	 */
	public void registerEvents(){
		pm.registerEvents(this.playerLogin, this);
		pm.registerEvents(this.playerDisconnect, this);
	}
	
	/**
	 * Register tasks
	 */
	public void registerTasks(){
		task.scheduleSyncRepeatingTask(this, new Runnable(){public void run(){
			MySQL.updateDatabase();
		}}, 0, 20*(60*5));
	}
	
	/**
	 * Register config file
	 */
	private void registerConfig(){
		new MySQL(this);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
}
