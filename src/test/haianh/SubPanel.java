package test.haianh;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.xy.XYSeriesCollection;

public class SubPanel extends JPanel{

	/**
	 * @author haianh
	 */
	private static final long serialVersionUID = 1L;
	
	
	public SubPanel(int data,int upperLimit,int lowerLimit,XYSeriesCollection seriesCollection){
		super();
		JFrame newJFrame = new JFrame("news");
		ChartPanel panel = new ChartPanel(createChart(upperLimit,lowerLimit,seriesCollection));
		JPanel sub = new JPanel();
		newJFrame.setLayout(new BorderLayout());
 		newJFrame.setTitle(TitleEnums.valueOf(data+1).getTittle());
		newJFrame.setBounds(100, 100, 1000, 600);
		newJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newJFrame.add(panel, BorderLayout.CENTER);
		newJFrame.add(sub,BorderLayout.SOUTH);
		JButton button = new JButton("SPECTRUM");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SubSpectrumPanel sub = new SubSpectrumPanel(data, upperLimit, lowerLimit, seriesCollection);
				sub.setVisible(true);
			}
		});
		button.setBounds(50, 0, 60, 30);
		sub.add(button);
		newJFrame.setVisible(true);
	}
	
	public JFreeChart createChart(int up, int low, XYSeriesCollection seriesCollection ){
		JFreeChart chart = ChartFactory.createXYLineChart("Omega Accelerator", "time", "acceleration", seriesCollection);
		ValueMarker markerUp = new ValueMarker(up);  
		markerUp.setPaint(Color.black);
		ValueMarker markerDwn = new ValueMarker(low);
		markerDwn.setPaint(Color.black);
		chart.getXYPlot().addRangeMarker(markerUp);
		chart.getXYPlot().addRangeMarker(markerDwn);
		chart.getXYPlot().getRangeAxis().setUpperBound(2000);
		chart.getXYPlot().getRangeAxis().setLowerBound(-2000);
		return chart;
	}
}
