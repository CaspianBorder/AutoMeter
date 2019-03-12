package autoMeter;

import java.io.IOException;

import javax.xml.bind.DatatypeConverter;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

class processPacket implements PacketReceiver
{
	public void receivePacket ( Packet packet )
	{
//		System.out.println(packet.data.toString() ) ;
//		System.out.printf ( "Data:0x%s\n", DatatypeConverter.printHexBinary( packet.data ) ) ;
//		System.out.println ( "packet received" ) ;
		DataAnalyze.analyzePacket ( packet ) ;
		return ;
	}
}

public class GetPacket 
{
	private static NetworkInterface[] devices = null ;
	private static JpcapCaptor jpcap = null ;
	private static final int CAPLEN = 65535 ;
	private static final int CAPTOR_TIME_OUT = 100000 ;
	private static int maxDevice = 0 ;
	private static boolean promiscMode = false ;
	private static int designatedDevice = 1 ;
	
	private static boolean hasDevices ()
	{
		if ( devices == null )
			return false ;
		else
			return true ;
	}
	
	private static boolean hasCaptor ()
	{
		if ( jpcap == null )
			return false ;
		else
			return true ;
	}
	
	private static boolean initDevices ()
	{
		if ( hasDevices () == false)
		{
			devices = JpcapCaptor.getDeviceList () ;
			maxDevice = devices.length ;
		}
		
		if ( maxDevice == 0 )
		{
			devices = null ;
			return false ;
		}
		else
			return true ;
	}
	public static int getDeviesNum ()
	{
		initDevices () ;
//		showDevices () ;
		return devices.length ;
	}
	private static void showDevices ()
	{
//		initDevices () ;
		for ( int i = 0 ; i < devices.length ; i++ )
		{
			System.out.println ( i + "£º" + devices [ i ].name + "(" + devices [ i ].description + ")" ) ;
			System.out.println ( "datalink£º" + devices [ i ].datalink_name + "(" + devices [ i ].datalink_description + ")" ) ;
			System.out.print ( "MAC address£º" ) ;
			for ( byte b : devices [ i ].mac_address )
			{
				System.out.print ( Integer.toHexString ( b & 0xff ) + ":" ) ;
			}
			System.out.println () ;
			for ( NetworkInterfaceAddress a : devices  [i ].addresses )
			{
				System.out.println ( " address: " + a.address + "|" + a.subnet + "|" + a.broadcast ) ;
			}
		}
	}
/*	
	public static boolean promiscStatus ()
	{
		return promiscMode ;
	}
	
	public static void changePromiscStatus ( boolean input )
	{
		promiscMode = input ;
		return ;
	}
*/	
	public static int getDesignated ()
	{
		return designatedDevice ;
	}
	
	public static void changeDesignated ( int input )
	{
		if ( input >= 0 && input < maxDevice )
			designatedDevice = input ;
		return ;
	}
	
	public static void initCaptor ()
	{
		if ( hasCaptor () == false )
		{
			try
	        {  
	            jpcap = JpcapCaptor.openDevice ( devices [ designatedDevice ] , CAPLEN , promiscMode , CAPTOR_TIME_OUT ) ;  
	        }
	        catch ( IOException e ) 
	        {  
//	          e.printStackTrace();  
	           System.out.println ( "Open captor device error" ) ;
	        }
		}
	}
	
	public static void startCaptor ()
	{
//		initDevices() ;
		initCaptor () ;
		jpcap.setNonBlockingMode ( true ) ;
/*
		try
		{
			jpcap.setFilter ( "ip" , true ) ;
		}
		catch ( Exception e )
		{
			System.out.println ( "Set filter error" ) ;
		}
*/
		jpcap.processPacket ( -1 , new processPacket () ) ;
//		jpcap.loopPacket ( -1 , new processPacket () ) ;
//		System.out.println ( "process return" );
		return ;
	}
	
	public static void stopCaptor ()
	{
		jpcap.breakLoop () ;
		return ;
	}
	
	public static void clearStatus ()
	{
		jpcap.breakLoop () ;
		jpcap.close () ;
		jpcap = null ;
		devices = null ;
		maxDevice = 0 ;
		designatedDevice = 0 ;
		promiscMode = false ;
		return ;
	}
}
