package concurent.Model;

import java.util.*;


public class CounterAgent extends Thread{
    private final ExtractAgent extractAgent;
    private TaskCompletionLatch synch;
    private InitialWordCounter wordCount;


    public CounterAgent(final ExtractAgent extractAgent , InitialWordCounter wordCount /*Barrier barrier ,concurent.controller.Flag stopFlag*/, TaskCompletionLatch synch){
        this.extractAgent =extractAgent;
        this.wordCount =new InitialWordCounter();
        this.synch=synch;
    }

    public  void run() {
        boolean stopped = false;

            System.out.println("test1");
           // ArrayList<ExtractAgent>  = new ArrayList<ExtractAgent>();
            Optional<List<String>> allWords = extractAgent.getWords();
            log("ready to initiate");
            while (allWords.isPresent()){
                //while (!synch.stopped() && allWords.isPresent()){
                log("ready to count");
                int numWords = 0;
                List<String> ListOfWord = allWords.get();
                for (String w : ListOfWord) {
                    wordCount.computeWord(w);
                    // System.out.println(ListOfWord +"errrr");
                }
                numWords += ListOfWord.size();
                /*synchronized (ListOfWord){

                     numWords += ListOfWord.size() ;
                }*/
               // System.out.println(ListOfWord +"errrr");
                log(" after counting we have new words : "+numWords);
                log("waiting to update the wordcounted");
                //barrier.hitAndWaitAll();
                wordCount.update(wordCount);
                /*synchronized (wordCount){

                   // allWords = extractText.getWords();
                    stopped = true;
                }*/
                log("data structure updated");
                //readyToDisplay.await();
                //barrier.hitAndWaitAll();
            }




    }
    private void log(String msg) {
        System.out.println("[WORD COUNTER " + Thread.currentThread().getName() +"] " + msg);
       /*
       *  synchronized(System.out) {
            System.out.println("[ "+getName()+" ] "+msg);
        }*/
    }
    private void waitFor(long ms) throws InterruptedException{
        Thread.sleep(ms);
    }


}
