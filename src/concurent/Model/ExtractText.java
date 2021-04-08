
package concurent.Model;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import static java.lang.Math.min;

public class ExtractText  {

    private  PDDocument document;
    private final List<String> wordsToIgnore;
    private int p = 1;
    private List<ExtractText> extractText;


    public ExtractText(final File DIRECTORY , final List<String> wordsToIgnore) {
        try {
            this.loadDocument(DIRECTORY);
        } catch( IOException e) {
            e.printStackTrace();
        }
        this.wordsToIgnore = wordsToIgnore;
        //System.out.println("test");
    }

    private void loadDocument(final File file) throws IOException {
        try {
            this.document = PDDocument.load(file);
            AccessPermission ap = this.document.getCurrentAccessPermission();
            if (!ap.canExtractContent()) {
                throw new IOException("You do not have permission to extract text");
            }
            System.out.println("OK " + file.getName());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public  Optional<List<String>> extractor(final int numPages){
       // System.out.println("test333");
        try {
            PDFTextStripper stripper = new PDFTextStripper();
           // stripper = new PDFTextStripper();
            if(p<= document.getNumberOfPages()){
                stripper.setStartPage(p);
                p = min(document.getNumberOfPages(), p +numPages);
                stripper.setEndPage(p);
                p += 1;
                String text = (stripper.getText(document).toLowerCase());
                List<String> words = new ArrayList<>(Arrays.stream(text.split("\\W+")).collect(Collectors.toList()));
                for (String toIgnore : wordsToIgnore) {
                    words.removeIf(word -> word.equals(toIgnore));
                }
                return Optional.of(words);//Optional.of(words);

            }

        }catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

}


