package concurent.Model;

import java.util.List;
import java.util.Optional;

public class ExtractAgent {

    private  final List<ExtractText> extractText;
    private int startIndex =0 ;
   // private final int pagesEachSection;

    public ExtractAgent(List<ExtractText> extractText) {
        this.extractText = extractText;
       /// this.pagesEachSection = pagesEachSection;

    }
    public int size(){
        return this.extractText.size();
    }


    public  synchronized Optional<List<String>> getWords(){
        //System.out.println("te"  +extractText.size());
        while (startIndex < extractText.size()) {
            //System.out.println("test2");
            Optional<List<String>>  words = extractText.get(startIndex).extractor();
            if ( words.isPresent()) {
                //System.out.println("eeeo");
                return words;
            } else {
                startIndex +=1;
                return this.getWords();
            }

        }
        return Optional.empty();

    }

}
