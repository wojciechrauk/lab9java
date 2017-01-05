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

public class BakeryClientGUI extends JFrame {

	private JPanel contentPane;
	private JPanel mainPanel;
	private JLabel orderLbl;

	private static Bakery bakery;
	private JComboBox selectedValue;
	private JPanel panel;
	private JPanel panel_1;
	private JButton btnBuy;
	private JPanel panel_2;
	private JLabel lblNewLabel;
	private JLabel breadsAvaliableLbl;

	public static void main(String[] args) {
		// Setting up the necessary objects
		System.setProperty("java.security.policy", "client.policy");
		System.setSecurityManager(new RMISecurityManager());
		String url = "rmi://localhost/";

		try {
			Context namingContext = new InitialContext();
			Grocery grocery = (Grocery) namingContext.lookup(url + "grocery");
			grocery.printStatus();

			bakery = new Bakery(20, grocery);
			Thread clientThread = new Thread(bakery, "Bakery");
			clientThread.start();

		} catch (Exception e) {
			System.err.println("Cannot establish connection with the server, " + e.getMessage());
			// e.printStackTrace();
		}

		////////////////////////////////////

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BakeryClientGUI frame = new BakeryClientGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BakeryClientGUI() {
		initComponents();
		createEvents();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		mainPanel = new JPanel();

		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Bakery"));
		contentPane.add(mainPanel);
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		panel_2 = new JPanel();
		mainPanel.add(panel_2);

		lblNewLabel = new JLabel(" Breads avaliable: ");
		panel_2.add(lblNewLabel);

		breadsAvaliableLbl = new JLabel("0");
		panel_2.add(breadsAvaliableLbl);

		panel = new JPanel();
		mainPanel.add(panel);
		orderLbl = new JLabel("Order from bakery");
		panel.add(orderLbl);
		orderLbl.setHorizontalAlignment(SwingConstants.CENTER);

		panel_1 = new JPanel();
		mainPanel.add(panel_1);

		String[] orderLoafsBakery = new String[100];
		for (int i = 0; i < orderLoafsBakery.length; i++) {
			orderLoafsBakery[i] = Integer.toString(i + 1);
		}

		selectedValue = new JComboBox(orderLoafsBakery);
		panel_1.add(selectedValue);

		btnBuy = new JButton("Buy");
		panel_1.add(btnBuy);
	}

	private void createEvents() {

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				breadsAvaliableLbl.setText(Integer.toString(bakery.getAvaliable()));
			}
		};
		Timer SimpleTimer = new Timer(500, listener);
		SimpleTimer.start();
		btnBuy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int numberOfBreads = Integer.parseInt((String) selectedValue.getSelectedItem());
				bakery.getBreads(numberOfBreads);
			}
		});
	}

}
