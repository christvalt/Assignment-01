package concurent.controller;

import concurent.Model.*;
import concurent.view.InputListener;
import concurent.view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller implements InputListener {


    private Master master;
    private CounterAgent counterAgent;
    private int nWorkers ;
    private TaskCompletionLatch synch;
    private Flag stopFlag;
    private  String d ;
    private String f ;


    public Controller(String f ,String d, int nWorkers){
        this.d=d;
        this.f=f;
        this.counterAgent =counterAgent;
        this.nWorkers =nWorkers;
        synch = new TaskCompletionLatch(nWorkers);
        this.stopFlag = new Flag();

    }

    @Override
    public synchronized  void started(View view) {
        stopFlag.reset();
        synch = new TaskCompletionLatch(nWorkers);
        try {
            master = new Master(f,d,view,synch,nWorkers,stopFlag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        master.start();

    }

    @Override
    public synchronized void stopped(View view) {
        master.interrupt();
        stopFlag.set();

    }
}
