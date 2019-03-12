package autoMeter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.* ;

import javax.xml.bind.DatatypeConverter;

import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

class analyzeRecord extends basicRecord
{
	private ArrayList<String> port = new ArrayList<String> () ;
	analyzeRecord ( String input1 , String input2 )
	{
		super ( input1 ) ;
		port.add ( input2 ) ;
	}

	public boolean hasPortRecord ( String input )
	{
		if ( port.contains ( input ) )
			return true ;
		return false ;
	}
	
	public boolean refreshPortList ( String appName , String portNo )
	{
		if ( hasRecord ( appName ) )
			if ( hasPortRecord ( portNo ) == false )
				{
					port.add ( portNo ) ;
					return true ;
				}
		return false ;
	}
}

public class DataAnalyze 
{
	private static ArrayList<analyzeRecord> anaRecList ;
//	private static int appLocation ;
	private static ArrayList<String> tempAnaList ;
	private static long startTime ;
	private static long currentTime ;
	private static InetAddress addr = null ;
	private static String localIP ;
	private static boolean hasStopped = false ;
	private static int count ;
	DataAnalyze ()
	{
		anaRecList = new ArrayList<analyzeRecord> () ;
		anaRecList.add ( new analyzeRecord ( MainBlock.TOTAL_MARK , "-1" ) ) ;
		tempAnaList = new ArrayList<String> () ;
		getLocalIP () ;
	}
	private static void initDA ()
	{
		anaRecList = new ArrayList<analyzeRecord> () ;
		anaRecList.add ( new analyzeRecord ( MainBlock.TOTAL_MARK , "-1" ) ) ;
		tempAnaList = new ArrayList<String> () ;
		getLocalIP () ;
		return ;
	}
	private static void getLocalIP ()
	{
		try 
		{
			addr = InetAddress.getLocalHost () ; 
		} 
		catch (UnknownHostException e) 
		{
			System.out.println( "Fail to get local IP" ) ;
		}
		localIP=addr.getHostAddress().toString() ;
	}
	
	private static String getProcessName ( String port )
	{
//		System.out.println ( "in getProcessName" ) ;
		String cmdLine = "netstat -ano " ;
		String pid = null ;
		BufferedReader br = null ; 
		Process p = null ;
		String line ;
		String pName = null ;
        try 
        {  
            p = Runtime.getRuntime ().exec ( cmdLine ) ;  
            br = new BufferedReader ( new InputStreamReader ( p.getInputStream () ) ) ;     
            while ( ( line = br.readLine () ) != null ) 
            {  
//            	System.out.println ( line ) ;
//            	System.out.println ("localip : " + localIP ) ;
//            	System.out.println ("port : " + port );
//                if ( line.indexOf ( localIP + ":" + port + " " ) != -1 )
//            	if ( line.contains ( port ) )
            	if ( line.indexOf ( port ) != -1 )
                {
//                	System.out.println ( "port founded") ;
                	pid = line.substring ( line.lastIndexOf ( " " ) + 1 ) ;
//                	System.out.println ( "PID : " + pid ) ;
                	break ;
                }
//                else
//                	System.out.println("port not found");
            }
            if ( pid != null )
            {
                cmdLine = "tasklist -fi \"pid eq " + pid + "\"" ;
                p = Runtime.getRuntime ().exec ( cmdLine ) ;  
                br = new BufferedReader ( new InputStreamReader ( p.getInputStream () ) ) ;     
                while ( ( line = br.readLine () ) != null ) 
                {  
                    if ( line.indexOf ( pid ) != -1 )
//                	if ( line.contains( pid ) )
                    {
                    	pName = line.substring ( 0 , line.indexOf ( " " ) ).trim () ;
//                    	System.out.println ( pName );
                    	break ;
                    }
                }
            }
        } 
        catch (Exception e) 
        {  
//	            e.printStackTrace();  
        }
        finally  
        {  
            if (br != null)  
            {  
                try 
                {  
                    br.close();  
                } 
                catch (Exception e) 
                {  
//	                    e.printStackTrace();  
                }  
            }  
        }
        return pName ;
	}
	
