package autoMeter;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.ClassicInnerBorderPainter;
import org.jvnet.substance.border.NullBorderPainter;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.painter.ClassicGradientPainter;
import org.jvnet.substance.painter.MatteGradientPainter;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.CremeSkin;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;
import org.jvnet.substance.theme.SubstanceEbonyTheme;
import org.jvnet.substance.title.ClassicTitlePainter;
import org.jvnet.substance.title.FlatTitlePainter;
import org.jvnet.substance.title.Glass3DTitlePainter;
import org.jvnet.substance.watermark.SubstanceBinaryWatermark;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.Font;

public class GUI extends JFrame {

	private JPanel contentPane;
	public PieChart barchart = new PieChart();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		// 装载可选择的主题  
        try {  
            //设置外观  
            UIManager.setLookAndFeel(new SubstanceLookAndFeel());  
            JFrame.setDefaultLookAndFeelDecorated(true);  
            //设置主题   
            SubstanceLookAndFeel.setSkin(new CremeSkin());
//          SubstanceLookAndFeel.setCurrentTheme(new SubstanceEbonyTheme());  
            //设置按钮外观  
            SubstanceLookAndFeel.setCurrentButtonShaper(new StandardButtonShaper());  
            //设置水印  
//          SubstanceLookAndFeel.setCurrentWatermark();  
            //设置边框  
            SubstanceLookAndFeel.setCurrentBorderPainter(new ClassicInnerBorderPainter());  
            //设置渐变渲染  
            SubstanceLookAndFeel.setCurrentGradientPainter(new ClassicGradientPainter());  
            //设置标题  
            SubstanceLookAndFeel.setCurrentTitlePainter(new ClassicTitlePainter());  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					GUI frame = new GUI();
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
	
	public GUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("img" + File.separator + "icon.png"));
		setResizable(false);
		setTitle("AutoMeter ver.1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		// 设置居中
		Toolkit kit= Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize();  // 获取屏幕的尺寸
		int screenWidth = screenSize.width / 2; // 获取屏幕的宽
		int screenHeight = screenSize.height / 2; // 获取屏幕的高
		int height = this.getHeight();
		int width = this.getWidth();
		setLocation(screenWidth - width / 2, screenHeight - height / 2);
		// 设置居中结束
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton monitor_btn = new JButton("监控流量");
		monitor_btn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		monitor_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{		
				monitor Monitor = new monitor();
				Monitor.setVisible(true);
				dispose();
			}
		});
		monitor_btn.setBounds(67, 217, 122, 45);
		contentPane.add(monitor_btn);
		
		JButton data_btn = new JButton("\u6D41\u91CF\u4F7F\u7528");
		data_btn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		data_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				barchart.main(null);
				dispose();
			}
		});
		data_btn.setBounds(259, 217, 122, 45);
		contentPane.add(data_btn);
		
		Icon logoIcon = new ImageIcon ( "img/logo.jpg" ) ;
		JLabel info_label = new JLabel("welcome information" , logoIcon ,JLabel.CENTER);
		info_label.setVerticalAlignment(SwingConstants.BOTTOM);
		info_label.setBounds(69, 10, 312, 197);
		contentPane.add(info_label);
		
	}  
}
