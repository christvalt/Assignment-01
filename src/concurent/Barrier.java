package concurent;

public interface Barrier {

	void hitAndWaitAll() throws InterruptedException;

}
