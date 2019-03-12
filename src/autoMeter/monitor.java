package autoMeter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import javax.swing.event.ChangeEvent;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.NullBorderPainter;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.painter.MatteGradientPainter;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.CremeSkin;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.jvnet.substance.theme.SubstanceEbonyTheme;
import org.jvnet.substance.title.FlatTitlePainter;
import org.jvnet.substance.title.Glass3DTitlePainter;
import org.jvnet.substance.watermark.SubstanceBinaryWatermark;

public class monitor extends JFrame
{
	
	private static JPanel contentPane;
	private JButton return_btn;
	
	public static JToggleButton switch_btn = new JToggleButton("开启监控");
	public static JLabel state_lbl = new JLabel("就绪");
	public static JLabel global_lbl = new JLabel("0");
//	public AlternateStop as = new AlternateStop();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					monitor frame = new monitor();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public monitor()

	{
		setIconImage(Toolkit.getDefaultToolkit().getImage("img" + File.separator + "icon.png"));
		setTitle("AutoMeter ver.1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		// 设置居中
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width / 2; // 获取屏幕的宽
		int screenHeight = screenSize.height / 2; // 获取屏幕的高
		int height = this.getHeight();
		int width = this.getWidth();
		setLocation(screenWidth - width / 2, screenHeight - height / 2);
		// 设置居中结束
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		// contentPane.setBackground(UIManager.getColor("Button.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		return_btn = new JButton("\u8FD4\u56DE");
		return_btn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if ( MonitorMainBlock.isMonitoring == true )
				{
					int res = JOptionPane.showConfirmDialog(null, "返回将停止监控，确认返回吗？", "提示", JOptionPane.OK_CANCEL_OPTION);
					if (res == 0)
					{
						MonitorMainBlock.stopMonitor();
					}
				}
				GUI frame = new GUI();
				frame.setVisible(true);
//				as.stopRequest();
				dispose();
			}
		});
		return_btn.setBounds(471, 71, 103, 35);
		contentPane.add(return_btn);

		switch_btn.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		// ImageIcon stopIcon = new ImageIcon ( "img/stopButton.png" ) ;
		// ImageIcon startIcon = new ImageIcon ( "img/startButton.png" ) ;
		// switch_btn.setIcon ( stopIcon ) ;
		// switch_btn.setSelectedIcon ( startIcon ) ;
		switch_btn.setBounds(332, 258, 230, 94);
		contentPane.add(switch_btn);

		JLabel label_1 = new JLabel("\u76D1\u63A7\u72B6\u6001\uFF1A");
		label_1.setFont(new Font("微软雅黑", Font.BOLD, 20));
		label_1.setBounds(10, 13, 100, 56);
		contentPane.add(label_1);

		
		state_lbl.setForeground(new Color(0, 0, 255));
		state_lbl.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		state_lbl.setBounds(112, 13, 337, 56);
		contentPane.add(state_lbl);

		
		global_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		global_lbl.setFont(new Font("微软雅黑", Font.BOLD, 24));
		global_lbl.setBounds(10, 81, 127, 55);
		contentPane.add(global_lbl);
		
		String tmp_str = new String();
		tmp_str = String.valueOf ( GetPacket.getDeviesNum() ) ;
		String port = JOptionPane.showInputDialog(contentPane, "当前有"+tmp_str+"个网络接口\n您要选择哪一个？(输入0~"+String.valueOf( Integer.parseInt(tmp_str)-1 )+")", "网络接口", JOptionPane.QUESTION_MESSAGE);
		while(Integer.parseInt(port) >= Integer.parseInt(tmp_str) || Integer.parseInt(port) < 0 )
		{
			JOptionPane.showMessageDialog(contentPane, "您的输入有误！", "网络接口", JOptionPane.WARNING_MESSAGE);
			port = JOptionPane.showInputDialog(contentPane, "当前有"+tmp_str+"个网络接口\n您要选择哪一个？(输入0~"+String.valueOf( Integer.parseInt(tmp_str)-1 )+")", "网络接口", JOptionPane.QUESTION_MESSAGE);
		}
		GetPacket.changeDesignated ( Integer.valueOf ( port ) ) ;
		textArea.setFont(new Font("Monospaced", Font.BOLD, 24));
		textArea.setLocation(132, 91);
		textArea.setSize(80, 44);
		
		contentPane.add(textArea);
		textArea_1.setFont(new Font("Monospaced", Font.PLAIN, 18));
		
