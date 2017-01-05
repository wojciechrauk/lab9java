package lab9;

import java.rmi.RMISecurityManager;

import javax.naming.Context;
import javax.naming.InitialContext;


public class RatClient {
	public static void main(String[] args) {
		System.setProperty("java.security.policy", "client.policy");
		System.setSecurityManager(new RMISecurityManager());
		String url = "rmi://localhost/";
	
		try {
			Context namingContext = new InitialContext();
			Grocery grocery = (Grocery) namingContext.lookup(url + "grocery");
			grocery.printStatus();
		
			RatRunnable rat = new RatRunnable(grocery);
			Thread ratThread1 = new Thread(rat, "RAT");
			ratThread1.start();
			
			grocery.printStatus();
			grocery.addBreads(1);
			grocery.printStatus();
			Thread.sleep(1000);
			
			grocery.printStatus();
		} catch (Exception e) {
			System.err.println("Cannot establish connection with the server, " + e.getMessage());
		//	e.printStackTrace();
		}
	}
}
