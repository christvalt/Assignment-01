package concurent.Model;

import concurent.controller.Flag;
import concurent.view.View;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

public class Master extends Thread{

    private File DIRECTORY;
    private FilenameFilter filter;
    private int nWorkers;
    //private concurent.controller.Flag stopFlag;
    // private View view;
    private InitialWordCounter wordCount;
    private TaskCompletionLatch synch;
    private View view;
    private final List<String> wordsToIgnore;
    //private final int outputWords;
    //private final ExtractAgent extractAgent;

    private ArrayList<CounterAgent> counterAgent;
    private final ExtractAgent extractAgent;
    private int totalWordByThread = 0;
    private int totalwords = 0;
    private Lock lock;
    private Flag stopFlag;




    public Master(final String ignored, final String directoryPath , View view, TaskCompletionLatch synch, int nWorkers, Flag stopFlag) throws IOException {
        this.nWorkers=nWorkers;
        System.out.println("test ingre"+ignored);
        this. wordsToIgnore = Files.readAllLines(new File(ignored).toPath());

        this.filter = new FilenameFilter() {
            public boolean accept(File f, String name) {
                return name.endsWith(".pdf");}
        };
        File[] files = new File (directoryPath).listFiles(this.filter);
        this.extractAgent = new ExtractAgent(Arrays.stream(files).map(f -> new ExtractText(f, this.wordsToIgnore)).collect(Collectors.toList()));
        this.counterAgent=counterAgent;
        this.synch=synch;
        this.lock =lock;
        this.stopFlag= stopFlag;
        this.view=view;

    }

    public void run(){
        //view.changeState("Processing...");
        long t0 = System.currentTimeMillis();

        int nCounterAgent = nWorkers;
        counterAgent = new ArrayList<CounterAgent>();
        for (int i = 0; i < nCounterAgent ; i++){
            CounterAgent worker = new CounterAgent(this.extractAgent, stopFlag, synch);
            counterAgent.add(worker);
            worker.start();
        }

        // main loop
        log("wait completion");
        try {
            synch.waitCompletion();
            log("completion arrived");
            //view.update(wordCount);
            if (view != null) {
                view.update();
                //stat.notifyDisplayCompleted();
            }

            long t1 = System.currentTimeMillis();
            // view.changeState("Completed - time elapsed: "+(t1-t0));

        } catch (InterruptedException ex) {
            log("interrupted");
            //view.changeState("Interrupted");

        }


        /* to stop CounterAgent */
        //stopFlag.set();
        for (CounterAgent c: counterAgent) {
            c.interrupt();
        }
    }





    private void log(String msg) {
        // System.out.println("[COLLISION CHECKER " + Thread.currentThread().getName() +"] " + msg);
        synchronized(System.out) {
            System.out.println("[ "+getName()+" ] "+msg);
        }
    }

   /* public void update(wordCuntener countener){
        update((countener.getWordCuntener()));
    }*/

}
