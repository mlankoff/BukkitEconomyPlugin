package com.minelogic.economy.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.minelogic.economy.core.Main;
import com.minelogic.economy.misc.HashMaps;

public class MySQL {
	
	private static Main plugin;
	
	public MySQL(Main plug){
		plugin = plug;
	}
	
	private static Connection connection;
	private static String ip;
	private static String port;
	private static String database;
	private static String login;
	private static String password;
	private static String table;
	
	
	
	/**
	 * Open connection with database
	 */
	public static synchronized void openConnection(){
		ip = plugin.getConfig().getString("ip");
		port = plugin.getConfig().getString("port");
		database = plugin.getConfig().getString("database");
		login = plugin.getConfig().getString("login");
		password = plugin.getConfig().getString("password");
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database + "?useSSL=false", login, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close connection with database
	 */
	public static synchronized void closeConnection(){
		try {
			connection.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if player is in database
	 * @param p - player
	 * @return true if player is in database
	 */
	public static synchronized boolean playerDataContainsPlayer(Player p){
		table = plugin.getConfig().getString("table_name");
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `" + table + "` WHERE uuid=?;");
			sql.setString(1, p.getUniqueId().toString());
			ResultSet resultSet = sql.executeQuery();
			boolean containsPlayer = resultSet.next();
			sql.close();
			resultSet.close();
			return containsPlayer;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Insert integer value to database
	 * @param p - player
	 * @param field - database field
	 * @param value - integer value
	 */
	private static void toDatabaseInt(Player p, String field, int value){
		table = plugin.getConfig().getString("table_name");
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT " + field + " From `" + table + "` WHERE uuid=?;");
			sql.setString(1, p.getUniqueId().toString());
			ResultSet sql_result = sql.executeQuery();
			sql_result.next();
			PreparedStatement sql_update = connection.prepareStatement("UPDATE `" + table + "` SET " + field + "=? WHERE uuid=?;");
			sql_update.setInt(1, value);
			sql_update.setString(2, p.getUniqueId().toString());
			sql_update.execute();
			sql_update.close();
			sql.close();
			sql_result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get data from database and put in HashMap<UUID, Integer>
	 * @param p - player
	 * @param field - database field
	 * @param hashmap - HashMap where to put integer value
	 */
	private static void fromDatabaseInt(Player p, String field, HashMap<UUID, Integer> hashmap){
		table = plugin.getConfig().getString("table_name");
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT " + field + " From `" + table + "` WHERE uuid=?;");
			sql.setString(1, p.getUniqueId().toString());
			ResultSet result = sql.executeQuery();
			result.next();
			hashmap.put(p.getUniqueId(), result.getInt(field));
			sql.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create database table
	 */
	public static void createTable(){
		openConnection();
		Statement statement;
		table = plugin.getConfig().getString("table_name");
		try {
			statement = connection.createStatement();
			String sql = "CREATE TABLE " + table + " " +
	                   "(Nick VARCHAR(16), " +
	                   " UUID CHAR(36), " + 
	                   " Money INTEGER, " + 
	                   " Tokens INTEGER)"; 
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
	}
	
	/**
	 * Create database table if not existed
	 */
	public static void registerSQL(){
		openConnection();
		table = plugin.getConfig().getString("table_name");
		try {
			DatabaseMetaData dmd = connection.getMetaData();
			ResultSet tables = dmd.getTables(null, null, table, null);
			if(tables.next() == false){
				createTable();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
	}
	
	/**
	 * Update database for all players
	 */
	public static void updateDatabase(){
		for(World w : Bukkit.getWorlds()){
			for(Player p : w.getPlayers()){
				if(w.getPlayers() != null){
					openConnection();
					try{
						if(playerDataContainsPlayer(p)){
							//TODO zapis do bazy
							toDatabaseInt(p, "Money", HashMaps.money.get(p.getUniqueId()));
							toDatabaseInt(p, "Tokens", HashMaps.tokens.get(p.getUniqueId()));
						}
					}catch(Exception exc){
						exc.printStackTrace();
					}finally{
						closeConnection();
					}
				}
			}
		}
	}
	
	/**
	 * Update database from specific player player
	 */
	public static void updatePlayerDatabase(Player p){
		openConnection();
		try{
			if(playerDataContainsPlayer(p)){
				//TODO zapis do bazy
				toDatabaseInt(p, "Money", HashMaps.money.get(p.getUniqueId()));
				toDatabaseInt(p, "Tokens", HashMaps.tokens.get(p.getUniqueId()));
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}finally{
			closeConnection();
		}
	}
	
	/**
	 * Create player record in database
	 * @param p
	 */
	private static void addPlayerToDatabase(Player p){
		PreparedStatement new_player;
		table = plugin.getConfig().getString("table_name");
		try {
			new_player = connection.prepareStatement("INSERT INTO `" + table + "` values(?,?,30,0);");
			new_player.setString(1, p.getName());
			new_player.setString(2, p.getUniqueId().toString());
			new_player.execute();
			new_player.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get all player data from database and put in HashMaps. If no player data than create it.
	 * @param p - player
	 */
	public static void getDataFromDatabase(Player p){
		openConnection();
		try{
			if(playerDataContainsPlayer(p)){
				//TODO zapis z bazy
				fromDatabaseInt(p, "Money", HashMaps.money);
				fromDatabaseInt(p, "Tokens", HashMaps.tokens);
			}else{
				addPlayerToDatabase(p);
				HashMaps.money.put(p.getUniqueId(), 30);
				HashMaps.tokens.put(p.getUniqueId(), 0);
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}finally{
			closeConnection();
		}
	}

}