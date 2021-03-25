package concurent;

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
       String words = new String("faccio lezione sta  io an io");

        System.out.println(wordCount.computeWord(words));
    }
}

