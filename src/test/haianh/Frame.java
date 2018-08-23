package test.haianh;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fazecast.jSerialComm.SerialPort;

import common.CommonMethod;
import entity.Acceleration_info;

import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.Font;

public class Frame {

	private JFrame frame;
	private SerialPort serialPort = null;;
	private static int[] arrayBaudRate = {9600, 19200, 38400, 57600, 115200, 230400, 460800, 921600};
	private JTextField upperText;
	private JTextField lowerText;
	private String upper = "0";
	private String lower = "0";
	private XYSeries series1X = new XYSeries("Sensor 1 "+TitleEnums.valueOf(1).getTittle());
	private XYSeries series2X = new XYSeries("Sensor 2 "+TitleEnums.valueOf(1).getTittle());
	private XYSeries series1Y = new XYSeries("Sensor 1 "+TitleEnums.valueOf(2).getTittle());
	private XYSeries series2Y = new XYSeries("Sensor 2 "+TitleEnums.valueOf(2).getTittle());
	private XYSeries series1Z = new XYSeries("Sensor 1 "+TitleEnums.valueOf(3).getTittle());
	private XYSeries series2Z = new XYSeries("Sensor 2 "+TitleEnums.valueOf(3).getTittle());
	
	private XYSeriesCollection seriesCollectionX = new XYSeriesCollection();
	private XYSeriesCollection seriesCollectionY = new XYSeriesCollection();
	private XYSeriesCollection seriesCollectionZ = new XYSeriesCollection();
	
	private static double signal1;
	private static double signal2;
	private static double signal3;
	private static double signal4;
	private static double signal5;
	private static double signal6;
	static int index = 0;
	final long A_MINUTE = 600;
	
