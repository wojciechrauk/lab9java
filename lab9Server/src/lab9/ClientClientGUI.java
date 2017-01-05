package lab9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RMISecurityManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import javax.swing.JButton;


public class ClientClientGUI extends JFrame {

	private JPanel contentPane;
	private JPanel mainPanel;
	private JLabel orderLbl;

	
	private static Client client;
	private JComboBox selectedValue;
	private JPanel panel;
	private JPanel panel_1;
	private JButton btnBuy;
	public static void main(String[] args) {
		// Setting up the necessary objects
		System.setProperty("java.security.policy", "client.policy");
		System.setSecurityManager(new RMISecurityManager());
		String url = "rmi://localhost/";
	
		try {
			Context namingContext = new InitialContext();
			Grocery grocery = (Grocery) namingContext.lookup(url + "grocery");
			grocery.printStatus();
		
			client = new Client(grocery);
			Thread clientThread = new Thread(client, "CLIENT");
			clientThread.start();
			
			grocery.printStatus();
			grocery.addBreads(1);
			grocery.printStatus();
			Thread.sleep(1000);
			
			grocery.printStatus();
		} catch (Exception e) {
			System.err.println("Cannot establish connection with the server, " + e.getMessage());
		//	e.printStackTrace();
		}
	
		////////////////////////////////////
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientClientGUI frame = new ClientClientGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public ClientClientGUI() {
		initComponents();
		createEvents();	
	}
	private void initComponents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		mainPanel = new JPanel();
		
		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2),"Client"));
		contentPane.add(mainPanel);
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panel = new JPanel();
		mainPanel.add(panel);
		orderLbl = new JLabel("Make you order");
		panel.add(orderLbl);
		orderLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_1 = new JPanel();
		mainPanel.add(panel_1);
		
		String[] avaliableSelections = {"1", "2", "3"};
		selectedValue = new JComboBox(avaliableSelections);
		panel_1.add(selectedValue);
		
		btnBuy = new JButton("Buy");
		panel_1.add(btnBuy);
	}
	private void createEvents(){
		btnBuy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int numberOfBreads = Integer.parseInt((String) selectedValue.getSelectedItem());
				client.buyBread(numberOfBreads);
			}
		});
	}

}
