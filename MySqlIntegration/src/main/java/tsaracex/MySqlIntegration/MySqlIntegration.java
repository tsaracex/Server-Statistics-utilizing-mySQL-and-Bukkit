package tsaracex.MySqlIntegration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/***
 * 
 * @author Paul J Williams (tsaracex)
 * @date 7/9/2011
 * @version 1.0
 * 
 */

public class MySqlIntegration extends JavaPlugin {

	Logger log = Logger.getLogger("Minecraft");
	final static String databaseName = "test";
	final static String tableName = "server_statistics_tab";
	final static String dbServerAddress = "";
	final static String serverUsername = "root";
	final static String serverPassword = "";
	final static String dbUsername = "root";
	final static String dbPassword = "";


	/**
	 * Main Testing Function (Will be moved into main on enable class then
	 * runnable class created
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{	
		//Initiate Connection
		//Connection conn = initiateDBConnectionOverSSH();
		
		ServerStatistics serverStatistics = new ServerStatistics();
		new Thread(serverStatistics).start();
		
		//System.exit(0);
	}
	
	
	/**
	 * On Enable
	 */
	public void onEnable() {
		
		log.info("SqlIntegration plugin has been enabled!");
		
		PluginManager pm = this.getServer().getPluginManager();
		Server server = this.getServer();
		
		//Create new thread to run data collection ever 60 seconds on server statistics
		ServerStatistics serverStatistics = new ServerStatistics();
		new Thread(serverStatistics).start();

	}

	/**
	 * On Disable
	 */
	public void onDisable() {
		log.info("SqlIntegration plugin has been disabled.");
	}

	/**
	 * 
	 * 
	 * 
	 * @return Connection object
	 * @throws Exception
	 */
	public static Connection initiateDBConnectionOverSSH() throws Exception
	{
		//JDBC Driver
		Class.forName("com.mysql.jdbc.Driver");
		
		//Creating a SSH connection 
		MySqlTunnelSession session = new MySqlTunnelSession(
				dbServerAddress, 22, serverUsername, serverPassword,
		        "127.0.0.1", 3306, dbUsername, dbPassword);

		//Creating a connection to the database
		return  session.getConnection(databaseName);
		
	}
	
	/**
	 * 
	 * 
	 * 
	 * @return Connection object
	 * @throws Exception
	 */
	public static Connection initiateDBConnection() throws Exception
	{
		//JDBC Driver
		Class.forName("com.mysql.jdbc.Driver");

		//Creating a connection to the database
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + dbServerAddress +":3306/" + databaseName,dbUsername,dbPassword);
		
		return  conn;
	}

}

