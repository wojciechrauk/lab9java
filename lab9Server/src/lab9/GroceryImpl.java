package lab9;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class GroceryImpl extends UnicastRemoteObject implements Grocery{
	private static final long serialVersionUID = -8355324085833130662L;
	
	private final int MAX_BREAD_CAPACITY = 100;
	private int breads;
	private int inDelivery = 0;

	public GroceryImpl(int initialBreads) throws RemoteException{
		super();
		this.breads = initialBreads;
	}
	

	public int getCurrentBreads() throws RemoteException{
		return this.breads;
	}
	
	public synchronized boolean addBreads(int amount) throws RemoteException{
		this.inDelivery -= amount;
		if(this.breads + amount > MAX_BREAD_CAPACITY) {
			this.breads = MAX_BREAD_CAPACITY;
			return false;
		}
		this.breads += amount;
		return true;
	}
	
	public synchronized boolean subBreads(int amount) throws RemoteException{
		if(this.breads - amount < 0){
			this.breads = 0;
			return false;
		}
		this.breads -= amount;
		return true;
	}
	public synchronized int getInDelivery() throws RemoteException{
		return this.inDelivery;
	}
	public synchronized void setInDelivery(int amount) throws RemoteException{
		this.inDelivery = amount;
	}

	@Override
	public void printStatus() throws RemoteException {
		System.out.println("[GROCERY]: number of breads: " + this.breads);		
	}
}
