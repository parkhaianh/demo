package test.haianh;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import common.FFT;

public class SubSpectrumPanel extends JPanel{

	/**
	 * @author haianh
	 */
	private static final long serialVersionUID = 1L;
	

	public SubSpectrumPanel(int data,int upperLimit,int lowerLimit,XYSeriesCollection seriesCollection){
		super();
		JFrame newJFrame = new JFrame("news");
		newJFrame.setTitle("HISTOGRAM");
		newJFrame.setLayout(new BorderLayout());
		newJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		XYSeriesCollection sub1 = new XYSeriesCollection(seriesCollection.getSeries(0));
		XYSeriesCollection sub2 = new XYSeriesCollection(seriesCollection.getSeries(1));
		newJFrame.add(new ChartPanel(createChart(upperLimit,lowerLimit,sub1,Color.RED,TitleEnums.valueOf(data+1))), BorderLayout.WEST);
		newJFrame.add(new ChartPanel(createChart(upperLimit,lowerLimit,sub2,Color.BLUE,TitleEnums.valueOf(data+4))),BorderLayout.EAST);
		newJFrame.setVisible(true);
		newJFrame.pack();
	}
	
	public JFreeChart createChart(int up, int low, XYSeriesCollection seriesCollection ,Color color, TitleEnums tittle){
		XYSeriesCollection set = getCollection(seriesCollection);
		JFreeChart chart = ChartFactory.createHistogram(tittle.getTittle(), TitleEnums.FREQUENCY.getTittle(), TitleEnums.MAGNITUDE.getTittle(), set, PlotOrientation.VERTICAL, false, false, false);
		ValueMarker markerUp = new ValueMarker(up);  
		markerUp.setPaint(Color.black);
		ValueMarker markerDwn = new ValueMarker(low);
		markerDwn.setPaint(Color.black);
		chart.getXYPlot().getRenderer().setSeriesPaint(0, color);
		chart.getXYPlot().addRangeMarker(markerUp);
		chart.getXYPlot().addRangeMarker(markerDwn);
		chart.getXYPlot().getRangeAxis().setUpperBound(3000);
		chart.getXYPlot().getRangeAxis().setLowerBound(0);
		return chart;
	}
	
	public XYSeriesCollection getCollection(XYSeriesCollection seriesCollection){
		XYSeries seriesTemp = seriesCollection.getSeries(0);
		int count = seriesTemp.getItemCount();
		int n = getSmallestMul(count);
		double[] img = new double[n];
		double[] mag = new double[n];
		List<?> items = seriesTemp.getItems();
		for (int i=0; i< n; i++)
	        {
	            XYDataItem i0 = (XYDataItem) items.get(i);
	            mag[i] = i0.getYValue();
	            img[i] = 0;
	        }
		XYSeriesCollection collection = new XYSeriesCollection();
		double[] output = FFT.fft(mag, img, true);
		XYSeries series = new XYSeries("test");
		for(int i = 1;i <= output.length;i++){
			series.add(i*10/n, output[i-1]);
		}
		collection.addSeries(series);
		return collection;
	}
	
	private int getSmallestMul(int input){
		int temp = (int) (Math.log(input)/Math.log(2));
		return (int) Math.pow(2, temp);
	}
}
