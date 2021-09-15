package com.minelogic.economy.misc;

import org.bukkit.entity.Player;

import com.minelogic.economy.core.Main;

public class Economy {

	Main plugin;
	
	public Economy(Main plug){
		this.plugin = plug;
	}
	
	/**
	 * Get specific player money
	 * @param p - player 
	 * @return
	 */
	public static int getMoney(Player p){
		return HashMaps.money.get(p.getUniqueId());
	}
	
	/**
	 * Set specific player money
	 * @param p - player
	 * @param money - new money value
	 */
	public static void setMoney(Player p, int money){
		HashMaps.money.put(p.getUniqueId(), money);
	}
	
	/**
	 * Get specific player tokens
	 * @param p - player
	 * @return
	 */
	public static int getTokens(Player p){
		return HashMaps.tokens.get(p.getUniqueId());
	}
	
	/**
	 * Set specific player tokens
	 * @param p - tokens
	 * @param tokens - new tokens value
	 */
	public static void setTokens(Player p, int tokens){
		HashMaps.tokens.put(p.getUniqueId(), tokens);
	}
	
	/**
	 * Increase specific player money
	 * @param p - player
	 * @param money - amount of money
	 */
	public static void increaseMoney(Player p, int money){
		HashMaps.money.put(p.getUniqueId(), HashMaps.money.get(p.getUniqueId()) + money);
	}
	
	/**
	 * Decrease specific player money
	 * @param p - player
	 * @param money - amount of money
	 */
	public static void decreaseMoney(Player p, int money){
		HashMaps.money.put(p.getUniqueId(), HashMaps.money.get(p.getUniqueId()) - money);
	}
	
	/**
	 * Increase specific player tokens
	 * @param p - player
	 * @param tokens - amount of tokens
	 */
	public static void increaseTokens(Player p, int tokens){
		HashMaps.tokens.put(p.getUniqueId(), HashMaps.tokens.get(p.getUniqueId()) + tokens);
	}
	
	/**
	 * Decrease specific player tokens
	 * @param p - player
	 * @param tokens - amount of tokens
	 */
	public static void decreaseTokens(Player p, int tokens){
		HashMaps.tokens.put(p.getUniqueId(), HashMaps.tokens.get(p.getUniqueId()) - tokens);
	}
}
