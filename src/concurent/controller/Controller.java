package concurent.controller;

import concurent.Model.*;
import concurent.view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {


    private Master master;
    private CounterAgent counterAgent;
    private int nWorkers ;
    private TaskCompletionLatch synch;
    private Flag stopFlag;
   // private final String ignored;
    //private final String directoryPath;

    String absolutep = new File("").getAbsolutePath()+"/src/concurent/doc/";
    String defaultDirectoryPath = "PDF/PDF";
    String defaultIgnoreFilePath = "ignore/empty.txt";

    String d = absolutep +  defaultDirectoryPath;
    String f = absolutep +defaultIgnoreFilePath;


    public Controller(CounterAgent counterAgent, int nWorkers){
        this.counterAgent =counterAgent;
        this.nWorkers =nWorkers;
        synch = new TaskCompletionLatch(nWorkers);
        this.stopFlag = new Flag();

    }

        public synchronized void Started(View view) throws IOException {
            stopFlag.reset();
            synch = new TaskCompletionLatch(nWorkers);
            master = new Master(d,f,synch,nWorkers,stopFlag);
            master.start();
    }
    public synchronized void Stopped( View view ){
        //synch.stop();
        master.interrupt();
        stopFlag.set();

    }





}
