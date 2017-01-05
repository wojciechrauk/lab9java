package lab9;

import javax.naming.Context;
import javax.naming.InitialContext;

public class Server {
	
	public static void main(String args[]) {
		try {
			printLog("Constructing server	implementations...");
					
			GroceryImpl grocery = new GroceryImpl(50);
				
			printLog("Binding server implementations to registry...");
			
			Context namingContext = new InitialContext();
			namingContext.rebind("rmi:grocery", grocery);
			printLog("Waiting for invocations from clients...");
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void printLog(String message){
		System.out.println("[SERVER]: " + message);
	}
}
