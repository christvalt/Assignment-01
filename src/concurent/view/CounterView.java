package concurent.view;

import concurent.Model.CounterAgent;
import concurent.Model.InitialWordCounter;

import javax.swing.*;

import concurent.controller.Controller;

import java.util.concurrent.locks.Lock;

public class CounterView implements View {

    private final CounterAgent counterAgent;
    private WordCounterFrame wordCounterFrame;
    private Lock lock;


    public CounterView(int w, int h, Controller controller ,CounterAgent counterAgent){
        wordCounterFrame = new WordCounterFrame(controller ,this);
        this.lock = lock;
        this.counterAgent =counterAgent;

    }


    public void display() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            wordCounterFrame.setVisible(true);
        });
    }

    /*public void update(InitialWordCounter initialWordCounter){
        wordCounterFrame.updatecount();
    }*/

    /*public void changeState(final String s){
        wordCounterFrame.updateText(s);
    }*/


    public void showUp() {
        SwingUtilities.invokeLater(() -> {
            wordCounterFrame.setVisible(true);
        });
    }


    @Override
    public void update() {
        lock.lock();
        try {
            wordCounterFrame.display(counterAgent,lock);
        } finally {
            lock.unlock();
        }
    }
}
