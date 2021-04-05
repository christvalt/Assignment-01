package concurent.controller;

import concurent.Model.*;

import java.util.ArrayList;

public class controller {


    private Master master;
    private CounterAgent counterAgent;
    private int nWorkers ;
    private TaskCompletionLatch synch;

    public controller(CounterAgent counterAgent,int nWorkers){
        this.counterAgent =counterAgent;
        this.nWorkers =nWorkers;
        synch = new TaskCompletionLatch(nWorkers);
    }
    public synchronized void Started(){
        int nWorkers = Runtime.getRuntime().availableProcessors() +1;
        synch = new TaskCompletionLatch(nWorkers);

        // new Master(nWorkers,extractAgent,counterAgent,view,synch).start();

    }
    public synchronized void Stopped(){
        synch.stop();

    }





}
