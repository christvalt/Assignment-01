package concurent.Model;

import java.util.List;
import java.util.Optional;

public class ExtractAgent {

    private List<ExtractText> extractText;
    private int startIndex =0 ;

    public ExtractAgent(List<ExtractText> extractText) {
        this.extractText = extractText;

    }


    public  synchronized Optional<List<String>> getWords(){
        while (startIndex < extractText.size()) {
            System.out.println("funzina");
            Optional<List<String>>  words = extractText.get(startIndex).extractor();
            if ( words.isPresent()) {
                return words;
            } else {
                startIndex ++;
                return this.getWords();
            }

        }
        return Optional.empty();

    }

}
