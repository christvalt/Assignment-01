package concurent.Model;

import java.util.HashMap;
import java.util.Map;

public class WordCount {
    Map<String, Integer> wordCuntener = new HashMap<String, Integer>();
    private int nWords;

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
        return wordCuntener;
    }


            //verifier apres........................................
    public Map<String, Integer> updatedCount( int nWordCount, WordCount wordCuntener) {

        Map<String,Integer>  finalMap = new HashMap<String,Integer>();
            nWords  += nWordCount;

        return this.wordCuntener;
    }
}
