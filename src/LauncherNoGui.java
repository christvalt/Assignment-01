import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LauncherNoGui
{
    public static void main(String[] args) throws IOException



    {
        int nWorkers = Runtime.getRuntime().availableProcessors() + 1;

        if (args.length > 0) {
            if (args.length == 1) {
                nWorkers = Integer.parseInt(args[0]);
            } else {
                System.err.println("Params: <nworkers>");
                System.exit(-1);
            }
        }


        Master master = new Master(nWorkers);
        master.start();
        ExtractTextSimple extractTextSimple = new ExtractTextSimple();
        extractTextSimple.textExtractor();
        WordCount wordCount = new WordCount();
        List<String> words = new ArrayList<String>();
        words.add("d");
        words.add("r");
        words.add("r");
        words.add("t");
        words.add("w");
        words.add("t");
        System.out.println(wordCount.counting(words));
    }
}

