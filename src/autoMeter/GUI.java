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
		// װ�ؿ�ѡ�������  
        try {  
            //�������  
            UIManager.setLookAndFeel(new SubstanceLookAndFeel());  
            JFrame.setDefaultLookAndFeelDecorated(true);  
            //��������   
            SubstanceLookAndFeel.setSkin(new CremeSkin());
//          SubstanceLookAndFeel.setCurrentTheme(new SubstanceEbonyTheme());  
            //���ð�ť���  
            SubstanceLookAndFeel.setCurrentButtonShaper(new StandardButtonShaper());  
            //����ˮӡ  
//          SubstanceLookAndFeel.setCurrentWatermark();  
            //���ñ߿�  
            SubstanceLookAndFeel.setCurrentBorderPainter(new ClassicInnerBorderPainter());  
            //���ý�����Ⱦ  
            SubstanceLookAndFeel.setCurrentGradientPainter(new ClassicGradientPainter());  
            //���ñ���  
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
		// ���þ���
		Toolkit kit= Toolkit.getDefaultToolkit(); // ���幤�߰�
		Dimension screenSize = kit.getScreenSize();  // ��ȡ��Ļ�ĳߴ�
		int screenWidth = screenSize.width / 2; // ��ȡ��Ļ�Ŀ�
		int screenHeight = screenSize.height / 2; // ��ȡ��Ļ�ĸ�
		int height = this.getHeight();
		int width = this.getWidth();
		setLocation(screenWidth - width / 2, screenHeight - height / 2);
		// ���þ��н���
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton monitor_btn = new JButton("�������");
		monitor_btn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
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
		data_btn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
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
