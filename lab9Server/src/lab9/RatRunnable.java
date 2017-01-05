package lab9;

import java.rmi.RemoteException;
import java.util.Date;

public class RatRunnable implements Runnable{
	private final int RAT_STARVATION_MINUTES = 2; // time after which rat will die of starvation 
	private final int RAT_EATING_MINUTES = 10;
	
	private RatStatus status = RatStatus.alive;
	private Grocery grocery;
	private Date lastEaten;
	
	
	public RatRunnable(Grocery grocery) {
		this.grocery = grocery;
	}	
	
	@Override
	public void run() {
		this.printStatus();
		while(status != RatStatus.dead){
			try {
				if(grocery.subBreads(1)){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					this.lastEaten = new Date();
				}
				else{
					this.printStatus();
					status = RatStatus.sleeping;
					Date temp = new Date();
					if(temp.getTime() - lastEaten.getTime() >= 1000 * 60 * RAT_STARVATION_MINUTES){ // difference in millis
						status = RatStatus.dead;
						this.printStatus();
						break;
					}
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.printStatus();
		}
	}
	public RatStatus getStatus(){
		return this.status;
	}
	public void printStatus(){
		String threadName = Thread.currentThread().getName();
		try {
			System.out.println("[" +threadName +"]: " + this.status.toString() + ", [Breads in grocery]: " + this.grocery.getCurrentBreads());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}