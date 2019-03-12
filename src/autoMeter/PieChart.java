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
          JFreeChart chart = ChartFactory.createPieChart3D("数据使用量分布图",data,true,false,false);  
        //设置百分比  
          PiePlot pieplot = (PiePlot) chart.getPlot();  
          DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题  
          NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象  
          StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//获得StandardPieSectionLabelGenerator对象  
          pieplot.setLabelGenerator(sp1);//设置饼图显示百分比  
        
      //没有数据的时候显示的内容  
          pieplot.setNoDataMessage("无数据显示");  
          pieplot.setCircular(false);  
          pieplot.setLabelGap(0.02D);  
        
          pieplot.setIgnoreNullValues(true);//设置不显示空值  
          pieplot.setIgnoreZeroValues(true);//设置不显示负值  
          frame1=new ChartPanel (chart,true);  
          frame1.setSize(800, 600);
          chart.getTitle().setFont(new Font("微软雅黑",Font.BOLD,20));//设置标题字体  
          PiePlot piePlot= (PiePlot) chart.getPlot();//获取图表区域对象  
          piePlot.setLabelFont(new Font("微软雅黑",Font.BOLD,12));//解决乱码  
          chart.getLegend().setItemFont(new Font("微软雅黑",Font.BOLD,12));  
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
        dataset.setValue("苹果",100);  
        dataset.setValue("梨子",200);  
        dataset.setValue("葡萄",300);  
        dataset.setValue("香蕉",400);  
        dataset.setValue("荔枝",500);  */
        return dataset;  
}  
    public ChartPanel getChartPanel(){  
        return frame1;  
          
    }  


	public static void main(String args[])
	{
		final JFrame frame = new JFrame("Java数据统计图");
//		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton return_btn = new JButton("返回");
		return_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI gui = new GUI();
				gui.setVisible(true);
				frame.dispose();
			}
		});
		return_btn.setFont(new Font("微软雅黑", Font.BOLD, 20));
		JButton clean_btn = new JButton("清除历史记录");
		clean_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DataBaseOperation.truncateTable();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(frame, "已清除历史记录", "Warning", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		clean_btn.setFont(new Font("微软雅黑", Font.BOLD, 20));
		clean_btn.setBounds(171, 608, 175, 64);
		ChartPanel piechart = new PieChart().getChartPanel();
		return_btn.setBounds(460, 608, 199, 64);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(piechart); 
		frame.getContentPane().add(clean_btn);
		frame.getContentPane().add(return_btn);
		// frame.add(new BarChart1().getChartPanel()); //添加柱形图的另一种效果
//		 frame.add(new PieChart().getChartPanel()); //添加饼状图
		// frame.add(new TimeSeriesChart().getChartPanel()); //添加折线图
		frame.setSize(810, 710);
		// 设置居中
				Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
				Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
				int screenWidth = screenSize.width / 2; // 获取屏幕的宽
				int screenHeight = screenSize.height / 2; // 获取屏幕的高
				int height = frame.getHeight();
				int width = frame.getWidth();
				frame.setLocation(screenWidth - width / 2, screenHeight - height / 2);
				// 设置居中结束
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
	}
}