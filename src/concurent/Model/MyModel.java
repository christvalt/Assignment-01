package concurent.Model;

import concurent.Model.InitialWordCounter;
import concurent.Model.ModelObserver;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyModel {

	private List<ModelObserver> observers;
	private int state;

	private int stateWords;
	private InitialWordCounter initialWordCounter;
	private Map<String, Integer> sortedWordCount;
	private int noutput;


	public MyModel(){
		state = 0;
		observers = new ArrayList<ModelObserver>();
	}

	public synchronized void update (final int nWords, final InitialWordCounter map){
		this.stateWords += nWords;
		this.sortedWordCount =
				Stream.concat(sortedWordCount.entrySet().stream(), map.getInitialMap().entrySet().stream())
						.collect(Collectors.groupingBy(Map.Entry::getKey,
								Collectors.summingInt(Map.Entry::getValue))).entrySet().stream()
						.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
								(x,y)-> {throw new AssertionError();}, LinkedHashMap::new));
		notifyObservers();
	}

	public synchronized int getTotWords(){
		return this.stateWords;
	}

	public synchronized Map<String, Integer> getSortedWordCount(){
		return this.sortedWordCount.entrySet().stream()
				.limit(noutput)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(e1, e2) -> e1, LinkedHashMap::new));
	}
	private void notifyObservers(){
		for (ModelObserver obs: observers){
			obs.modelUpdated(this);
		}
	}



	public void addObserver(ModelObserver obs){
		observers.add(obs);
	}







}
