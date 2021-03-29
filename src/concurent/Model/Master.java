package concurent.Model;

import java.util.ArrayList;

public class Master extends Thread{

    private int nWorkers;
    //private concurent.controller.Flag stopFlag;
   // private View view;
    private WordCount wordCount;
    private TaskCompletionLatch synch;
    private CountAndExtracView view;

    private ArrayList<CounterAgent> counterAgent;
    private final ExtractText extractText;


    public Master(int nWorkers, ExtractText extractText , ArrayList<CounterAgent> counterAgent,CountAndExtracView view,TaskCompletionLatch synch){
        this.nWorkers=nWorkers;
        this.extractText =extractText;
        this.counterAgent=counterAgent;
        //this.stopFlag =stopFlag;
       // this.view=view;
        this.synch=synch;
    }

    public void run(){
        boolean stopped = false;
        //nWorkers = Runtime.getRuntime().availableProcessors() + 1;
        int nCounterAgent = nWorkers;
        for (int i = 0; i < nWorkers ; i++){
            CounterAgent worker = new CounterAgent(extractText,wordCount,synch);
            worker.start();
        }
        CounterAgent worker = new CounterAgent(extractText,wordCount,synch);
        worker.start();
        /* main loop */
        log("wait completion");
        try {
            synch.waitCompletion();
            log("completion arrived");
            view.update(wordCount);

        } catch (InterruptedException ex) {
            log("interrupted");
            view.changeState("Interrupted");
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





