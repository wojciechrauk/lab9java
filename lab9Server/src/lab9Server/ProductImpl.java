package lab9Server;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class ProductImpl extends UnicastRemoteObject implements Product {
	
	private static final long servialVersionUID = 1L;
	private String name;
	
	protected ProductImpl(String n) throws RemoteException {
		super();
		name = n;
	}

	@Override
	public String getDescription() throws RemoteException {
		return "I am a " + name;
	}

	@Override
	public void performAction() throws RemoteException {
		System.out.println("Some action performed! :D");		
	}

}
