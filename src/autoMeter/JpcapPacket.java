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
        System.out.println("ԴIP " + ip.src_ip.getHostAddress());  
        System.out.println("Ŀ��IP " + ip.dst_ip.getHostAddress());
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
        System.out.println("���ͣ�TCP");  
//        System.out.println("����Ȩ��" + tcp.priority);  
//        System.out.println("���ַ��������������� " + tcp.t_flag);  
//        System.out.println("���ַ�����ߵĿɿ��ԣ�" + tcp.r_flag);  
        System.out.println("���ȣ�" + tcp.length);  
//        System.out.println("��ʶ��" + tcp.ident);  
//        System.out.println("DF:Don't Fragment: " + tcp.dont_frag);  
//        System.out.println("NF:Nore Fragment: " + tcp.more_frag);  
//        System.out.println("Ƭƫ�ƣ�" + tcp.offset);  
//        System.out.println("����ʱ�䣺"+ tcp.hop_limit);  
        System.out.println("ԴIP " + tcp.src_ip.getHostAddress());  
        System.out.println("Ŀ��IP " + tcp.dst_ip.getHostAddress());  
    	System.out.println("�˿ں� " + tcp.src_port);
//        Date date = new Date();
//        long times = date.getTime();
//        System.out.println("ʱ�� "+ times);
        System.out.println("----------------------------------------------");
	}
	public static void UDPPacketProcess (Packet packet)
	{
    	UDPPacket udp = (UDPPacket)packet ;
        System.out.println("���ͣ�UDP");  
//        System.out.println("����Ȩ��" + udp.priority);  
 //       System.out.println("���ַ��������������� " + udp.t_flag);  
 //       System.out.println("���ַ�����ߵĿɿ��ԣ�" + udp.r_flag);  
        System.out.println("���ȣ�" + udp.length);  
//        System.out.println("��ʶ��" + udp.ident);  
//        System.out.println("DF:Don't Fragment: " + udp.dont_frag);  
//        System.out.println("NF:Nore Fragment: " + udp.more_frag);  
//        System.out.println("Ƭƫ�ƣ�" + udp.offset);  
//        System.out.println("����ʱ�䣺"+ udp.hop_limit);  
        System.out.println("ԴIP " + udp.src_ip.getHostAddress());  
        System.out.println("Ŀ��IP " + udp.dst_ip.getHostAddress());  
    	System.out.println("�˿ں� " + udp.src_port);
//        Date date = new Date();
//        long times = date.getTime();
//        System.out.println("ʱ�� "+ times);
        System.out.println("----------------------------------------------"); 
	}
	public static void OtherPacketProcess (Packet packet)
	{
        System.out.println("���ȣ�" + packet.len); 
        System.out.println("----------------------------------------------"); 
	}
	
    public static void main(String[] args)  
    {  
        /*--------------    ��һ���������豸       --------------*/   
          
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();//��ȡ����ӿ��б�
		for(int i = 0; i < devices.length; i++){
			//���ơ�����
			System.out.println(i + "��" + devices[i].name + "(" + devices[i].description + ")");
			//������·�����ơ�����
			System.out.println("datalink��" + devices[i].datalink_name + "(" + devices[i].datalink_description + ")");
			//MAC��ַ
			System.out.print(" MAC address��");
			for(byte b: devices[i].mac_address){
				System.out.print(Integer.toHexString(b & 0xff) + ":");
			}
			System.out.println();
			//IP��ַ���������롢�㲥��ַ
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

        /*----------�ڶ���ץ��-----------------*/   
/*
        while(true)  
        {  
            Packet packet  = jpcap.getPacket();
            if(packet instanceof TCPPacket)
            {
            	TCPPacket tcp = (TCPPacket)packet ;
                System.out.println("���ͣ�TCP");  
                System.out.println("����Ȩ��" + tcp.priority);  
                System.out.println("���ַ��������������� " + tcp.t_flag);  
                System.out.println("���ַ�����ߵĿɿ��ԣ�" + tcp.r_flag);  
                System.out.println("���ȣ�" + tcp.length);  
                System.out.println("��ʶ��" + tcp.ident);  
                System.out.println("DF:Don't Fragment: " + tcp.dont_frag);  
                System.out.println("NF:Nore Fragment: " + tcp.more_frag);  
                System.out.println("Ƭƫ�ƣ�" + tcp.offset);  
                System.out.println("����ʱ�䣺"+ tcp.hop_limit);  
                System.out.println("ԴIP " + tcp.src_ip.getHostAddress());  
                System.out.println("Ŀ��IP " + tcp.dst_ip.getHostAddress());  
            	System.out.println("�˿ں� " + tcp.src_port);
                Date date = new Date();
                long times = date.getTime();
                System.out.println("ʱ�� "+ times);
                System.out.println("----------------------------------------------");  
            	
            }
            if(packet instanceof UDPPacket)
            {
            	UDPPacket udp = (UDPPacket)packet ;
                System.out.println("���ͣ�UDP");  
                System.out.println("����Ȩ��" + udp.priority);  
                System.out.println("���ַ��������������� " + udp.t_flag);  
                System.out.println("���ַ�����ߵĿɿ��ԣ�" + udp.r_flag);  
                System.out.println("���ȣ�" + udp.length);  
                System.out.println("��ʶ��" + udp.ident);  
                System.out.println("DF:Don't Fragment: " + udp.dont_frag);  
                System.out.println("NF:Nore Fragment: " + udp.more_frag);  
                System.out.println("Ƭƫ�ƣ�" + udp.offset);  
                System.out.println("����ʱ�䣺"+ udp.hop_limit);  
                System.out.println("ԴIP " + udp.src_ip.getHostAddress());  
                System.out.println("Ŀ��IP " + udp.dst_ip.getHostAddress());  
            	System.out.println("�˿ں� " + udp.src_port);
                Date date = new Date();
                long times = date.getTime();
                System.out.println("ʱ�� "+ times);
                System.out.println("----------------------------------------------");  
            	
            }
        }
*/

/*            if(packet instanceof IPPacket && ((IPPacket)packet).version == 4)  
            {  
                IPPacket ip = (IPPacket)packet;//ǿת  
                  
                System.out.println("�汾��IPv4");  
                System.out.println("����Ȩ��" + ip.priority);  
                System.out.println("���ַ��������������� " + ip.t_flag);  
                System.out.println("���ַ�����ߵĿɿ��ԣ�" + ip.r_flag);  
                System.out.println("���ȣ�" + ip.length);  
                System.out.println("��ʶ��" + ip.ident);  
                System.out.println("DF:Don't Fragment: " + ip.dont_frag);  
                System.out.println("NF:Nore Fragment: " + ip.more_frag);  
                System.out.println("Ƭƫ�ƣ�" + ip.offset);  
                System.out.println("����ʱ�䣺"+ ip.hop_limit);  
                                
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
                System.out.println("Э�飺" + protocol); 
                System.out.println("ԴIP " + ip.src_ip.getHostAddress());  
                System.out.println("Ŀ��IP " + ip.dst_ip.getHostAddress());  
                Date date = new Date();
                long times = date.getTime();
                System.out.println("ʱ�� "+ times);
//                System.out.println("Դ�������� " + ip.src_ip);  
//                System.out.println("Ŀ���������� " + ip.dst_ip);  
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