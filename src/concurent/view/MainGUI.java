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
        String defaultPath = "ignore/empty.txt";


        String d = absolutep +  defaultDirectoryPath;
        String f = absolutep +defaultPath;
        

        int nWorkers = Runtime.getRuntime().availableProcessors() + 1;
        Flag stopFlag = new Flag();


        InitialWordCounter wordCount = new InitialWordCounter();

        TaskCompletionLatch synch = new TaskCompletionLatch(nWorkers);
        InitialWordCounter ini= new InitialWordCounter();

        Controller controller = new Controller(f,d,nWorkers);
        CounterView view = new CounterView(880,620, controller ,f,d,ini);
        view.addListener(controller);
        view.showUp();

       // master.start();



    }



}
