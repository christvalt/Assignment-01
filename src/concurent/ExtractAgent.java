package concurent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExtractAgent extends Thread{

    private List<ExtractText> extractText;
    private Barrier barrier;
    private int startIndex;

    public ExtractAgent( List <ExtractText> extractText,Barrier barrier) {
        this.extractText = extractText;
        this.barrier = barrier;
    }


    public  synchronized Optional<List<String>> getWords(){
        while (startIndex < extractText.size()) {
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
