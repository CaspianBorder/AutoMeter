package autoMeter;

import java.sql.SQLException;
import java.util.* ;

class monitorRecord extends basicRecord
{
	private long lastConnectTime ;
	monitorRecord ( String input ) 
	{
		super ( input ) ;
		lastConnectTime = System.currentTimeMillis () ;
	}
	public void addData ( long input )
	{
		super.addData ( input ) ;
		lastConnectTime = System.currentTimeMillis () ;
		return ;
	}
	public ArrayList<String> getInfo ()
	{
		ArrayList<String> tempList = super.getInfo () ;
//		tempList.add ( String.valueOf ( lastConnectTime ) ) ;
		return tempList ;
	}
}

class monitorThread extends Thread
{
	private ArrayList<String> tempList ;
	private long startTime ;
	private long currentTime ;
	private boolean currentStatus = true ;
	monitorThread ()
	{
		startTime = System.currentTimeMillis () ;
	}
	private boolean checkStatus ()
	{
		return MainBlock.noError ;
	}
	private boolean checkTime ()
	{
		currentTime = System.currentTimeMillis () ;
		if ( currentTime - startTime >= MonitorMainBlock.SAVE_DELAY )
		{
			startTime = currentTime ;
			return true ;
		}
		return false ;
	}
	public void run ()
	{
		while ( MonitorMainBlock.isMonitoring == true )
		{
			tempList = DataAnalyze.startAnalyze () ;
//			System.out.println ("update");
			MonitorMainBlock.updateMonRecList ( tempList ) ;
			monitor.getList ( tempList ) ;
//			Thread.yield();
//			System.out.println("update completed");
			currentStatus = checkStatus () ;
			if ( currentStatus == false )
				break ;
			if ( checkTime () )
				MonitorMainBlock.sendRecord () ;
		}
		if ( currentStatus == true )
			MonitorMainBlock.sendRecord () ;
		return ;
	}
}

public class MonitorMainBlock 
{
	private static ArrayList<monitorRecord> monRecList ;
	private static int appLocation ;
	private static ArrayList<String> tempMonList ;
	private static Thread t ;
	public static final long SAVE_DELAY = 10000 ;//5min-300000 , 1min-60000
	public static boolean isMonitoring = false ;
	
	MonitorMainBlock ()
	{	
		monRecList = new ArrayList<monitorRecord> () ;
		monRecList.add ( new monitorRecord ( MainBlock.TOTAL_MARK ) ) ;
		tempMonList = new ArrayList<String> () ;
	}
	public static void initMMB ()
	{
		monRecList = new ArrayList<monitorRecord> () ;
		monRecList.add ( new monitorRecord ( MainBlock.TOTAL_MARK ) ) ;
		tempMonList = new ArrayList<String> () ;
		return ;
	}
	private static int getNameLoc ( String input )
	{
		for ( int i = 0 ; i < monRecList.size () ; i ++ )
		{
			if ( monRecList.get ( i ).hasRecord ( input ) == true )
				return i ;
		}
		return -1 ;
	}
	
	private static ArrayList<String> packRec ()
	{
		tempMonList.clear () ;
		for ( int i = 0 ; i < monRecList.size() ; i ++ )
		{
			tempMonList.addAll ( monRecList.get ( i ).getInfo () ) ;
		}
		return tempMonList ;
	}
	
	private static void resetRecList ()
	{
		monRecList.clear () ;
		monRecList.add ( new monitorRecord ( MainBlock.TOTAL_MARK ) ) ;
	}
	
	public static void updateMonRecList ( ArrayList<String> tempList )
	{
		for ( int i = 0 ; i < ( tempList.size () ) / 2 ; i ++ )
		{
			appLocation = getNameLoc ( tempList.get ( 2 * i ) ) ;
			if ( appLocation == -1 )
			{
				appLocation = monRecList.size () ;
				monRecList.add( new monitorRecord ( tempList.get ( 2 * i ) ) ) ;
			}
			monRecList.get ( appLocation ).addData ( Long.valueOf ( tempList.get ( 2 * i + 1 ) ) ) ;
		}
		return ;
	}
	public static void sendRecord ()
	{
//		System.out.println("sending");
		packRec () ;
		DataBaseOperation.initializeDBandTABLE() ;
		try 
		{
			DataBaseOperation.InToDB ( tempMonList ) ;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			MainBlock.noError = false ;
			monitor.backUpMode();
		}
		resetRecList () ;
//		System.out.println ( "send completed") ;
		return ;
	}
	public static void startMonitor ()
	{
		if ( monRecList == null )
			initMMB () ;
		if ( isMonitoring == false )
		{
			isMonitoring = true ;
			t = new Thread ( new monitorThread () ) ;
			t.start () ;
			return ;
		}
	}
	public static void stopMonitor ()
	{
		isMonitoring = false ;
		t.stop () ;
		while ( t.isAlive() ) ;
		System.runFinalization () ;
		return ;
	}
}
