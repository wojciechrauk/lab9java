package lab9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

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
import java.awt.Font;


public class ServerGUI extends JFrame {

	private JPanel contentPane;
	private JPanel mainPanel;

	
	private static GroceryImpl grocery;
	private JPanel panel_1;
	private JLabel breadsLbl;
	private JLabel inDeliveryLbl;
	public static void main(String[] args) {
		// Setting up the necessary objects
		System.setProperty("java.security.policy", "client.policy");
		System.setSecurityManager(new RMISecurityManager());
		String url = "rmi://localhost/";
	
		try {
			grocery = new GroceryImpl(50);
			
			Context namingContext = new InitialContext();
			namingContext.rebind("rmi:grocery", grocery);
			printLog("running");
			printLog("Waiting for invocations from clients...");
			
		} catch (Exception e) {
			System.err.println("Cannot establish connection with the server, " + e.getMessage());
		//	e.printStackTrace();
		}
	
		////////////////////////////////////
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI frame = new ServerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public ServerGUI() {
		initComponents();
		createEvents();	
	}
	private void initComponents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		mainPanel = new JPanel();
		
		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2),"SERVER - grocery statistics"));
		contentPane.add(mainPanel);
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		mainPanel.add(panel);
		JLabel breadsDescrLbl = new JLabel("Breads: ");
		breadsDescrLbl.setFont(new Font("Dialog", Font.BOLD, 20));
		panel.add(breadsDescrLbl);
		breadsDescrLbl.setHorizontalAlignment(SwingConstants.LEFT);
		
		breadsLbl = new JLabel("0");
		breadsLbl.setFont(new Font("Dialog", Font.BOLD, 20));
		breadsLbl.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(breadsLbl);
		
		panel_1 = new JPanel();
		mainPanel.add(panel_1);
		
		JLabel lblNewLabel = new JLabel("In delivery: ");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		panel_1.add(lblNewLabel);
		
		inDeliveryLbl = new JLabel("0");
		inDeliveryLbl.setFont(new Font("Dialog", Font.BOLD, 20));
		inDeliveryLbl.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(inDeliveryLbl);
	}
	private void createEvents(){
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					breadsLbl.setText(Integer.toString(grocery.getCurrentBreads()));
					inDeliveryLbl.setText(Integer.toString(grocery.getInDelivery()));
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		};
		Timer SimpleTimer = new Timer(500, listener);
		SimpleTimer.start();
	}

	private static void printLog(String message){
		System.out.println("[SERVER]: " + message);
	}
}
