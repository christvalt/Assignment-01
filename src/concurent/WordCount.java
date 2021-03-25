package concurent;

import java.util.*;


public class WordCount extends Thread{


    ArrayList<ExtractTextSimple> extracted;

    private Barrier barrier;
    private Flag stopFlag;



    public WordCount(final int  words, ArrayList<ExtractTextSimple> extracted,Flag stopFlag ){
        this.extracted =extracted;
        this.Words= words;
        this.stopFlag = stopFlag;
        this.barrier = barrier;

    }
    public  Map<String, Integer> computeWord(String word) {
        Map<String, Integer> wordCounter = new HashMap<String, Integer>();
            if (!wordCounter.containsKey(word)) {  // first time we've seen this string
                wordCounter.put(word, 1);//add the word
            }
            else {
                int count = wordCounter.get(word);
                wordCounter.put(word, count + 1);//updating the cont of word
            }
        return wordCounter;
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

                int numWords = extracted.size() ;
                int  wordfound = 0;
                log("counting"+numWords+"found"+wordfound);
                log("ready to update the wordcounted");
                //readyToUpdateWordCounted.await();
                /**
                 code for update the wordcounted in the data structure
                 */
                log("data structure updated");
                //readyToDisplay.await();


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