//		textArea_1.setBackground(new Color(240 , 240 , 240));
		textArea_1.setBounds(10, 149, 175, 30);
		contentPane.add(textArea_1);
		textArea_2.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_2.setBackground(new Color(240 , 240 , 240));
		textArea_2.setBounds(200, 149, 60, 30);
		
		contentPane.add(textArea_2);
		textArea_3.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_3.setBackground(new Color(240 , 240 , 240));
		textArea_3.setBounds(10, 189, 175, 30);
		
		contentPane.add(textArea_3);
		textArea_4.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_4.setBackground(new Color(240 , 240 , 240));
		textArea_4.setBounds(200, 189, 60, 30);
		
		contentPane.add(textArea_4);
		textArea_5.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_5.setBackground(new Color(240 , 240 , 240));
		textArea_5.setBounds(10, 229, 175, 30);
		
		contentPane.add(textArea_5);
		textArea_6.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_6.setBackground(new Color(240 , 240 , 240));
		textArea_6.setBounds(200, 229, 60, 30);
		
		contentPane.add(textArea_6);
		textArea_7.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_7.setBackground(new Color(240 , 240 , 240));
		textArea_7.setBounds(10, 269, 175, 30);
		
		contentPane.add(textArea_7);
		textArea_8.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_8.setBackground(new Color(240 , 240 , 240));
		textArea_8.setBounds(200, 269, 60, 30);
		
		contentPane.add(textArea_8);
		textArea_9.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_9.setBackground(new Color(240 , 240 , 240));
		textArea_9.setBounds(10, 309, 175, 30);
		
		contentPane.add(textArea_9);
		textArea_10.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_10.setBackground(new Color(240 , 240 , 240));
		textArea_10.setBounds(200, 309, 60, 30);
		
		contentPane.add(textArea_10);
		txtrKbs.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_11.setBackground(new Color(240 , 240 , 240));
		txtrKbs.setBounds(274, 149, 50, 30);
		
		contentPane.add(txtrKbs);
		textArea_12.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_12.setBackground(new Color(240 , 240 , 240));
		textArea_12.setBounds(274, 189, 50, 30);
		
		contentPane.add(textArea_12);
		textArea_13.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_13.setBackground(new Color(240 , 240 , 240));
		textArea_13.setBounds(274, 229, 50, 30);
		
		contentPane.add(textArea_13);
		textArea_14.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_14.setBackground(new Color(240 , 240 , 240));
		textArea_14.setBounds(274, 269, 50, 30);
		
		contentPane.add(textArea_14);
		textArea_15.setFont(new Font("Monospaced", Font.PLAIN, 18));
