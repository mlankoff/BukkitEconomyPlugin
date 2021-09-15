package com.minelogic.economy.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.minelogic.economy.core.Main;
import com.minelogic.economy.sql.MySQL;


public class PlayerDisconnect implements Listener{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public PlayerDisconnect(Main plug){
		this.plugin = plug;
	}
	
	@EventHandler
	public static void playerDisconnect(PlayerQuitEvent e){
		Player p = e.getPlayer();
		MySQL.updatePlayerDatabase(p);
	}

}
