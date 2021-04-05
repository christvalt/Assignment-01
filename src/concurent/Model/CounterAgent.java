package concurent.Model;

import java.util.*;


public class CounterAgent extends Thread{
    private final ExtractAgent extractAgent;
    private TaskCompletionLatch synch;
    private InitialWordCounter initialWordCounter;
    private  int numWords = 0;
    private Master master;


    public CounterAgent(final ExtractAgent extractAgent  /*Barrier barrier ,concurent.controller.Flag stopFlag*/, TaskCompletionLatch synch){
        this.extractAgent =extractAgent;
        this.initialWordCounter =new InitialWordCounter();
        this.synch=synch;
       // this.master= master;
    }


    public  void run() {

         boolean stopped = false;
        log("ready to count words ");
       // ArrayList<CollisionToCheck> collisionsToBeSolved = new ArrayList<CollisionToCheck>();

        Map<String, Integer> wordToBeupdate = new HashMap<String, Integer>();
        //dfWordsOpt = wordsExtractor.getWords()).isPresent()
        Optional<List<String>> allWords ;
        //boolean allWordss =(allWords).isPresent();
        Optional<List<String>> allWordsGet;
        while ((allWords=extractAgent.getWords()).isPresent()) {

            // log("waiting to count word");
            List<String> ListOfWord = allWords.get();
            //log("---new words: " + ListOfWord.size());

                for (String w : ListOfWord) {
                    initialWordCounter.computeWord(w);
                       stopped =true;
                    //wordToBeupdate.putAll();
                }
                numWords += ListOfWord.size();

            log("-counting   : " + ListOfWord.size() + " new words" +"From "+numWords) ;
            log("waiting to solve update");




            synchronized (this){
                //initialWordCounter.All(initialWordCounter);
               // allWords = extractAgent.getWords();
                initialWordCounter.update(ListOfWord.size(), initialWordCounter);
                initialWordCounter.finalUpdater();

            }


            /* if (allWords.isPresent()){
                initialWordCounter.All(initialWordCounter);
                allWords = extractAgent.getWords();
            }*/

            //log("work finish");
            synch.notifyCompletion();
            log("completed");
            //master.threadCompleted(numWords, initialWordCounter);
        }


       /* log("ready");
        Optional<List<String>> allWords = extractAgent.getWords();
        while (allWords.isPresent()) {
           // log("waiting to count word");
            List<String> ListOfWord = allWords.get();
            log("new words: " + ListOfWord.size());
            for (String w : ListOfWord) {
                initialWordCounter.computeWord(w);
            }
            numWords += ListOfWord.size();
            //log(" after counting i have  : " + numWords + "  new words");
            initialWordCounter.All(initialWordCounter);
            allWords = extractAgent.getWords();
        }

        log("completed");
       // master.threadCompleted(numWords,initialWordCounter);
    */


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