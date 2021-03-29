
package concurent.Model;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

public class ExtractText implements List<ExtractText> {

    private  PDDocument document;
    private final List<String> wordsToIgnore;
    private int startIndex;
    private List<ExtractText> extractText;


    public ExtractText(final File DIRECTORY , final List<String> wordsToIgnore) {
        try {
            this.loadDocument(DIRECTORY);
        } catch( IOException e) {
            e.printStackTrace();
        }
        this.wordsToIgnore = wordsToIgnore;
        System.out.println("test");
    }

    private void loadDocument(final File file) throws IOException {
        try {
            this.document = PDDocument.load(file);
            AccessPermission ap = this.document.getCurrentAccessPermission();
            if (!ap.canExtractContent()) {
                throw new IOException("You do not have permission to extract text");
            }
            //this.document = file.getName();
            System.out.println("Loaded " + file.getName());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public  Optional<List<String>> extractor(){
        try {
            PDFTextStripper stripper = new PDFTextStripper();
           // stripper = new PDFTextStripper();
            for (int p = 1; p <= document.getNumberOfPages(); ++p) {
                // Set the page interval to extract. If you don't, then all pages would be extracted.
                stripper.setStartPage(p);
                stripper.setEndPage(p);
                // let the magic happen
                String text = stripper.getText(document);
                List<String> words = new ArrayList<>(Arrays.stream(text.split("\\W+")).collect(Collectors.toList()));
                for (String toIgnore : wordsToIgnore) {
                    words.removeIf(word -> word.equals(toIgnore));
                }
                System.out.println(text);
                return Optional.of(words);
            }
            document.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
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


    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<ExtractText> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(ExtractText extractText) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends ExtractText> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends ExtractText> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public ExtractText get(int index) {
        return null;
    }

    @Override
    public ExtractText set(int index, ExtractText element) {
        return null;
    }

    @Override
    public void add(int index, ExtractText element) {

    }

    @Override
    public ExtractText remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<ExtractText> listIterator() {
        return null;
    }

    @Override
    public ListIterator<ExtractText> listIterator(int index) {
        return null;
    }

    @Override
    public List<ExtractText> subList(int fromIndex, int toIndex) {
        return null;
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
