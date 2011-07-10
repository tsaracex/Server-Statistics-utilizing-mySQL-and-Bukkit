package tsaracex.MySqlIntegration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Server;
import org.bukkit.entity.Player;


/**
 * 
 * 
 * 
 * @author Paul
 *
 */
public class ServerStatistics extends MySqlIntegration implements Runnable  {
	
	final static String tableName = "server_statistics_tab";
	
    public void run() {

        try {
            
			Timer timer = new Timer();
	        timer.schedule(new ServerStatisticsTimerTask(),
	                       0,        //initial delay (0 seconds)
	                       60000);  //subsequent rate (60 seconds)

        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 
     * @author Paul
     *
     */
    class ServerStatisticsTimerTask extends TimerTask {
        public void run() {
	    	try 
	    	{
				Connection conn = MySqlIntegration.initiateDBConnectionOverSSH();
				
				/*
				 * Implement obtaining information from the data here and saving it to the mySQL database
				 */
				//INSERT INTO server_statistics_tab (`id`, `timestamp`, `num_of_players_online`, `players_online`) VALUES (2, '2011-07-08 12:12:12', 2, 'tsaracex,ljjokay');		
				PreparedStatement statementInsert = conn.prepareStatement("INSERT INTO " + tableName + " (`id`, `timestamp`, `num_of_players_online`, `players_online`) VALUES " +
						"(" + null + ", " + "CURRENT_TIMESTAMP" + ", 1, 'tsaracex')");
				
				statementInsert.executeUpdate();		
			
				//Create a prepared statement
				PreparedStatement statement = conn.prepareStatement("select * from server_statistics_tab");
				
				ResultSet result = statement.executeQuery();
				
				//Output results to STDOUT for debugging purposes
				while (result.next())
				{
					System.out.println(result.getString(1) + " " + result.getString(2) + " " + result.getString(3) + " " + result.getString(4));
				}
				
			} 
	    	catch (Exception e) 
	    	{
				e.printStackTrace();
			}
        	
        }
    }
    
    public void obtainCurrentServerInformation(Connection conn) throws Exception
    {	
    	
    	Server server = this.getServer();

    	//A list of players currently online
    	List<Player> onlinePlayerList = Arrays.asList(server.getOnlinePlayers());
    	
    	
    	
    	
    }
}
