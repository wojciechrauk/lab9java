package lab9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


public class RatClientGUI extends JFrame {

	private JPanel contentPane;
	private JPanel ratPanel;
	private JLabel ratStatusLbl;

	
	private static RatRunnable rat;
	public static void main(String[] args) {
		// Setting up the necessary objects
		System.setProperty("java.security.policy", "client.policy");
		System.setSecurityManager(new RMISecurityManager());
		String url = "rmi://localhost/";
	
		try {
			Context namingContext = new InitialContext();
			Grocery grocery = (Grocery) namingContext.lookup(url + "grocery");
			grocery.printStatus();
		
			rat = new RatRunnable(grocery);
			Thread ratThread1 = new Thread(rat, "RAT");
			ratThread1.start();
			
		} catch (Exception e) {
			System.err.println("Cannot establish connection with the server, " + e.getMessage());
		//	e.printStackTrace();
		}
	
		////////////////////////////////////
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RatClientGUI frame = new RatClientGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public RatClientGUI() {
		initComponents();
		createEvents();	
	}
	private void initComponents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		ratPanel = new JPanel();
		contentPane.add(ratPanel, BorderLayout.CENTER);
		
		ratPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2),"Rat"));
		ratStatusLbl = new JLabel("ALIVE");
		ratStatusLbl.setHorizontalAlignment(SwingConstants.CENTER);
		ratPanel.add(ratStatusLbl);
		
//		JPanel rat1Panel = new JPanel();
//		rat1Panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2),"Rat1"));
//		ratsPanel.add(rat1Panel);
//		rat1Panel.setLayout(new BorderLayout(0, 0));
//		
//		rat1StatusLbl = new JLabel("ALIVE");
//		rat1StatusLbl.setHorizontalAlignment(SwingConstants.CENTER);
//		rat1Panel.add(rat1StatusLbl);
	}
	private void createEvents(){
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateRat(rat, ratStatusLbl, ratPanel);
			}
		};
		Timer SimpleTimer = new Timer(500, listener);
		SimpleTimer.start();
	}
	private void updateRat(RatRunnable rat,JLabel lbl, JPanel panel){
		switch (rat.getStatus()){
			case alive:
				lbl.setText("ALIVE");
				panel.setBackground(Color.green);
				break;
			case sleeping:
				lbl.setText("SLEEPING");
				panel.setBackground(Color.lightGray);
				break;
			case dead:
				lbl.setText("DEAD");
				panel.setBackground(Color.darkGray);
				break;
		}
	}
}
