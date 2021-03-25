
import java.util.*;
import


public class WordCount extends Thread{


    ArrayList<ExtractTextSimple> extratTexrSimple;
    private int Words = 0;
    private Barrier barrier;



    public WordCount(final int  words, ArrayList<ExtractTextSimple> extractTextSimple ){
        this.extratTexrSimple=extractTextSimple;
        this.Words= words;

    }
    public  Map<String, Integer> counting(List<String> word) {
        Map<String, Integer> CountingWord = new HashMap<String, Integer>();
        for (String s:word) {

            if (!CountingWord.containsKey(s)) {  // first time we've seen this string
                CountingWord.put(s, 1);
            }
            else {
                int count = CountingWord.get(s);
                CountingWord.put(s, count + 1);
            }
        }
        return CountingWord;
    }


    public void run() {
        try{
            log("before counting");
            ArrayList<ExtractTextSimple> wordExtracted = new ArrayList<ExtractTextSimple>();
            while (!stopFlag.isSet()){

                log("waiting to check");

                List<String> pdfWords = pdfWordsOpt.get();
                log("new words: " + pdfWords.size());
                for (String w : pdfWords) {
                    map.computeWord(w);
                }
                totWords += pdfWords.size();
                controller.update(map);
                pdfWordsOpt = wordsExtractor.getWords();
            }
            log("after counting");
            controller.threadCompleted(totWords, map);


        }catch(Exception ex){

        }
    }
    private void log(String msg) {
        // System.out.println("[COLLISION CHECKER " + Thread.currentThread().getName() +"] " + msg);
    }


}
