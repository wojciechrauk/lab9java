package lab9;

import java.rmi.RemoteException;
import java.util.Stack;

public class Client implements Runnable{
	private Stack<Integer> breadsToBuy = new Stack<Integer>();
	private Grocery grocery;
	
	public Client(Grocery grocery) {
		this.grocery = grocery;
	}
	public void buyBread(int amount){
		this.breadsToBuy.push(amount);
	}
	
	@Override
	public void run() {
		while(true){
			if(!breadsToBuy.empty()){
				int breads = this.breadsToBuy.pop();
				try {
					this.grocery.subBreads(breads);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				String threadName = Thread.currentThread().getName();
				System.out.println("[" +threadName +"]: " + ", bought " + Integer.toString(breads));
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}		
}
