package concurent.controller;

import concurent.Model.*;

import java.util.ArrayList;

public class controller {

    private ArrayList<CounterAgent> counterAgent;
    private ExtractText extractText;
    private CountAndExtracView view;
    private TaskCompletionLatch synch;

    public controller(ArrayList<CounterAgent> counterAgent,CountAndExtracView view,TaskCompletionLatch synch){
        this.counterAgent =counterAgent;
        this.view =view;
        this.synch = synch;
    }
    public synchronized void started(){
        int nWorkers = Runtime.getRuntime().availableProcessors() +1;
        new Master(nWorkers,extractText,counterAgent,view,synch).start();

    }
    public synchronized void stopped(){
        synch.stop();

    }





}
