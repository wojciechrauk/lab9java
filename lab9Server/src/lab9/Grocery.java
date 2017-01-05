package lab9;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Grocery extends Remote  {

	public int getCurrentBreads() throws RemoteException;
	
	public boolean addBreads(int amount) throws RemoteException;
	
	public boolean subBreads(int amount) throws RemoteException;
	
	public int getInDelivery() throws RemoteException;
	
	public void printStatus() throws RemoteException;
}
