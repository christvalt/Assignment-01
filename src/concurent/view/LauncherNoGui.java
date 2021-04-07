package concurent.view;

import concurent.Model.*;
import concurent.controller.Flag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LauncherNoGui
{
    public static void main(String[] args) throws IOException

    {
        int nWorkers = Runtime.getRuntime().availableProcessors() + 1;


        String absolutep = new File("").getAbsolutePath()+"/src/concurent/doc/";
        String defaultDirectoryPath = "PDF/PDF";
        String defaultIgnoreFilePath = "ignore/empty.txt";

        String d = absolutep +  defaultDirectoryPath;
        String f = absolutep +defaultIgnoreFilePath;
        //int n = args.length >= 3 ? Integer.parseInt(args[2]) : defaultN;

       // Lock lock = new ReentrantLock();
        InitialWordCounter wordCount = new InitialWordCounter();
        ArrayList<CounterAgent> counterAgent = new ArrayList<CounterAgent>();
        TaskCompletionLatch synch = new TaskCompletionLatch(nWorkers);
        Master master = new Master(f,d,synch,nWorkers,new Flag());

        //CounterAgent ca = new CounterAgent(extractAgent,wordCount ,synch);
        master.start();
      // ca.start();



    }
}

