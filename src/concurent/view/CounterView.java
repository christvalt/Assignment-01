package concurent.view;

import concurent.Model.CounterAgent;
import concurent.Model.InitialWordCounter;

import javax.swing.*;

import concurent.controller.Controller;

import java.util.concurrent.locks.Lock;

public class CounterView implements View {

   // private final CounterAgent counterAgent;
    private final InitialWordCounter initialWordCounter;
    private WordCounterFrame wordCounterFrame;
    private Lock lock;
    private  String d ;
    private String f ;


    public CounterView(int w, int h, Controller controller ,String f , String d, InitialWordCounter initialWordCounter){
        wordCounterFrame = new WordCounterFrame(controller ,this);
        this.lock = lock;
       // this.counterAgent =counterAgent;
        this.initialWordCounter =initialWordCounter;

    }


    public void display() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            wordCounterFrame.setVisible(true);
        });
    }
    public void addListener(InputListener l){
        wordCounterFrame.addListener(l);
    }



    public void showUp() {
        SwingUtilities.invokeLater(() -> {
            wordCounterFrame.setVisible(true);
        });
    }


    @Override
    public void update() {
        lock.lock();
        try {
            wordCounterFrame.display(initialWordCounter,lock);
        } finally {
            lock.unlock();
        }
    }
}
