package concurent.Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Master extends Thread{

    private File DIRECTORY;
    private int nWorkers;
    //private concurent.controller.Flag stopFlag;
   // private View view;
    private InitialWordCounter wordCount;
    private TaskCompletionLatch synch;
    private CountAndExtracView view;
    private final List<String> wordsToIgnore;
    //private final int outputWords;
    //private final ExtractAgent extractAgent;

    private ArrayList<CounterAgent> counterAgent;
    private final ExtractAgent extractAgent;


    public Master(final String ignored ,final String directoryPath, TaskCompletionLatch synch) throws IOException {
        this.nWorkers=nWorkers;
        this. wordsToIgnore = Files.readAllLines(new File(ignored).toPath());
        this.extractAgent = new ExtractAgent(new ExtractText(DIRECTORY, this.wordsToIgnore));
        this.counterAgent=counterAgent;
        //this.stopFlag =stopFlag;
       // this.view=view;
            this.synch=synch;

        //this.outputWords = wordsNumber;
    }

    public void run(){
       // boolean stopped = false;
        int nCounterAgent = nWorkers;

        counterAgent = new ArrayList<CounterAgent>();
        for (int i = 0; i < nCounterAgent ; i++){
            CounterAgent worker = new CounterAgent( this.extractAgent,wordCount,synch);
            counterAgent.add(worker);
            worker.start();
        }
        CounterAgent worker = new CounterAgent( this.extractAgent,wordCount,synch);
        worker.start();
        /* main loop
        System.out.println("error mater 1");

        log("wait completion");
        try {
            System.out.println("test  mater 1");
            synch.waitCompletion();
            log("completion arrived");
            view.update(wordCount);

        } catch (InterruptedException ex) {
            log("interrupted");
            view.changeState("Interrupted");
        }
        System.out.println("test 3");*/

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