	private static String updatePortList ()
	{
		String cmdLine = "netstat -ano " ;
		String pid ;
		BufferedReader br = null ; 
		Process p = null ;
		String line ;
		String temp ;
		int index = 0 ;
		String pName = null ;
		ArrayList<String> appName = new ArrayList<String> () ;
		ArrayList<String> portNo = new ArrayList<String> () ;	
        try 
        {  
            p = Runtime.getRuntime ().exec ( cmdLine ) ;  
            br = new BufferedReader ( new InputStreamReader ( p.getInputStream () ) ) ;     
            while ( ( line = br.readLine () ) != null ) 
            {
                if ( line.indexOf ( localIP ) != -1 )
                {
                	index = line.indexOf ( ":" ) ;
                	temp = line.substring ( index + 1 , index + 5 ) ;
                	portNo.add ( temp.trim () ) ;
                	index = line.lastIndexOf ( " " ) ;
                	appName.add ( line.substring ( index ).trim () ) ;
                	break ;
                }
            }
        } 
        catch (Exception e) 
        {  
//	            e.printStackTrace();  
        }   
        finally  
        {  
            if (br != null)  
            {  
                try 
                {  
                    br.close();  
                } 
                catch (Exception e) 
                {  
//	                    e.printStackTrace();  
                }  
            }  
        }
        
        for ( int i = 0 ; i < appName.size() ; i ++ )
        {
        	cmdLine = "tasklist -fi \"pid eq " + portNo.get ( i ) + "\"" ;
            try 
            {  
                p = Runtime.getRuntime ().exec ( cmdLine ) ;  
                br = new BufferedReader ( new InputStreamReader ( p.getInputStream () ) ) ;     
                while ( ( line = br.readLine () ) != null ) 
                {  
                    if ( line.indexOf ( portNo.get ( i ) ) != -1 )
                    {
                    	pName = line.substring ( 0 , line.indexOf ( " " ) ) ;
                    	break ;
                    }
                }
            } 
            catch (Exception e) 
            {  
//    	            e.printStackTrace();  
            }   
            finally  
            {  
                if (br != null)  
                {  
                    try 
                    {  
                        br.close();  
                    } 
                    catch (Exception e) 
                    {  
//    	                    e.printStackTrace();  
                    }  
                }  
            }
        }
        return null ;
	}
	
	private static int getNameLoc ( String input )
	{
//		System.out.println ( "in getNameLoc" ) ;
//		System.out.println ( "port :" + input ) ;
		for ( int i = 0 ; i < anaRecList.size () ; i ++ )
		{
			if ( anaRecList.get ( i ).hasPortRecord ( input ) == true )
				return i ;
		}
		return -1 ;
	}
	
	private static int getSamePortLoc ( String appName , String portNo )
	{
		for ( int i = 0 ; i < anaRecList.size () ; i ++ )
		{
			if ( anaRecList.get ( i ).refreshPortList ( appName , portNo ) )
				return i ;
		}
		return -1 ;
	}
	
	private static ArrayList<String> packRec ()
	{
//		System.out.println ( "in packRec" ) ;
		tempAnaList.clear () ;
		for ( int i = 0 ; i < anaRecList.size () ; i ++ )
		{
			tempAnaList.addAll ( anaRecList.get ( i ).getInfo () ) ;
			anaRecList.get ( i ).clearData () ;
		}
		return tempAnaList ;
	}
	
	private static void resetRecList ()
	{
		if ( anaRecList != null )
			anaRecList.clear () ;
		else
			initDA () ;
		anaRecList.add ( new analyzeRecord ( MainBlock.TOTAL_MARK , "-1" ) ) ;
	}

	private static void updateAnaRecList ( int appLocation , long appData )
	{
//		System.out.println ( "in updateAnaRecList" ) ;
		anaRecList.get ( appLocation ).addData ( appData ) ;
		return ;
	}
	private static boolean checkTime ()
	{
//		System.out.println ( "in checkTime" ) ;
		currentTime = System.currentTimeMillis () ;
//		System.out.println ( currentTime - startTime ) ;
		if ( currentTime - startTime >= 1000 )
			return false ;
		return true ;
	}
	
	private static void tcpPacketProcess ( IPPacket packet )
	{
//		System.out.println ( "in tcpPacketProcess" ) ;
		TCPPacket tcp = ( TCPPacket ) packet ;
		String tcpPort = null ;
		int appLocation ;
		if ( tcp.dst_ip.toString ().contains ( localIP ) )
			tcpPort = Integer.toString ( tcp.dst_port ) ;
		else
			tcpPort = Integer.toString ( tcp.src_port ) ;
		appLocation = getNameLoc ( tcpPort );
//		System.out.println("appLocation" + appLocation);
		if ( appLocation == -1 )
		{
			String appName ;
			appName = getProcessName ( tcpPort ) ;
			if ( appName == null )
			{
//				System.out.println ( "notadd tcpPacketProcess" ) ;
				count -- ;
				return ;
			}
			appLocation = getSamePortLoc ( appName , tcpPort ) ;
			if ( appLocation == -1 )
			{
				appLocation = anaRecList.size () ;
				anaRecList.add ( new analyzeRecord ( appName , tcpPort ) ) ;
			}
		}
		updateAnaRecList ( appLocation , ( long ) tcp.len ) ;
//		System.out.println ( "add tcpPacketProcess" ) ;
		count -- ;
		return ;
	}
	
