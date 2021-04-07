package concurent.view;

import concurent.Model.*;
import concurent.controller.Controller;
import concurent.controller.Flag;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainGUI {


    public static void main(String[] args) throws IOException

    {
        String absolutep = new File("").getAbsolutePath()+"/src/concurent/doc/";
        String defaultDirectoryPath = "PDF/PDF";
        String defaultIgnoreFilePath = "ignore/empty.txt";

        String d = absolutep +  defaultDirectoryPath;
        String f = absolutep +defaultIgnoreFilePath;



        final List<String> wordsToIgnore= Files.readAllLines(new File(f).toPath());;


        FilenameFilter filter= new FilenameFilter() {
            public boolean accept(File f, String name) {
                return name.endsWith(".pdf");}
        };
        File[] files = new File (d).listFiles(filter);
      new ExtractAgent(Arrays.stream(files).map(w -> new ExtractText(w, wordsToIgnore)).collect(Collectors.toList()));
        int nWorkers = Runtime.getRuntime().availableProcessors() + 1;
        Flag stopFlag = new Flag();





        InitialWordCounter wordCount = new InitialWordCounter();
        //ArrayList<CounterAgent> counterAgent = new ArrayList<CounterAgent>();


        TaskCompletionLatch synch = new TaskCompletionLatch(nWorkers);
        //ExtractText extractor = new ExtractText(d,f);
        ExtractAgent extractAgent = new ExtractAgent(Arrays.stream(files).map(w -> new ExtractText(w, wordsToIgnore)).collect(Collectors.toList()));

        CounterAgent counterAgent = new CounterAgent(extractAgent,stopFlag,synch);

        //Master master = new Master(f,d,synch,nWorkers,new Flag());
        Controller controller = new Controller(counterAgent,nWorkers);
        CounterView view = new CounterView(620,620, controller,counterAgent);
        view.showUp();

       // master.start();



    }



}
