package com.minelogic.economy.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.minelogic.economy.core.Main;
import com.minelogic.economy.sql.MySQL;

public class PlayerLogin implements Listener{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public PlayerLogin(Main plug){
		this.plugin = plug;
	}
	
	@EventHandler
	public static void playerLogin(PlayerLoginEvent e){
		Player p = e.getPlayer();
		MySQL.getDataFromDatabase(p);
	}

}
