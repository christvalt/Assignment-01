package concurent;

import java.util.*;


public class CounterAgent extends Thread{
    private final ExtractText extractText;
   // private Barrier barrier;
    private  TaskCompletionLatch synch;
   // private Flag stopFlag;
    private WordCount wordCount;


    public CounterAgent(final ExtractText extractText ,WordCount wordCount /*Barrier barrier ,Flag stopFlag*/,TaskCompletionLatch synch){
        this.extractText =extractText;
        this.wordCount =wordCount;
        this.synch=synch;
       // this.barrier = barrier;
        //this.stopFlag = stopFlag;
    }

    public  void run() {
        boolean stopped = false;
        try{
           // ArrayList<ExtractAgent>  = new ArrayList<ExtractAgent>();
            Optional<List<String>> allWords = extractText.getWords();

            while (!synch.stopped() && allWords.isPresent()){
                log("waiting to count  word ");
                //barrier.hitAndWaitAll();
                int numWords = 0;
                ArrayList<String> ListOfWord = (ArrayList<String>) allWords.get();
                synchronized (allWords){
                    for (String w : ListOfWord) {
                        wordCount.computeWord(String.valueOf(w));
                    }
                     numWords += ListOfWord.size() ;
                }
                log(" after counting we have "+ListOfWord.size()+"found"+numWords);
                log("waiting to update the wordcounted");
                //barrier.hitAndWaitAll();
                synchronized (wordCount){
                    wordCount.updatedCount(wordCount);
                    allWords = extractText.getWords();
                    stopped = true;
                }
                log("data structure updated");
                //readyToDisplay.await();
                //barrier.hitAndWaitAll();
            }
        }catch(Exception ex){

        }
    }
    private void log(String msg) {
        // System.out.println("[COLLISION CHECKER " + Thread.currentThread().getName() +"] " + msg);
        synchronized(System.out) {
            System.out.println("[ "+getName()+" ] "+msg);
        }
    }
    private void waitFor(long ms) throws InterruptedException{
        Thread.sleep(ms);
    }


}
