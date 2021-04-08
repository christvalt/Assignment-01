package concurent.Model;

import concurent.controller.Flag;

import java.io.IOException;
import java.util.*;


public class CounterAgent extends Thread{
    private final ExtractAgent extractAgent;
    private TaskCompletionLatch synch;
    private InitialWordCounter initialWordCounter;
    private  int numWords = 0;
    private Flag stopFlag;
    private  boolean running = true;
    private Master master;
    private Barrier barrier;



    public CounterAgent(final ExtractAgent extractAgent  , Barrier barrier  ,Flag stopFlag, TaskCompletionLatch synch){
        this.extractAgent =extractAgent;
        this.initialWordCounter =new InitialWordCounter();
        this.synch=synch;
        this.stopFlag = stopFlag;
        this.barrier = barrier;
    }


    public  void run() {

        Optional<List<String>> allWords= null;
        try {
            allWords = extractAgent.getWords();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (!(!stopFlag.isSet()&&(allWords=extractAgent.getWords()).isPresent())) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Random gen = new Random(System.nanoTime());
            List<String> ListOfWord = allWords.get();
            try {
                waitFor(gen.nextInt(1));
                log("ready to count words ");
                barrier.hitAndWaitAll();
                for (String w : ListOfWord) {
                    initialWordCounter.computeWord(w);
                }
                numWords += ListOfWord.size();

                log("-counting   : " + ListOfWord.size() + " new words" +"From "+numWords) ;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("waiting to update counting");
            try {
                barrier.hitAndWaitAll();
                synchronized (this){
                    initialWordCounter.update(ListOfWord.size(), initialWordCounter);
                    initialWordCounter.finalUpdater();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }log("updated count  complete");

        }
        synch.notifyCompletion();
        log("completed");

    }
    private void log(String msg) {
        System.out.println("[WORD COUNTER " + Thread.currentThread().getName() +"] " + msg);
    }
    private void waitFor(long ms) throws InterruptedException{
        Thread.sleep(ms);
    }


}