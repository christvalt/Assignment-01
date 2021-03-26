package concurent;
import concurent.WordCount;

import concurent.ExtractTextSimple;

import java.util.ArrayList;

public class Master extends Thread{

    private int nWorkers;
   // private Flag stopFlag;

    ArrayList<WordCount> wordCount;
    ArrayList<ExtractTextSimple> extratTexrSimple;
    ArrayList<ExtractTextSimple> checkword;

    public Master(int nWorkers){
        this.nWorkers=nWorkers;

    }


    public void run(){
        int wordCount=nWorkers;
        int extratTexrSimple=nWorkers;


    }
    public void update(wordCuntener countener){
        update((countener.getWordCuntener()));
    }

}





