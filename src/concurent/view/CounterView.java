package concurent.view;

import concurent.Model.CounterAgent;
import concurent.Model.InitialWordCounter;

import javax.swing.*;

import concurent.controller.Controller;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class CounterView implements View {

   // private final CounterAgent counterAgent;
    private final InitialWordCounter initialWordCounter;
    private WordCounterFrame wordCounterFrame;
    private Lock lock;
    private  String d ;
    private String f ;
    private JTextField state;


    public CounterView(int w, int h, Controller controller ,String f , String d, InitialWordCounter initialWordCounter){
        wordCounterFrame = new WordCounterFrame(w,h,controller ,this);
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
    public void updateText(final String s){
        SwingUtilities.invokeLater(() -> {
            state.setText(s);
        });
    }

    @Override
    public void update() {
        Semaphore done = new Semaphore(0);
        wordCounterFrame.display(initialWordCounter,done);
        System.out.println("*********************************************************+++++++");
        try {
            done.acquire();
        } catch (Exception ex) {
        }
    }
}