//		textArea_15.setBackground(new Color(240 , 240 , 240));
		textArea_15.setBounds(274, 309, 50, 30);
		
		contentPane.add(textArea_15);

		switch_btn.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent itemEvent)
			{
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED)
				{
//					System.out.println("Selected");
					state_lbl.setText("正在监控");
//			        Thread t = new Thread(as);
//			        t.start();
					/*new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							switch_btn.setText("停止监控");
							state_lbl.setText("正在监控");
							state_lbl.setForeground(Color.GREEN);
							for (int i = 0; i < 10; i++)
							{
								global_lbl.setText(String.valueOf(i));
								try
								{
									Thread.sleep(1000);
								}
								catch (InterruptedException ex)
								{
									ex.printStackTrace();
								}
							}
						}
					}).start();*/
					MonitorMainBlock.initMMB() ;
					MonitorMainBlock.startMonitor();
				}
				else
				{
//					 System.out.println("Deselected");
					switch_btn.setText("开启监控");
					state_lbl.setText("就绪");
					state_lbl.setForeground(new Color(0, 0, 255));
//					as.stopRequest();
					MonitorMainBlock.stopMonitor() ;
				}
			}
		});
	}	    
	
	private static volatile boolean stopRequested;
	private Thread runThread;
	private static ArrayList<String> tempList = new ArrayList<String> () ;
	private static boolean listGot = true ;
	private static long currentData = 0 ;
	private static long currentDataDouble = 0 ;
	private static String tempStr ;
	private static JTextArea textArea = new JTextArea();
	private static JTextArea textArea_1 = new JTextArea();
	private static JTextArea textArea_2 = new JTextArea();
	private static JTextArea textArea_3 = new JTextArea();
	private static JTextArea textArea_4 = new JTextArea();
	private static JTextArea textArea_5 = new JTextArea();
	private static JTextArea textArea_6 = new JTextArea();
	private static JTextArea textArea_7 = new JTextArea();
	private static JTextArea textArea_8 = new JTextArea();
	private static JTextArea textArea_9 = new JTextArea();
	private static JTextArea textArea_10 = new JTextArea();
	private static JTextArea txtrKbs = new JTextArea();
	private static JTextArea textArea_12 = new JTextArea();
	private static JTextArea textArea_13 = new JTextArea();
	private static JTextArea textArea_14 = new JTextArea();
	private static JTextArea textArea_15 = new JTextArea();
	
	public static void backUpMode ()
	{
		state_lbl.setText("程序出错,请重启或联系管理员");
		state_lbl.setForeground(new Color(255, 0, 0));
		return ;
	}
	
	public static void getList ( ArrayList<String> input )
	{
		int count ;
		int i ;
		tempList = input ;
//		System.out.println ( "get input" ) ;
//		System.out.println ( tempList.size() ) ;
        switch_btn.setText("停止监控");
        if ( MainBlock.noError == true )
		{
        	state_lbl.setText("正在监控");
    		state_lbl.setForeground(Color.GREEN);
        	listGot = true ;
        	currentData = Long.valueOf( tempList.get ( 1 ) ) ;
        	currentDataDouble = currentData ;
        	switch ( getFormater () )
        	{	
        		case 0 :
        		{
        			textArea.setText ( "B/s" ) ;
        			break ;
        		}
        		case 1 :
        		{
        			textArea.setText ( "KB/s" ) ;
        			break ;
        		}
        		case 2 :
        		{
        			textArea.setText ( "MB/s" ) ;
        			break ;
        		}
        		case 3 :
        		{
        			textArea.setText ( "GB/s" ) ;
        			break ;
        		}
        	}
    		tempStr = String.valueOf ( currentDataDouble ) ;
    		if ( tempStr.contains ( "." ) && tempStr.length() > tempStr.indexOf ( "." ) + 3 ) 
    			tempStr = tempStr.substring ( 0, tempStr.indexOf ( "." ) + 3 ) ;
    		global_lbl.setText ( String.valueOf ( tempStr ) ) ;
    		contentPane.add ( global_lbl ) ;
    		contentPane.add ( textArea ) ;
    		for ( i = 2 ; i < tempList.size() && i < 11 ; i += 2 )
    		{
    			switch ( i )
    			{
    				case 2 :
    				{
    					textArea_1.setText ( tempList.get ( i ) ) ;
    					currentData = Long.valueOf( tempList.get ( i + 1 ) ) ;
    		        	currentDataDouble = currentData ;
    		        	switch ( getFormater () )
    		        	{	
    		        		case 0 :
    		        		{
    		        			txtrKbs.setText ( "B/s" ) ;
    		        			break ;
    		        		}
    		        		case 1 :
    		        		{
    		        			txtrKbs.setText ( "KB/s" ) ;
    		        			break ;
    		        		}
    		        		case 2 :
    		        		{
    		        			txtrKbs.setText ( "MB/s" ) ;
    		        			break ;
    		        		}
    		        		case 3 :
    		        		{
    		        			txtrKbs.setText ( "GB/s" ) ;
    		        			break ;
    		        		}
    		        	}
    	        		tempStr = String.valueOf ( currentDataDouble ) ;
    	        		if ( tempStr.contains ( "." ) && tempStr.length() > tempStr.indexOf ( "." ) + 3 ) 
    	        			tempStr = tempStr.substring ( 0, tempStr.indexOf ( "." ) + 3 ) ;
    	        		textArea_2.setText ( tempStr ) ;
    	        		break ;
    				}
    				case 4 :
    				{
    					textArea_3.setText ( tempList.get ( i ) ) ;
    					currentData = Long.valueOf( tempList.get ( i + 1 ) ) ;
    		        	currentDataDouble = currentData ;
    		        	switch ( getFormater () )
    		        	{	
    		        		case 0 :
    		        		{
    		        			textArea_12.setText ( "B/s" ) ;
    		        			break ;
    		        		}
    		        		case 1 :
    		        		{
    		        			textArea_12.setText ( "KB/s" ) ;
    		        			break ;
    		        		}
    		        		case 2 :
    		        		{
    		        			textArea_12.setText ( "MB/s" ) ;
    		        			break ;
    		        		}
    		        		case 3 :
    		        		{
    		        			textArea_12.setText ( "GB/s" ) ;
    		        			break ;
    		        		}
    		        	}
    	        		tempStr = String.valueOf ( currentDataDouble ) ;
    	        		if ( tempStr.contains ( "." ) && tempStr.length() > tempStr.indexOf ( "." ) + 3 ) 
    	        			tempStr = tempStr.substring ( 0, tempStr.indexOf ( "." ) + 3 ) ;
    	        		textArea_4.setText ( tempStr ) ;
    	        		break ;
    				}
    				case 6 :
    				{
    					textArea_5.setText ( tempList.get ( i ) ) ;
    					currentData = Long.valueOf( tempList.get ( i + 1 ) ) ;
    		        	currentDataDouble = currentData ;
    		        	switch ( getFormater () )
    		        	{	
    		        		case 0 :
    		        		{
    		        			textArea_13.setText ( "B/s" ) ;
    		        			break ;
    		        		}
    		        		case 1 :
    		        		{
    		        			textArea_13.setText ( "KB/s" ) ;
    		        			break ;
    		        		}
    		        		case 2 :
    		        		{
    		        			textArea_13.setText ( "MB/s" ) ;
    		        			break ;
    		        		}
    		        		case 3 :
    		        		{
    		        			textArea_13.setText ( "GB/s" ) ;
    		        			break ;
    		        		}
    		        	}
    	        		tempStr = String.valueOf ( currentDataDouble ) ;
    	        		if ( tempStr.contains ( "." ) && tempStr.length() > tempStr.indexOf ( "." ) + 3 ) 
    	        			tempStr = tempStr.substring ( 0, tempStr.indexOf ( "." ) + 3 ) ;
    	        		textArea_6.setText ( tempStr ) ;
    	        		break ;
    				}
    				case 8 :
    				{
    					textArea_7.setText ( tempList.get ( i ) ) ;
    					currentData = Long.valueOf( tempList.get ( i + 1 ) ) ;
    		        	currentDataDouble = currentData ;
    		        	switch ( getFormater () )
    		        	{	
    		        		case 0 :
    		        		{
    		        			textArea_14.setText ( "B/s" ) ;
    		        			break ;
    		        		}
    		        		case 1 :
    		        		{
    		        			textArea_14.setText ( "KB/s" ) ;
    		        			break ;
    		        		}
    		        		case 2 :
    		        		{
    		        			textArea_14.setText ( "MB/s" ) ;
    		        			break ;
    		        		}
    		        		case 3 :
    		        		{
    		        			textArea_14.setText ( "GB/s" ) ;
    		        			break ;
    		        		}
    		        	}
    	        		tempStr = String.valueOf ( currentDataDouble ) ;
    	        		if ( tempStr.contains ( "." ) && tempStr.length() > tempStr.indexOf ( "." ) + 3 ) 
    	        			tempStr = tempStr.substring ( 0, tempStr.indexOf ( "." ) + 3 ) ;
    	        		textArea_8.setText ( tempStr ) ;
    	        		break ;
    				}
    				case 10 :
    				{
    					textArea_9.setText ( tempList.get ( i ) ) ;
    					currentData = Long.valueOf( tempList.get ( i + 1 ) ) ;
    		        	currentDataDouble = currentData ;
    		        	switch ( getFormater () )
    		        	{	
    		        		case 0 :
    		        		{
    		        			textArea_15.setText ( "B/s" ) ;
    		        			break ;
    		        		}
    		        		case 1 :
    		        		{
    		        			textArea_15.setText ( "KB/s" ) ;
    		        			break ;
    		        		}
    		        		case 2 :
    		        		{
    		        			textArea_15.setText ( "MB/s" ) ;
    		        			break ;
    		        		}
    		        		case 3 :
    		        		{
    		        			textArea_15.setText ( "GB/s" ) ;
    		        			break ;
    		        		}
    		        	}
    	        		tempStr = String.valueOf ( currentDataDouble ) ;
    	        		if ( tempStr.contains ( "." ) && tempStr.length() > tempStr.indexOf ( "." ) + 3 ) 
    	        			tempStr = tempStr.substring ( 0, tempStr.indexOf ( "." ) + 3 ) ;
    	        		textArea_10.setText ( tempStr ) ;
    	        		break ;
    				}
    			}
    		}
//    		System.out.println ( "1" ) ;
    		switch ( i )
    		{
    			case 2 :
    			{
    				textArea_1.setText ( " " ) ;
    				textArea_2.setText ( " " ) ;
    				txtrKbs.setText ( " " ) ;
    				contentPane.add ( textArea_1 ) ;
    				contentPane.add ( textArea_2 ) ;
    				contentPane.add ( txtrKbs ) ;
    			}
    			case 4 :
    			{
    				textArea_3.setText ( " " ) ;
    				textArea_4.setText ( " " ) ;
    				textArea_12.setText ( " " ) ;
    				contentPane.add ( textArea_3 ) ;
    				contentPane.add ( textArea_4 ) ;
    				contentPane.add ( textArea_12 ) ;
    			}
    			case 6 :
    			{
    				textArea_5.setText ( "" ) ;
    				textArea_6.setText ( "" ) ;
    				textArea_13.setText ( "" ) ;
    				contentPane.add ( textArea_5 ) ;
    				contentPane.add ( textArea_6 ) ;
    				contentPane.add ( textArea_13 ) ;
    			}
    			case 8 :
    			{
    				textArea_7.setText ( "" ) ;
    				textArea_8.setText ( "" ) ;
    				textArea_14.setText ( "" ) ;
    				contentPane.add ( textArea_7 ) ;
    				contentPane.add ( textArea_8 ) ;
    				contentPane.add ( textArea_14 ) ;
    			}
    			case 10 :
    			{
    				textArea_9.setText ( "" ) ;
    				textArea_10.setText ( "" ) ;
    				textArea_15.setText ( "" ) ;
    				contentPane.add ( textArea_9 ) ;
    				contentPane.add ( textArea_10 ) ;
    				contentPane.add ( textArea_15 ) ;
    			}
    			default :
    				break ;
    		}
		}
	}
	
	private static int getFormater ()
	{
		int step = 0 ;
		while ( currentDataDouble > 1024 )
		{
			currentDataDouble /= (double) 1024 ;
			step ++ ;
			if ( step == 3 )
				break ;
		}
		return step ;
	}
