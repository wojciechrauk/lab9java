package lab9Server;

import java.rmi.*;
import java.rmi.server.*;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ProductServer {
	public static void main(String args[]) {
		try {
			System.out.println("Constructing server	implementations...");
			
			ProductImpl p1 = new ProductImpl("Blackwell Toaster");
			ProductImpl p2 = new ProductImpl("ZapXpress Microwave Oven");
			
			System.out.println("Binding server implementations to registry...");
			Context namingContext = new InitialContext();
			namingContext.rebind("rmi:toaster", p1);
			namingContext.rebind("rmi:microwave", p2);
			System.out.println("Waiting for invocations from clients...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
