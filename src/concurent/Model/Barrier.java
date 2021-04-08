package concurent.Model;

public interface Barrier {

	void hitAndWaitAll() throws InterruptedException;

}
