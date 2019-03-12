package autoMeter;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShowMainBlock 
{
	private static ArrayList<String> tempShowList ;
	private static String querySql = "select * from data order by appData desc" ;
	ShowMainBlock ()
	{	
		tempShowList = new ArrayList<String> () ;
	}
	public static void showData ()
	{
		resetList () ;
		try 
		{
			tempShowList = DataBaseOperation.Query ( querySql ) ;
		} 
		catch (SQLException e) 
		{
			MainBlock.noError = false ;
		}
		//to gui
	}
	public static void clearData ()
	{
		DataBaseOperation.dropTABLE() ;
		return ;
	}
	private static void resetList ()
	{
		tempShowList.clear() ;
		return ;
	}
}