	private static void udpPacketProcess ( IPPacket packet )
	{
//		System.out.println ( "in udpPacketProcess" ) ;
		UDPPacket udp = ( UDPPacket ) packet ;
		String udpPort = null ;
		int appLocation ;
		if ( udp.dst_ip.toString ().contains ( localIP ) )
			udpPort = Integer.toString ( udp.src_port ) ;
		else
			udpPort = Integer.toString ( udp.src_port ) ;
		appLocation = getNameLoc ( udpPort );
		if ( appLocation == -1 )
		{
			String appName ;
			appName = getProcessName ( udpPort ) ;
			if ( appName == null )
			{
//				System.out.println ( "notadd udpPacketProcess" ) ;
				count -- ;
				return ;
			}
			appLocation = getSamePortLoc ( appName , udpPort ) ;
			if ( appLocation == -1 )
			{
				appLocation = anaRecList.size () ;
				anaRecList.add ( new analyzeRecord ( appName , udpPort ) ) ;
			}
		}
		updateAnaRecList ( appLocation , ( long ) udp.len ) ;
//		System.out.println ( "add udpPacketProcess" ) ;
		count -- ;
		return ;
	}
	
	public static void analyzePacket ( Packet packet )
	{
//		System.out.println ( "in analyzePacket" ) ;
		anaRecList.get ( 0 ).addData ( (long) packet.len ) ;
		if ( packet instanceof IPPacket )
		{
			IPPacket ip = ( IPPacket ) packet ;
			if ( ip.protocol == 6 )
			{
				count ++ ;
				tcpPacketProcess ( ip ) ;
			}
			else if ( ip.protocol == 17 )
			{
				count ++ ;
				udpPacketProcess ( ip ) ;
			}
		}

		if ( count > 100 && checkTime () == false )
		{
			GetPacket.stopCaptor();
			hasStopped = true ;
		}

		return ;
	}
	
	public static ArrayList<String> startAnalyze ()
	{
//		System.out.println ( "Start analyze" ) ;
		hasStopped = false ;
		resetRecList () ;
		count = 0 ;
		if ( anaRecList == null )
			initDA () ;
		startTime = System.currentTimeMillis () ;
		while ( checkTime () )
		{
			GetPacket.startCaptor () ;
		}
		GetPacket.stopCaptor () ;
		while ( count > 0 ) ;
		ArrayList<String> temp = packRec() ;
//		printout ( temp ) ;
		return temp ;
	}

	public static void main ( String[] args )
	{
		anaRecList = new ArrayList<analyzeRecord> () ;
		anaRecList.add ( new analyzeRecord ( MainBlock.TOTAL_MARK , "-1" ) ) ;
		tempAnaList = new ArrayList<String> () ;
		getLocalIP () ;
		GetPacket.getDeviesNum() ;
		while ( true )
		{
			hasStopped = true ;
//			resetRecList () ;
			startTime = System.currentTimeMillis () ;
//			System.out.println ( "StartTime:" + startTime ) ;
			while ( checkTime () )
			{
				GetPacket.startCaptor () ;
			}
//			System.out.println ( "CurrentTime" + currentTime ) ;
//			GetPacket.stopCaptor () ;
			ArrayList<String> temp = packRec() ;
			System.out.println ("----------------------" ) ;
			for ( int i = 0 ; i < temp.size() ; i ++ )
			{
				System.out.println ( temp.get( i ) ) ;
			}

			printFormater ( temp ) ;
			System.out.println ("----------------------" ) ;
		}
	}
	private static void printout ( ArrayList<String> temp )
	{
		System.out.println ("----------------------" ) ;
		for ( int i = 0 ; i < temp.size() ; i ++ )
		{
			System.out.println ( temp.get( i ) ) ;
		}

		printFormater ( temp ) ;
		System.out.println ("----------------------" ) ;
	}
	private static void printFormater ( ArrayList<String> temp )
	{
		for ( int i = 0 ; i < temp.size () ; i ++ )
		{
			if ( i % 2 == 0 )
			{
				if ( Long.valueOf( temp.get ( i + 1 ) ) == 0 )
				{
					i ++ ;
					continue ;
				}
				System.out.println ( temp.get ( i ) );
			}
			else
			{
				long temp1 = Long.valueOf ( temp.get ( i ) ) ;
				double num = temp1 ;
				int step = 0 ;
				if ( num > 1024 ) // kb
				{
					num /= 1024 ;
					step ++ ;
				}
				if ( num > 1024 ) // mb
				{
					num /= 1024 ;
					step ++ ;
				}
				if ( num > 1024 ) // gb
				{
					num /= 1024 ;
					step ++ ;
				}
				System.out.printf( "%.2f" , num ) ;
				switch ( step )
				{
					case 0 :
					{
						System.out.println ( " B/s" ) ;
						break ;
					}
					case 1 :
					{
						System.out.println ( "kB/s" ) ;
						break ;
					}
					case 2 :
					{
						System.out.println ( "mB/s" ) ;
						break ;
					}
					case 3 :
					{
						System.out.println ( "gB/s" ) ;
						break ;
					}
				}
			}
		}
	}

	protected void finalize ()
	{
		GetPacket.clearStatus () ;
	}
}
