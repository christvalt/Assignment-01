package concurent;

import java.util.*;


public class WordCount extends Thread{


    ArrayList<ExtractTextSimple> extracted;

    private Barrier barrier;
    private Flag stopFlag;
    Map<String, Integer> wordCuntener = new HashMap<String, Integer>();



    public WordCount(/*final int  words,*/ ArrayList<ExtractTextSimple> extracted, Flag stopFlag ){
        this.extracted =extracted;
       // this.Words= words;
        this.stopFlag = stopFlag;
        this.barrier = barrier;

    }
    public void computeWord(String word) {
        ///Map<String, Integer> wordCounter = new HashMap<String, Integer>();
            if (!wordCuntener.containsKey(word)) {  // first time we've seen this string
                wordCuntener.put(word, 1);//add the word
            }
            else {
                int count = wordCuntener.get(word);
                wordCuntener.put(word, count + 1);//updating the cont of word
            }
    }
    public Map<String, Integer> getWordCuntener() {
        Map<String, Integer> e = new HashMap<String, Integer>();
        return wordCuntener;
    }

    public void run() {
        try{
            ArrayList<ExtractTextSimple> extracted = new ArrayList<ExtractTextSimple>();

            while (!stopFlag.isSet()){
                log("waiting to count  word ");
                //readyToCountNewWord.await();
                barrier.hitAndWaitAll();
                // code for count new word
                int words = 0;
                for (ExtractTextSimple w : extracted) {
                    computeWord(String.valueOf(extracted));
                    words++;
                }
                //int numWords + = extracted.size() ;
                log(" after counting we have "+extracted.size()+"found"+words);
                log("waiting to update the wordcounted");

                //readyToUpdateWordCounted.await();
                barrier.hitAndWaitAll();
                //code for update the wordcounted in the data structure
                updateCount(words);


                log("data structure updated");
                //readyToDisplay.await();


            }


        }catch(Exception ex){

        }
    }

    private void updateCount(int words) {

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
