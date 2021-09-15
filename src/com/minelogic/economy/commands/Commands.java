package com.minelogic.economy.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minelogic.economy.core.Main;
import com.minelogic.economy.misc.Economy;
import com.minelogic.economy.misc.HashMaps;
import com.minelogic.economy.misc.Permissions;

public class Commands implements CommandExecutor{

	Main plugin;
	
	public Commands(Main plug){
		this.plugin = plug;
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		Player p = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("economy")){
			if(args.length == 0){
				p.sendMessage(ChatColor.GREEN + "Money: " + ChatColor.RESET + Economy.getMoney(p) + ChatColor.GREEN + "$");
				p.sendMessage(ChatColor.YELLOW + "Tokens: " + ChatColor.RESET + Economy.getTokens(p));
			}else if(args.length == 1){
				if(args[0].equalsIgnoreCase("help")){
					p.sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "MINELOGIC ECONOMY" + ChatColor.GOLD + "]");
					p.sendMessage(ChatColor.GREEN + "/economy" + ChatColor.GOLD + "- Show players account");
					if(p.hasPermission(Permissions.admin)){
						p.sendMessage(ChatColor.GREEN + "/money <player>" + ChatColor.GOLD + "- Show players account");
						p.sendMessage(ChatColor.GREEN + "/money set <player> <money>" + ChatColor.GOLD + "- sets money");
						p.sendMessage(ChatColor.GREEN + "/money add <player> <money>" + ChatColor.GOLD + "- adds money");
						p.sendMessage(ChatColor.GREEN + "/money decrease <player> <money>" + ChatColor.GOLD + "- decrease money");
						p.sendMessage(ChatColor.GREEN + "/tokens <player>" + ChatColor.GOLD + "- Show players account");
						p.sendMessage(ChatColor.GREEN + "/tokens set <player> <tokens>" + ChatColor.GOLD + "- sets tokens");
						p.sendMessage(ChatColor.GREEN + "/tokens add <player> <tokens>" + ChatColor.GOLD + "- adds tokens");
						p.sendMessage(ChatColor.GREEN + "/tokens decrease <player> <tokens>" + ChatColor.GOLD + "- decrease tokens");
					}
					p.sendMessage(ChatColor.LIGHT_PURPLE + "Created by " + ChatColor.GOLD + "men8");
				}
			}
		}else if(cmd.getName().equalsIgnoreCase("money")){
			if(p.hasPermission(Permissions.admin)){
				if(args.length == 1){
					if(HashMaps.money.containsKey(Bukkit.getPlayer(args[0]).getUniqueId())){
						p.sendMessage(args[0] + "'s money: " + Economy.getMoney(Bukkit.getPlayer(args[0])));
					}
				}else if(args.length == 3){
					if(HashMaps.money.containsKey(Bukkit.getPlayer(args[1]).getUniqueId())){
						if(StringUtils.isNumeric(args[2])){
							if(args[0].equalsIgnoreCase("set")){
								Economy.setMoney(Bukkit.getPlayer(args[1]), Integer.parseInt(args[2]));
							}else if(args[0].equalsIgnoreCase("add")){
								Economy.increaseMoney(Bukkit.getPlayer(args[1]), Integer.parseInt(args[2]));
							}else if(args[0].equalsIgnoreCase("decrease")){
								Economy.decreaseMoney(Bukkit.getPlayer(args[1]), Integer.parseInt(args[2]));
							}
						}
					}
				}
			}
		}else if(cmd.getName().equalsIgnoreCase("tokens")){
			if(p.hasPermission(Permissions.admin)){
				if(args.length == 1){
					if(HashMaps.tokens.containsKey(Bukkit.getPlayer(args[0]).getUniqueId())){
						p.sendMessage(args[0] + "'s tokens: " + Economy.getTokens(Bukkit.getPlayer(args[0])));
					}
				}else if(args.length == 3){
					if(HashMaps.money.containsKey(Bukkit.getPlayer(args[1]).getUniqueId())){
						if(StringUtils.isNumeric(args[2])){
							if(args[0].equalsIgnoreCase("set")){
								Economy.setTokens(Bukkit.getPlayer(args[1]), Integer.parseInt(args[2]));
							}else if(args[0].equalsIgnoreCase("add")){
								Economy.increaseTokens(Bukkit.getPlayer(args[1]), Integer.parseInt(args[2]));
							}else if(args[0].equalsIgnoreCase("decrease")){
								Economy.decreaseTokens(Bukkit.getPlayer(args[1]), Integer.parseInt(args[2]));
							}
						}
					}
				}
			}
		}
		return true;
	}

}
