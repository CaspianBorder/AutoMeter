package autoMeter;

import jpcap.* ;
import jpcap.packet.ARPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

import java.io.* ;

class PacketPrinter implements PacketReceiver 
{
	//this method is called every time Jpcap captures a packet
	public void receivePacket(Packet packet)
	{
		IPPacket ip = (IPPacket) packet ;
        System.out.println("源IP " + ip.src_ip.getHostAddress());  
        System.out.println("目的IP " + ip.dst_ip.getHostAddress());
//		if ( ip instanceof TCPPacket )
		if ( ip.protocol == 6 )
			JpcapPacket.TCPPacketProcess(packet) ;
//		else if ( ip instanceof UDPPacket )
		else if ( ip.protocol == 17 )
			JpcapPacket.UDPPacketProcess(packet);
//		else
			//JpcapPacket.OtherPacketProcess(packet);
	}
}

class PacketPrinter1 implements PacketReceiver
{
	public void receivePacket ( Packet packet )
	{
		System.out.println (packet.toString());
//		if ( packet instanceof DatalinkPacket )
//			System.out.println("DatalinkPacket");
//		if ( packet instanceof EthernetPacket )
//			System.out.println("EthernetPacket");
		if ( packet instanceof Packet )
			System.out.println("Packet");
		if ( packet instanceof ARPPacket )
			System.out.println("ARPPacket");
		if ( packet instanceof IPPacket )
			System.out.println("IPPacket");
		if ( packet instanceof ICMPPacket )
			System.out.println("ICMPPacket");
		if ( packet instanceof TCPPacket )
			System.out.println("TCPPacket");
		if ( packet instanceof UDPPacket )
			System.out.println("UDPPacket");

		System.out.println();
	}
}

public class JpcapPacket {  
	
	public static void TCPPacketProcess (Packet packet)
	{
		TCPPacket tcp = (TCPPacket)packet ;
        System.out.println("类型：TCP");  
//        System.out.println("优先权：" + tcp.priority);  
//        System.out.println("区分服务：最大的吞吐量： " + tcp.t_flag);  
//        System.out.println("区分服务：最高的可靠性：" + tcp.r_flag);  
        System.out.println("长度：" + tcp.length);  
//        System.out.println("标识：" + tcp.ident);  
//        System.out.println("DF:Don't Fragment: " + tcp.dont_frag);  
//        System.out.println("NF:Nore Fragment: " + tcp.more_frag);  
//        System.out.println("片偏移：" + tcp.offset);  
//        System.out.println("生存时间："+ tcp.hop_limit);  
        System.out.println("源IP " + tcp.src_ip.getHostAddress());  
        System.out.println("目的IP " + tcp.dst_ip.getHostAddress());  
    	System.out.println("端口号 " + tcp.src_port);
//        Date date = new Date();
//        long times = date.getTime();
//        System.out.println("时间 "+ times);
        System.out.println("----------------------------------------------");
	}
	public static void UDPPacketProcess (Packet packet)
	{
    	UDPPacket udp = (UDPPacket)packet ;
        System.out.println("类型：UDP");  
//        System.out.println("优先权：" + udp.priority);  
 //       System.out.println("区分服务：最大的吞吐量： " + udp.t_flag);  
 //       System.out.println("区分服务：最高的可靠性：" + udp.r_flag);  
        System.out.println("长度：" + udp.length);  
//        System.out.println("标识：" + udp.ident);  
//        System.out.println("DF:Don't Fragment: " + udp.dont_frag);  
//        System.out.println("NF:Nore Fragment: " + udp.more_frag);  
//        System.out.println("片偏移：" + udp.offset);  
//        System.out.println("生存时间："+ udp.hop_limit);  
        System.out.println("源IP " + udp.src_ip.getHostAddress());  
        System.out.println("目的IP " + udp.dst_ip.getHostAddress());  
    	System.out.println("端口号 " + udp.src_port);
//        Date date = new Date();
//        long times = date.getTime();
//        System.out.println("时间 "+ times);
        System.out.println("----------------------------------------------"); 
	}
	public static void OtherPacketProcess (Packet packet)
	{
        System.out.println("长度：" + packet.len); 
        System.out.println("----------------------------------------------"); 
	}
	
