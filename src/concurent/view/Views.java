package concurent.view;

import concurent.Model.ModelObserver;
import concurent.Model.MyModel;

public class Views implements ModelObserver {
    @Override
    public void modelUpdated(MyModel model) {
        System.out.println(model.getTotWords() + " words analysed");
        model.getSortedWordCount().forEach((w, count) -> System.out.println(w +": " + count +" times"));

    }
}
