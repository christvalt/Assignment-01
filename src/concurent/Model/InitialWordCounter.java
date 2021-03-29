package concurent.Model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InitialWordCounter {
    Map<String, Integer> initialMap = new HashMap<String, Integer>();
    private int nWords;
//enlever le final apres
    public void computeWord( final String word) {
        ///Map<String, Integer> wordCounter = new HashMap<String, Integer>();
        if (!initialMap.containsKey(word)) {  // first time we've seen this string
            initialMap.put(word, 1);//add the word

        }
        else {
            int count = initialMap.get(word);
            initialMap.put(word, count + 1);//updating the cont of word
        }
    }



   public Map<String, Integer> getInitialMap() {
        return initialMap;
    }


            //verifier apres........................................
    public Map<String, Integer> updatedCount( int nWordCount, InitialWordCounter wordCuntener) {

        Map<String,Integer>  finalMap = new HashMap<String,Integer>();
            nWords  += nWordCount;

      finalMap =
                Stream.concat(finalMap.entrySet().stream(), wordCuntener.getInitialMap().entrySet().stream())
                        .collect(Collectors.groupingBy(Map.Entry::getKey,
                                Collectors.summingInt(Map.Entry::getValue))).entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (x,y)-> {throw new AssertionError();}, LinkedHashMap::new));

        return this.initialMap;
    }

    public void update(InitialWordCounter wordCuntener){
        update(wordCuntener.getInitialMap());
    }

    public void update(final Map<String, Integer> finalMap) {
        finalMap.forEach((w, count) -> System.out.println(w +": " + count +" times"));
    }


}
