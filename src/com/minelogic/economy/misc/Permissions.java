package com.minelogic.economy.misc;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

public class Permissions {
	public static Permission admin = new Permission("economy.admin");
	
	public static void registerPermissions(PluginManager pm){
		pm.addPermission(admin);
	}
}
