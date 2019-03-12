package autoMeter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.DecimalFormat;  
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;  
import org.jfree.chart.plot.PiePlot;  
import org.jfree.data.general.DefaultPieDataset;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;  
  
public class PieChart extends JFrame{  
    ChartPanel frame1;  
    public PieChart(){  
          DefaultPieDataset data = getDataSet();  
          JFreeChart chart = ChartFactory.createPieChart3D("����ʹ�����ֲ�ͼ",data,true,false,false);  
        //���ðٷֱ�  
          PiePlot pieplot = (PiePlot) chart.getPlot();  
          DecimalFormat df = new DecimalFormat("0.00%");//���һ��DecimalFormat������Ҫ������С������  
          NumberFormat nf = NumberFormat.getNumberInstance();//���һ��NumberFormat����  
          StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//���StandardPieSectionLabelGenerator����  
          pieplot.setLabelGenerator(sp1);//���ñ�ͼ��ʾ�ٷֱ�  
        
      //û�����ݵ�ʱ����ʾ������  
          pieplot.setNoDataMessage("��������ʾ");  
          pieplot.setCircular(false);  
          pieplot.setLabelGap(0.02D);  
        
          pieplot.setIgnoreNullValues(true);//���ò���ʾ��ֵ  
          pieplot.setIgnoreZeroValues(true);//���ò���ʾ��ֵ  
          frame1=new ChartPanel (chart,true);  
          frame1.setSize(800, 600);
          chart.getTitle().setFont(new Font("΢���ź�",Font.BOLD,20));//���ñ�������  
          PiePlot piePlot= (PiePlot) chart.getPlot();//��ȡͼ���������  
          piePlot.setLabelFont(new Font("΢���ź�",Font.BOLD,12));//�������  
          chart.getLegend().setItemFont(new Font("΢���ź�",Font.BOLD,12));  
    }  
    private static DefaultPieDataset getDataSet() {  
        DefaultPieDataset dataset = new DefaultPieDataset();  
        ArrayList<String> temp = null ;
        try 
        {
			temp = DataBaseOperation.Query ( "select * from data ORDER BY appData desc" ) ;
		} catch (SQLException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        for(int i=0 ; i < temp.size()  ; i += 2 )
        {
        	int j ;
        	j = i + 1 ;
//        	System.out.println(temp.get(i));
//        	System.out.println(temp.get(j));
        	if ( temp.get(i).trim().equals ( "TOTAL" ) == false )
        		dataset.setValue( temp.get ( i ).trim() , Long.valueOf(temp.get ( i + 1 ).trim() ) );
        }

/*
        dataset.setValue("ƻ��",100);  
        dataset.setValue("����",200);  
        dataset.setValue("����",300);  
        dataset.setValue("�㽶",400);  
        dataset.setValue("��֦",500);  */
        return dataset;  
}  
    public ChartPanel getChartPanel(){  
        return frame1;  
          
    }  


	public static void main(String args[])
	{
		final JFrame frame = new JFrame("Java����ͳ��ͼ");
//		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton return_btn = new JButton("����");
		return_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI gui = new GUI();
				gui.setVisible(true);
				frame.dispose();
			}
		});
		return_btn.setFont(new Font("΢���ź�", Font.BOLD, 20));
		JButton clean_btn = new JButton("�����ʷ��¼");
		clean_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DataBaseOperation.truncateTable();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(frame, "�������ʷ��¼", "Warning", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		clean_btn.setFont(new Font("΢���ź�", Font.BOLD, 20));
		clean_btn.setBounds(171, 608, 175, 64);
		ChartPanel piechart = new PieChart().getChartPanel();
		return_btn.setBounds(460, 608, 199, 64);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(piechart); 
		frame.getContentPane().add(clean_btn);
		frame.getContentPane().add(return_btn);
		// frame.add(new BarChart1().getChartPanel()); //�������ͼ����һ��Ч��
//		 frame.add(new PieChart().getChartPanel()); //��ӱ�״ͼ
		// frame.add(new TimeSeriesChart().getChartPanel()); //�������ͼ
		frame.setSize(810, 710);
		// ���þ���
				Toolkit kit = Toolkit.getDefaultToolkit(); // ���幤�߰�
				Dimension screenSize = kit.getScreenSize(); // ��ȡ��Ļ�ĳߴ�
				int screenWidth = screenSize.width / 2; // ��ȡ��Ļ�Ŀ�
				int screenHeight = screenSize.height / 2; // ��ȡ��Ļ�ĸ�
				int height = frame.getHeight();
				int width = frame.getWidth();
				frame.setLocation(screenWidth - width / 2, screenHeight - height / 2);
				// ���þ��н���
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
	}
}