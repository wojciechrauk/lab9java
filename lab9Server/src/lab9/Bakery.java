package lab9;

import java.rmi.RemoteException;

public class Bakery implements Runnable{
	private final int DELIVERY_TIME_SEC = 3;
	private final int MAX_BREAD_CAPACITY = 100;
	private int numOfBreads;
	private Grocery grocery;
	
	public Bakery(int numOfBreads, Grocery grocery ) {
		if(numOfBreads < MAX_BREAD_CAPACITY) this.numOfBreads = numOfBreads;
		else this.numOfBreads = MAX_BREAD_CAPACITY;
		
		this.grocery = grocery;
	}
	
	@Override
	public void run() {
		while(true){
			if(this.numOfBreads < MAX_BREAD_CAPACITY){
				this.numOfBreads++;
			}
	
			this.printStatus();
			
			try {
				Thread.sleep(5000);	// produce one bread every 5 seconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}	
	public synchronized int getAvaliable(){
		return this.numOfBreads;
	}
	public synchronized void getBreads( int breads){
		if(this.numOfBreads < breads){
			breads = this.numOfBreads;
		}
		this.numOfBreads -= breads;
		int toDeliver = breads;
		try {
			grocery.setInDelivery(toDeliver);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		new Thread(new Runnable(){
	        @Override
	        public void run() {
	            try {
					Thread.sleep(1000 * DELIVERY_TIME_SEC);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	            try {
					grocery.addBreads(toDeliver);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
	        }
	    }).start();
	}
	
	public void printStatus(){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" +threadName +"]: " + ", [Breads in bakery]: " + this.numOfBreads);
	}
}
