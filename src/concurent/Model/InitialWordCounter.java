package concurent.Model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.*;


public  class InitialWordCounter {

    private static Map<String, Integer> sortedWordCount;
    private static  int numberOfOutputWords= 4;
    private int stateWords;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock writeLock = lock.writeLock();

    Map<String, Integer> initialMap = new HashMap<String, Integer>();
    Map<String, Integer> getSortedWordCount = new HashMap<String, Integer>();
    private int nWords;
    private boolean completed;
    private boolean stopped ;

    public synchronized   void  computeWord( final String word) {
        if (!initialMap.containsKey(word)) {  // first time we've seen this string
            try {
                writeLock.lock();
                initialMap.put(word, 1);//add the word
            } finally {
                writeLock.unlock();
            }
        }
        else {
            try {
                writeLock.lock();
                int count = initialMap.get(word);
                initialMap.put(word, count + 1);//updating the cont of word
            } finally {
                writeLock.unlock();
            }
        }
    }


   public Map<String, Integer> getInitialMap() {
        return initialMap;
    }

    public synchronized void update (final int nWords, final InitialWordCounter map){
        this.stateWords += nWords;
        this.sortedWordCount = new LinkedHashMap<>();

        this.sortedWordCount =
                Stream.concat(sortedWordCount.entrySet().stream(), map.getInitialMap().entrySet().stream())
                        .collect(Collectors.groupingBy(Map.Entry::getKey,
                                Collectors.summingInt(Map.Entry::getValue))).entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (x,y)-> {throw new AssertionError();}, LinkedHashMap::new));
    }


    public static synchronized Map<String, Integer> getSortedWordCount(){
        return sortedWordCount.entrySet().stream()
                .limit(numberOfOutputWords)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    public  synchronized int getStateWords(){
        return this.stateWords;
    }
    public  synchronized int getGetStateWords(){
        return getStateWords();
    }

    public  synchronized void  updater() {
        this.getSortedWordCount();
        for (Map.Entry<String, Integer> entry : this.getSortedWordCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " time");
        }
    }

    public  void  finalUpdater() {
        try {
            writeLock.lock();
            this.getSortedWordCount().forEach((word, count) -> System.out.println(word +": " + count +" times"));
            System.out.println("total  word:" +getStateWords());
        } finally {
            writeLock.unlock();
        }
    }

    public boolean isCompleted(){
        return this.completed;
    }

    public boolean isStopped(){
        return this.stopped;
    }




}
