package pcd.lab03.monitors.barrier;

/*
 * Barrier - to be implemented
 */
public class BarrierImpl implements Barrier {

	private int nParticipants;
	private int nHits;
	
	public BarrierImpl(int nParticipants) {
		this.nParticipants = nParticipants;
		nHits = 0;
	}
	
	@Override
	public synchronized void hitAndWaitAll() throws InterruptedException {	
		nHits++;
		if (nHits == nParticipants) {
			notifyAll();
		} else { 
			while (nHits < nParticipants) {
				wait();
			}
		}
	}
}