/*	
	public class AlternateStop extends Object implements Runnable {
	    public void run() {
	    	int i ;
	    	System.out.println ( "start" ) ;
	        runThread = Thread.currentThread();
	        stopRequested = false;
	        int count = 0;
	        switch_btn.setText("停止监控");
			state_lbl.setText("正在监控");
			state_lbl.setForeground(Color.GREEN);
	        while ( !stopRequested ) 
	        {
	        	System.out.println ( "loop" ) ;
	        	count = 0 ;
	        	if ( listGot )
	        	{
	        		continue ;
//	        		System.out.println ( "waiting" ) ;
//	        		Thread.yield() ;
	        	}
	        	listGot = true ;
	        	currentData = Long.valueOf( tempList.get ( 1 ) ) ;
	        	currentDataDouble = currentData ;
	        	switch ( getFormater () )
	        	{	
	        		case 0 :
	        		{
	        			textArea.setText ( "B/s" ) ;
	        			break ;
	        		}
	        		case 1 :
	        		{
	        			textArea.setText ( "KB/s" ) ;
	        			break ;
	        		}
	        		case 2 :
	        		{
	        			textArea.setText ( "MB/s" ) ;
	        			break ;
	        		}
	        		case 3 :
	        		{
	        			textArea.setText ( "GB/s" ) ;
	        			break ;
	        		}
	        	}
        		tempStr = String.valueOf ( currentDataDouble ) ;
        		if ( tempStr.contains ( "." ) && tempStr.length() > tempStr.indexOf ( "." ) + 3 ) 
        			tempStr = tempStr.substring ( 0, tempStr.indexOf ( "." ) + 3 ) ;
        		global_lbl.setText ( String.valueOf ( tempStr ) ) ;
        		for ( i = 2 ; i < tempList.size() && i < 11 ; i += 2 )
        		{
        			switch ( i )
        			{
        				case 2 :
        				{
        					textArea_1.setText ( tempList.get ( i ) ) ;
        					currentData = Long.valueOf( tempList.get ( i + 1 ) ) ;
        		        	currentDataDouble = currentData ;
        		        	switch ( getFormater () )
        		        	{	
        		        		case 0 :
        		        		{
        		        			textArea_11.setText ( "B/s" ) ;
        		        			break ;
        		        		}
        		        		case 1 :
        		        		{
        		        			textArea_11.setText ( "KB/s" ) ;
        		        			break ;
        		        		}
        		        		case 2 :
        		        		{
        		        			textArea_11.setText ( "MB/s" ) ;
        		        			break ;
        		        		}
        		        		case 3 :
        		        		{
        		        			textArea_11.setText ( "GB/s" ) ;
        		        			break ;
        		        		}
        		        	}
        	        		tempStr = String.valueOf ( currentDataDouble ) ;
        	        		if ( tempStr.contains ( "." ) && tempStr.length() > tempStr.indexOf ( "." ) + 3 ) 
        	        			tempStr = tempStr.substring ( 0, tempStr.indexOf ( "." ) + 3 ) ;
        	        		textArea_2.setText ( tempStr ) ;
        	        		break ;
        				}
        				case 4 :
        				{
        					textArea_3.setText ( tempList.get ( i ) ) ;
        					currentData = Long.valueOf( tempList.get ( i + 1 ) ) ;
        		        	currentDataDouble = currentData ;
        		        	switch ( getFormater () )
        		        	{	
        		        		case 0 :
        		        		{
        		        			textArea_12.setText ( "B/s" ) ;
        		        			break ;
        		        		}
        		        		case 1 :
        		        		{
        		        			textArea_12.setText ( "KB/s" ) ;
        		        			break ;
        		        		}
        		        		case 2 :
        		        		{
        		        			textArea_12.setText ( "MB/s" ) ;
        		        			break ;
        		        		}
        		        		case 3 :
        		        		{
        		        			textArea_12.setText ( "GB/s" ) ;
        		        			break ;
        		        		}
        		        	}
        	        		tempStr = String.valueOf ( currentDataDouble ) ;
        	        		if ( tempStr.contains ( "." ) && tempStr.length() > tempStr.indexOf ( "." ) + 3 ) 
        	        			tempStr = tempStr.substring ( 0, tempStr.indexOf ( "." ) + 3 ) ;
        	        		textArea_4.setText ( tempStr ) ;
        	        		break ;
        				}
        				case 6 :
        				{
        					textArea_5.setText ( tempList.get ( i ) ) ;
        					currentData = Long.valueOf( tempList.get ( i + 1 ) ) ;
        		        	currentDataDouble = currentData ;
        		        	switch ( getFormater () )
        		        	{	
        		        		case 0 :
        		        		{
        		        			textArea_13.setText ( "B/s" ) ;
        		        			break ;
        		        		}
        		        		case 1 :
        		        		{
        		        			textArea_13.setText ( "KB/s" ) ;
        		        			break ;
        		        		}
        		        		case 2 :
        		        		{
        		        			textArea_13.setText ( "MB/s" ) ;
        		        			break ;
        		        		}
        		        		case 3 :
        		        		{
        		        			textArea_13.setText ( "GB/s" ) ;
        		        			break ;
        		        		}
        		        	}
        	        		tempStr = String.valueOf ( currentDataDouble ) ;
        	        		if ( tempStr.contains ( "." ) && tempStr.length() > tempStr.indexOf ( "." ) + 3 ) 
        	        			tempStr = tempStr.substring ( 0, tempStr.indexOf ( "." ) + 3 ) ;
        	        		textArea_6.setText ( tempStr ) ;
        	        		break ;
        				}
        				case 8 :
        				{
        					textArea_7.setText ( tempList.get ( i ) ) ;
        					currentData = Long.valueOf( tempList.get ( i + 1 ) ) ;
        		        	currentDataDouble = currentData ;
        		        	switch ( getFormater () )
        		        	{	
        		        		case 0 :
        		        		{
        		        			textArea_14.setText ( "B/s" ) ;
        		        			break ;
        		        		}
        		        		case 1 :
        		        		{
        		        			textArea_14.setText ( "KB/s" ) ;
        		        			break ;
        		        		}
        		        		case 2 :
        		        		{
        		        			textArea_14.setText ( "MB/s" ) ;
        		        			break ;
        		        		}
        		        		case 3 :
        		        		{
        		        			textArea_14.setText ( "GB/s" ) ;
        		        			break ;
        		        		}
        		        	}
        	        		tempStr = String.valueOf ( currentDataDouble ) ;
        	        		if ( tempStr.contains ( "." ) && tempStr.length() > tempStr.indexOf ( "." ) + 3 ) 
        	        			tempStr = tempStr.substring ( 0, tempStr.indexOf ( "." ) + 3 ) ;
        	        		textArea_8.setText ( tempStr ) ;
        	        		break ;
        				}
        				case 10 :
        				{
        					textArea_9.setText ( tempList.get ( i ) ) ;
        					currentData = Long.valueOf( tempList.get ( i + 1 ) ) ;
        		        	currentDataDouble = currentData ;
        		        	switch ( getFormater () )
        		        	{	
        		        		case 0 :
        		        		{
        		        			textArea_15.setText ( "B/s" ) ;
        		        			break ;
        		        		}
        		        		case 1 :
        		        		{
        		        			textArea_15.setText ( "KB/s" ) ;
        		        			break ;
        		        		}
        		        		case 2 :
        		        		{
        		        			textArea_15.setText ( "MB/s" ) ;
        		        			break ;
        		        		}
        		        		case 3 :
        		        		{
        		        			textArea_15.setText ( "GB/s" ) ;
        		        			break ;
        		        		}
        		        	}
        	        		tempStr = String.valueOf ( currentDataDouble ) ;
        	        		if ( tempStr.contains ( "." ) && tempStr.length() > tempStr.indexOf ( "." ) + 3 ) 
        	        			tempStr = tempStr.substring ( 0, tempStr.indexOf ( "." ) + 3 ) ;
        	        		textArea_10.setText ( tempStr ) ;
        	        		break ;
        				}
        			}
        		}
        		while ( i < 11 )
        		{
        			switch ( i )
        			{
        				case 2 :
        				{
        					textArea_1.setText ( "" ) ;
        					textArea_2.setText ( "" ) ;
        					textArea_11.setText ( "" ) ;
        					break ;
        				}
        				case 4 :
        				{
        					textArea_3.setText ( "" ) ;
        					textArea_4.setText ( "" ) ;
        					textArea_12.setText ( "" ) ;
        					break ;
        				}
        				case 6 :
        				{
        					textArea_5.setText ( "" ) ;
        					textArea_6.setText ( "" ) ;
        					textArea_13.setText ( "" ) ;
        					break ;
        				}
        				case 8 :
        				{
        					textArea_7.setText ( "" ) ;
        					textArea_8.setText ( "" ) ;
        					textArea_14.setText ( "" ) ;
        					break ;
        				}
        				case 10 :
        				{
        					textArea_9.setText ( "" ) ;
        					textArea_10.setText ( "" ) ;
        					textArea_15.setText ( "" ) ;
        					break ;
        				}
        			}
        		}
	            try 
	            {
	                Thread.sleep(1000);
	            } 
	            catch ( InterruptedException x ) 
	            {
	                Thread.currentThread().interrupt(); // re-assert interrupt
	            }
	        }
	        
	        System.out.println("stoped");
	    }
	    public void stopRequest() {
	        stopRequested = true;
	        if ( runThread != null ) {
	            runThread.interrupt();
	        }
	    }
	}
*/
}