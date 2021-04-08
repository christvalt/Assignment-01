package concurent.Model;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ExtractAgent {

    private  final List<ExtractText> extractText;
    private int startIndex =0 ;
    private final int numPages;


    public ExtractAgent(List<ExtractText> extractText, int numPages) {
        this.extractText = extractText;

        this.numPages = numPages;
    }

    public  Optional<List<String>> getWords() throws IOException {

        while (startIndex < extractText.size()) {

            Optional<List<String>>  words = extractText.get(startIndex).extractor(numPages);
            if ( words.isPresent()) {
                return words;
            } else {
                startIndex +=1;
                return this.getWords();
            }

        }
        return Optional.empty();

    }

}
