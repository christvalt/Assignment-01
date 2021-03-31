package concurent.Model;

import java.util.*;


public class CounterAgent extends Thread{
    private final ExtractAgent extractAgent;
    private TaskCompletionLatch synch;
    private InitialWordCounter initialWordCounter;
    private  int numWords = 0;


    public CounterAgent(final ExtractAgent extractAgent  /*Barrier barrier ,concurent.controller.Flag stopFlag*/, TaskCompletionLatch synch){
        this.extractAgent =extractAgent;
        this.initialWordCounter =new InitialWordCounter();
        this.synch=synch;
    }

    public  void run() {

        boolean stopped = false;
        log("ready");
        Optional<List<String>> allWords = extractAgent.getWords();
        while (!synch.stopped() && allWords.isPresent()) {
           // log("waiting to count word");
            List<String> ListOfWord = allWords.get();
            log("new words: " + ListOfWord.size());
            for (String w : ListOfWord) {
                initialWordCounter.computeWord(w);

                stopped =true;
            }
            numWords += ListOfWord.size();

            log(" after counting i have  : " + numWords + "  new words");
           // log("waiting to update the wordcounted");
                initialWordCounter.All(initialWordCounter);
                allWords = extractAgent.getWords();
            /* if (allWords.isPresent()){
                initialWordCounter.All(initialWordCounter);
                allWords = extractAgent.getWords();
            }*/

            //log("work finish");
            synch.notifyCompletion();
            log("completed");

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