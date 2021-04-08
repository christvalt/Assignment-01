package concurent.Model;


import java.util.ArrayList;

public class Statistics {

	private static Statistics instance;

	private long computeduration;
	private long countwordDuration;
	private long displayDuration;
	private long startTime;
	private long endTime;
	
	private long t0, t1, t2, t3;
	private long iter;



	public static Statistics getInstance() {
		synchronized (Statistics.class) {
			if (instance == null) {
				instance = new Statistics();
			}
		}
		return instance;
	}
	
	private Statistics() {}
	
	public void notifyStartedNewSimulation(ArrayList<CounterAgent> counterAgent, int nWorkers) {
		System.out.println("-----Started------");
		computeduration = 0;
		countwordDuration = 0;
		displayDuration = 0;

		startTime = System.currentTimeMillis();
	}

	public void notifyEndSimulation() {
		endTime = System.currentTimeMillis();
	}
	

	
	public void notifycomputeWordPosCompleted() {
		t1 = System.currentTimeMillis();
		computeduration += (t1 - t0);
	}
	
	public void notifyuPDATEwordOccurenceCompleted() {
		t2 = System.currentTimeMillis();
		countwordDuration += (t2 - t1);
	}

	public void notifyDisplayCompleted() {
		t3 = System.currentTimeMillis();
		displayDuration += (t3 - t2);
	}

	public void dump() {
		System.out.println("Time elapsed: " + (endTime - startTime) + " ms - "  + "  completed.");

		
	}
}
