package concurent.view;

import concurent.Model.CounterAgent;
import concurent.Model.ExtractText;
import concurent.Model.Master;
import concurent.Model.TaskCompletionLatch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class LauncherNoGui
{
    public static void main(String[] args) throws IOException

    {
        int nWorkers = Runtime.getRuntime().availableProcessors() + 1;
        File DIRECTORY = new File(new File("C:\\Users\\camerum\\Desktop\\SW\\15.semanticWeb.pdf").getAbsolutePath());
        String ignored = new File("C:\\Users\\camerum\\Desktop\\improve\\exlude.txt").getAbsolutePath() ;

        if (args.length > 0) {
            if (args.length == 1) {
                nWorkers = Integer.parseInt(args[0]);
            } else {
                System.err.println("Params: <nworkers>");
                System.exit(-1);
            }
        }
        ExtractText extractText = new ExtractText(DIRECTORY,ignored);
        ArrayList<CounterAgent> counterAgent = new ArrayList<CounterAgent>();
        TaskCompletionLatch synch = new TaskCompletionLatch(nWorkers);
        Master master = new Master(nWorkers,extractText,counterAgent,null,synch);
        master.start();

    }
}

