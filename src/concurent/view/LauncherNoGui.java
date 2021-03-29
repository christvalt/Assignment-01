package concurent.view;

import concurent.Model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class LauncherNoGui
{
    public static void main(String[] args) throws IOException

    {
        int nWorkers = Runtime.getRuntime().availableProcessors() + 1;
        //File DIRECTORY = new File(new File("C:\\Users\\camerum\\Desktop\\SW\\15.semanticWeb.pdf").getAbsolutePath());
        //String ignored = String.valueOf(new File(new File("C:\\Users\\camerum\\Desktop\\SW\\").getAbsolutePath()));
        String absolutep = new File("").getAbsolutePath()+"src/concurent/doc/";
        String defaultDirectoryPath = "take";
        String defaultIgnoreFilePath = "ignore/empty.txt";

        String d = absolutep + (args.length >= 1 ? args[0] : defaultDirectoryPath);
        String f = absolutep + (args.length >= 2 ? args[1] : defaultIgnoreFilePath);
        //int n = args.length >= 3 ? Integer.parseInt(args[2]) : defaultN;

        if (args.length > 0) {
            if (args.length == 1) {
                nWorkers = Integer.parseInt(args[0]);
            } else {
                System.err.println("Params: <nworkers>");
                System.exit(-1);
            }
        }

        InitialWordCounter wordCount = new InitialWordCounter();
        ArrayList<CounterAgent> counterAgent = new ArrayList<CounterAgent>();
        TaskCompletionLatch synch = new TaskCompletionLatch(nWorkers);
        Master master = new Master(f,d,synch);

        //CounterAgent ca = new CounterAgent(extractAgent,wordCount ,synch);
        master.start();
      // ca.start();

    }
}