    public static void main(String[] args)  
    {  
        /*--------------    第一步绑定网络设备       --------------*/   
          
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();//获取网络接口列表
		for(int i = 0; i < devices.length; i++){
			//名称、描述
			System.out.println(i + "：" + devices[i].name + "(" + devices[i].description + ")");
			//数据链路层名称、描述
			System.out.println("datalink：" + devices[i].datalink_name + "(" + devices[i].datalink_description + ")");
			//MAC地址
			System.out.print(" MAC address：");
			for(byte b: devices[i].mac_address){
				System.out.print(Integer.toHexString(b & 0xff) + ":");
			}
			System.out.println();
			//IP地址、子网掩码、广播地址
			for(NetworkInterfaceAddress a : devices[i].addresses){
				System.out.println(" address: " + a.address + "|" + a.subnet + "|" + a.broadcast);
			}
		}
        
        JpcapCaptor jpcap = null;
        int caplen = 65535;
        boolean promiscCheck = false;
        try
        {  
            jpcap = JpcapCaptor.openDevice(devices[1], caplen, promiscCheck, 100000);  
        }
        catch(IOException e)  
        {  
           e.printStackTrace();  
           System.out.println ("Capture error") ;
        }  

        try {
			jpcap.setFilter("ip", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*
        try {
			jpcap.setFilter("ip and udp", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
 //       jpcap.processPacket(-1, new PacketPrinter()) ;
        jpcap.loopPacket(-1, new PacketPrinter());
        System.out.println ( "loop return" ) ;

        /*----------第二步抓包-----------------*/   
/*
        while(true)  
        {  
            Packet packet  = jpcap.getPacket();
            if(packet instanceof TCPPacket)
            {
            	TCPPacket tcp = (TCPPacket)packet ;
                System.out.println("类型：TCP");  
                System.out.println("优先权：" + tcp.priority);  
                System.out.println("区分服务：最大的吞吐量： " + tcp.t_flag);  
                System.out.println("区分服务：最高的可靠性：" + tcp.r_flag);  
                System.out.println("长度：" + tcp.length);  
                System.out.println("标识：" + tcp.ident);  
                System.out.println("DF:Don't Fragment: " + tcp.dont_frag);  
                System.out.println("NF:Nore Fragment: " + tcp.more_frag);  
                System.out.println("片偏移：" + tcp.offset);  
                System.out.println("生存时间："+ tcp.hop_limit);  
                System.out.println("源IP " + tcp.src_ip.getHostAddress());  
                System.out.println("目的IP " + tcp.dst_ip.getHostAddress());  
            	System.out.println("端口号 " + tcp.src_port);
                Date date = new Date();
                long times = date.getTime();
                System.out.println("时间 "+ times);
                System.out.println("----------------------------------------------");  
            	
            }
            if(packet instanceof UDPPacket)
            {
            	UDPPacket udp = (UDPPacket)packet ;
                System.out.println("类型：UDP");  
                System.out.println("优先权：" + udp.priority);  
                System.out.println("区分服务：最大的吞吐量： " + udp.t_flag);  
                System.out.println("区分服务：最高的可靠性：" + udp.r_flag);  
                System.out.println("长度：" + udp.length);  
                System.out.println("标识：" + udp.ident);  
                System.out.println("DF:Don't Fragment: " + udp.dont_frag);  
                System.out.println("NF:Nore Fragment: " + udp.more_frag);  
                System.out.println("片偏移：" + udp.offset);  
                System.out.println("生存时间："+ udp.hop_limit);  
                System.out.println("源IP " + udp.src_ip.getHostAddress());  
                System.out.println("目的IP " + udp.dst_ip.getHostAddress());  
            	System.out.println("端口号 " + udp.src_port);
                Date date = new Date();
                long times = date.getTime();
                System.out.println("时间 "+ times);
                System.out.println("----------------------------------------------");  
            	
            }
        }
*/

/*            if(packet instanceof IPPacket && ((IPPacket)packet).version == 4)  
            {  
                IPPacket ip = (IPPacket)packet;//强转  
                  
                System.out.println("版本：IPv4");  
                System.out.println("优先权：" + ip.priority);  
                System.out.println("区分服务：最大的吞吐量： " + ip.t_flag);  
                System.out.println("区分服务：最高的可靠性：" + ip.r_flag);  
                System.out.println("长度：" + ip.length);  
                System.out.println("标识：" + ip.ident);  
                System.out.println("DF:Don't Fragment: " + ip.dont_frag);  
                System.out.println("NF:Nore Fragment: " + ip.more_frag);  
                System.out.println("片偏移：" + ip.offset);  
                System.out.println("生存时间："+ ip.hop_limit);  
                                
                String protocol ="";  
                switch(new Integer(ip.protocol))  
                {  
                case 1:protocol = "ICMP";break;  
                case 2:protocol = "IGMP";break;  
                case 6:protocol = "TCP";break;  
                case 8:protocol = "EGP";break;  
                case 9:protocol = "IGP";break;  
                case 17:protocol = "UDP";break;  
                case 41:protocol = "IPv6";break;  
                case 89:protocol = "OSPF";break;  
                default : break;  
                }  
                System.out.println("协议：" + protocol); 
                System.out.println("源IP " + ip.src_ip.getHostAddress());  
                System.out.println("目的IP " + ip.dst_ip.getHostAddress());  
                Date date = new Date();
                long times = date.getTime();
                System.out.println("时间 "+ times);
//                System.out.println("源主机名： " + ip.src_ip);  
//                System.out.println("目的主机名： " + ip.dst_ip);  
                if ( ip.protocol == 6 )
                {
                	TCPPacket tcp = (TCPPacket) ip ;
                	System.out.println("Port:" + tcp.src_port);
                }
                if ( ip.protocol == 17 )
                {
                	UDPPacket udp = (UDPPacket) ip ;
                	System.out.println("Port:" + udp.src_port);
                }
                System.out.println("----------------------------------------------");  
            }
*/      
    }  
}  