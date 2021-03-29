package concurent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordCount {
    Map<String, Integer> wordCuntener = new HashMap<String, Integer>();

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
    public Map<String, Integer> updatedCount(WordCount wordCuntener) {

        return this.wordCuntener;
    }
}