	/**
	 * Launch the application.
	 * @author haianh
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame window = new Frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel jLabel = new JLabel("label");
		jLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
		jLabel.setBounds(109, 24, 196, 14);
		jLabel.setVisible(false);
		JComboBox<String> portList = new JComboBox<String>();
		portList.setBounds(33, 75, 107, 22);
		for(int i = 0;i <listPort().size();i++){
			portList.addItem(listPort().get(i).getSystemPortName());
		}
		JButton btnConnect = new JButton("CONNECT");
		btnConnect.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnConnect.setBounds(286, 74, 126, 25);
		JComboBox<String> listBaudRate = new JComboBox<String>();
		listBaudRate.setBounds(150, 75, 126, 22);
		for(int i : arrayBaudRate){
			listBaudRate.addItem(String.valueOf(i));
		}
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.add(portList);
		topPanel.add(listBaudRate);
		topPanel.add(btnConnect);
		topPanel.add(jLabel);
		frame.getContentPane().add(topPanel,BorderLayout.CENTER);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(84, 134, 111, 35);
		comboBox.addItem("chanel X");
		comboBox.addItem("chanel Y");
		comboBox.addItem("chanel Z");
		topPanel.add(comboBox);
		
		upperText = new JTextField();
		upperText.setBounds(84, 207, 86, 20);
		upperText.setText("0");
		upperText.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setIndex();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setIndex();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setIndex();
			}
			
			public void setIndex(){
				upper = upperText.getText();
			}
		});
		
		topPanel.add(upperText);
		upperText.setColumns(10);
		
		lowerText = new JTextField();
		lowerText.setBounds(235, 207, 86, 20);
		lowerText.setText("0");
		lowerText.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setIndex();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setIndex();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setIndex();
			}
			public void setIndex(){
				lower = lowerText.getText();
			}
		});
		topPanel.add(lowerText);
		lowerText.setColumns(10);
		
		JButton btnChanel = new JButton("SIGNAL");
		btnChanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!isNumeric(lower) || !isNumeric(upper)) {
					jLabel.setText("Wrong Format");
					jLabel.setForeground(Color.red);
					return;
				}
				int tempUp = Integer.parseInt(upper);
				int tempLow = Integer.parseInt(lower);
				if(comboBox.getSelectedIndex()==0) {
					SubPanel subPanel = new SubPanel(comboBox.getSelectedIndex(),tempUp,tempLow, seriesCollectionX);
					subPanel.setVisible(true);
				} else if(comboBox.getSelectedIndex() == 1) {
					SubPanel subPanel = new SubPanel(comboBox.getSelectedIndex(),tempUp,tempLow, seriesCollectionY);
					subPanel.setVisible(true);
				} else if(comboBox.getSelectedIndex() == 2) {
					SubPanel subPanel = new SubPanel(comboBox.getSelectedIndex(),tempUp,tempLow, seriesCollectionZ);
					subPanel.setVisible(true);
				}
				
			}
		});
		btnChanel.setFont(new Font("Consolas", Font.PLAIN, 14));

		

		btnChanel.setBounds(235, 134, 105, 35);
		btnChanel.setEnabled(false);
		topPanel.add(btnChanel);
		
		JLabel lblUpperlimit = new JLabel("UpperLimit");
		lblUpperlimit.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblUpperlimit.setBounds(94, 180, 76, 14);
		topPanel.add(lblUpperlimit);
		
		JLabel lblLowerlimit = new JLabel("LowerLimit");
		lblLowerlimit.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblLowerlimit.setBounds(245, 180, 76, 14);
		topPanel.add(lblLowerlimit);
		
		JLabel lblComport = new JLabel("ComPort");
		lblComport.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblComport.setBounds(36, 51, 76, 14);
		topPanel.add(lblComport);
		
		JLabel lblBaudrate = new JLabel("BaudRate");
		lblBaudrate.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblBaudrate.setBounds(178, 49, 76, 14);
		topPanel.add(lblBaudrate);
		
		
		
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnConnect.getText().toLowerCase().equals("connect")){
					serialPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
					serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					serialPort.setBaudRate(Integer.valueOf(listBaudRate.getSelectedItem().toString()));
					if(serialPort.openPort()){
						jLabel.setText("successful connection");
						jLabel.setVisible(true);
						btnConnect.setText("disconnect");
						portList.setEnabled(false);
						listBaudRate.setEnabled(false);
						btnChanel.setEnabled(true);
						Thread thread = new Thread(){
							@Override
							public void run() {
								try {
									Thread.sleep(200);
								} catch (Exception e) {
									// TODO: handle exception
								}
								Scanner scanner = new Scanner(serialPort.getInputStream());
								int x = 0;
								int y = 0;
								int z = 0;
								while(scanner.hasNextLine()){
									try {
										String result = scanner.nextLine();
										String line1 = result.split(",")[0];
										String line2 = result.split(",")[3];
										String line3 = result.split(",")[1];
										String line4 = result.split(",")[4];
										String line5 = result.split(",")[2];
										String line6 = result.split(",")[5];
										signal1 = Double.parseDouble(line1);
										signal2 = Double.parseDouble(line2);
										signal3 = Double.parseDouble(line3);
										signal4 = Double.parseDouble(line4);
										signal5 = Double.parseDouble(line5);
										signal6 = Double.parseDouble(line6);
										index++;
										series1X.add(++x, signal1);
										series2X.add(++x, signal2);
										series1Y.add(++y, signal3);
										series2Y.add(++y, signal4);
										series1Z.add(++z, signal5);
										series2Z.add(++z, signal6);
										seriesCollectionX.addSeries(series1X);
										seriesCollectionX.addSeries(series2X);
										seriesCollectionY.addSeries(series1Y);
										seriesCollectionY.addSeries(series2Y);
										seriesCollectionZ.addSeries(series1Z);
										seriesCollectionZ.addSeries(series2Z);
									} catch (Exception e) {
										// TODO: handle exception
									}
								}
								scanner.close();
							}
						};
						thread.start();
						
						Thread subThread = new Thread(){
							@Override
							public void run(){
								while(true){
									System.out.println(index);
									if(index == A_MINUTE) {
										String date = CommonMethod.getCurrentTimeStamp(new Date());
										Acceleration_info acceleration_info = new Acceleration_info(date, signal1, signal2, signal3, signal4, signal5, signal6);
										if(CommonMethod.insertAcceleration(acceleration_info)< 0) {
											System.out.println("failed");
										}
										index = 0;
									}
								}
							}
						};
						subThread.start();
					}
					else{
						jLabel.setText("failed connection");
						jLabel.setVisible(true);
						btnChanel.setEnabled(false);
						
					}
				}
				else{
					jLabel.setText("successful disconnection");
					jLabel.setVisible(true);
					serialPort.closePort();
					btnChanel.setEnabled(false);
					btnConnect.setText("connect");
					portList.setEnabled(true);
					listBaudRate.setEnabled(true);
				}
			}
		});
		
	}
	public List<SerialPort> listPort(){
		SerialPort[] array = SerialPort.getCommPorts();
		List<SerialPort> list = new ArrayList<>();
		for (SerialPort serialPort : array) {
			list.add(serialPort);
		}
		return list;
	}
	
	public boolean isNumeric(String str)
	{
	  return str.matches("-?[0-9]{0,10}");  //match a number with optional '-' and decimal.
	}
}
