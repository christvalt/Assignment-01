package concurent.Model;

import concurent.view.InterfaceFrame;

public class CountAndExtracView {


    private InterfaceFrame gui;

    public CountAndExtracView() {}

    public void update(WordCount map){
        map.updatedCount(map);
    }
    public void changeState(final String s){
        InterfaceFrame.updateText(s);
    }


}