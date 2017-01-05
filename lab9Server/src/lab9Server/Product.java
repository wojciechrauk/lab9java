package lab9Server;

import java.rmi.*;

public interface Product extends Remote {
	String getDescription()
		throws RemoteException;
	
	void performAction()
		throws RemoteException;
}
