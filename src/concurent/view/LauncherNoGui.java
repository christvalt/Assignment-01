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


        String absolutep = new File("").getAbsolutePath()+"/src/concurent/doc/";
        String defaultDirectoryPath = "take";
        String defaultIgnoreFilePath = "empty.txt";

        String d = absolutep +  defaultDirectoryPath;
        String f = absolutep +defaultIgnoreFilePath;
        //int n = args.length >= 3 ? Integer.parseInt(args[2]) : defaultN;


        InitialWordCounter wordCount = new InitialWordCounter();
        ArrayList<CounterAgent> counterAgent = new ArrayList<CounterAgent>();
        TaskCompletionLatch synch = new TaskCompletionLatch(nWorkers);
        Master master = new Master(f,d,synch,nWorkers);

        //CounterAgent ca = new CounterAgent(extractAgent,wordCount ,synch);
        master.start();
      // ca.start();

    }
}

