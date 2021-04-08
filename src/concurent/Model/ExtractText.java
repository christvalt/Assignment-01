
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

    public  Optional<List<String>> extractor(){
       // System.out.println("test333");
        try {
            PDFTextStripper stripper = new PDFTextStripper();
           // stripper = new PDFTextStripper();
            while(p<= document.getNumberOfPages()){
                stripper.setStartPage(p);
                //p = min(document.getNumberOfPages(), p );
                stripper.setEndPage(p);
                p += 1;
                String text = (stripper.getText(document));
                List<String> words = new ArrayList<>(Arrays.stream(text.split("\\W+")).collect(Collectors.toList()));
                for (String toIgnore : wordsToIgnore) {
                    words.removeIf(word -> word.equals(toIgnore));
                }
                return Optional.of(words);//Optional.of(words);

            }
            /*for (int p = 1; p <= document.getNumberOfPages(); ++p) {
                // Set the page interval to extract. If you don't, then all pages would be extracted.
                stripper.setStartPage(p);
                stripper.setEndPage(p);
                // let the magic happen
                String text = stripper.getText(document);
               // System.out.println("test333"+p);
                List<String> words = new ArrayList<>(Arrays.stream(text.split("\\W+")).collect(Collectors.toList()));
                System.out.println("tess0000"+words);
                for (String toIgnore : wordsToIgnore) {
                    words.removeIf(word -> word.equals(toIgnore));
                }
                //System.out.println(text);
                return Optional.of(words);
            }*/
            //document.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}

/*
* public  void textExtractor(final  File DIRECTORY ) throws IOException {

        //final  File DIRECTORY = new File("C:\\Users\\camerum\\Desktop\\SW\\");

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File f, String name) {
                return name.endsWith(".pdf");
            }
        };
        File[] files = DIRECTORY.listFiles(filter);


            for (int j = 0; j < files.length; j++) {
                document = PDDocument.load(files[j]);
                System.out.println(document);
                System.out.println(files[j].getName());
                AccessPermission ap = document.getCurrentAccessPermission();
                if (!ap.canExtractContent()) {
                    throw new IOException("You do not have permission to extract text");
                }
            }
        ///System.out.println(AllTheText.get(0));
        System.out.println("my size is "+ AllTheText.size());


    }*/
