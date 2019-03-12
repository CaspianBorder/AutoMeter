package autoMeter;

import java.util.ArrayList;

public class basicRecord
{
	private String appName ;
	private long appData ;
	basicRecord ( String input )
	{
		appName = input ;
		appData = 0 ;
	}
	
	public boolean hasRecord ( String input )
	{
		if ( input.equals ( appName ) )
			return true ;
		return false ;
	}
	
	public void addData ( long input )
	{
		appData += input ;
		return ;
	}
	
	public ArrayList<String> getInfo ()
	{
		ArrayList<String> tempList = new ArrayList<String> () ;
		tempList.add ( appName ) ;
		tempList.add ( String.valueOf ( appData ) ) ;
		return tempList ;
	}
	
	public void clearData ()
	{
		appData = 0 ;
		return ;
	}
}