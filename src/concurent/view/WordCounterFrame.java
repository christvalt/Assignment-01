package concurent.view;


//import View.DirectoryChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

import javax.swing.*;

import concurent.Model.CounterAgent;
import concurent.Model.InitialWordCounter;
import concurent.controller.Controller;

public class WordCounterFrame extends JFrame implements ActionListener{

	//private VisualiserPanel bodyAreaPanel;
	private JButton browseButton1;
    private JButton browseButton2;
    private Label dirLabel;
    private Label maxOccurrenceLabel; 
    private JTextField dirField;
    private JTextField fileField; 
    private JButton startbutton;
    private JButton stopbutton;;
	private InitialWordCounter initialWordCounter;
	private FrequentwordPanel frequentWordPanel;
	private JLabel lbl;
	private ArrayList<InputListener> listeners;
	private View view;

	// Constructor to setup the GUI components
	public WordCounterFrame(int w, int h, Controller controller, CounterView view) {
		super("Word Occurrences Counter");
		setSize(w,h);

		listeners = new ArrayList<InputListener>();

		setSize(500, 260);
		setResizable(false);
		Label dirLabel = new Label("Directory : ");
		dirLabel.setBounds(0, 50, 100, 35);
		JTextField dirField = new JTextField();
		dirField.setBounds(100, 50, 230, 30);
		
		JButton browseButton1 = new JButton("Browse");
		browseButton1.setBounds(350, 50, 90, 30);
	    browseButton1.addActionListener(this);
	    
	    Label fileLabel = new Label("Select  File:");
	    fileLabel.setBounds(0, 60, 100, 85);
		
		
		JTextField fileField = new JTextField();
		fileField.setBounds(100, 90, 230, 30);
		
		
		JButton browseButton2 = new JButton("Browse");
		browseButton2.setBounds(350, 90, 90, 30);
	    browseButton2.addActionListener(this);
		
		Label maxOccurrenceLabel = new Label("frequent words: ");
		maxOccurrenceLabel.setBounds(0, 80, 100, 140);
		
		
		JTextField maOccurrenceField = new JTextField();
		maOccurrenceField.setBounds(100, 130, 40, 30);
		add(maOccurrenceField);
		//browseButton.addActionListener(this);
		
		add(dirLabel);
		add(dirField);
		add(browseButton1);
		add(fileLabel);
		add(fileField);
		add(browseButton2);
		add(maxOccurrenceLabel);


		startbutton = new JButton("Start");
		stopbutton = new JButton("Stop");
		JPanel panel = new JPanel();
		panel.add(startbutton);
		panel.add(stopbutton);
		panel.setBounds(150,180,140, 40);
		getContentPane().add(panel);

		frequentWordPanel = new FrequentwordPanel(w,h);
		frequentWordPanel.setSize(w,h);

	    setLayout(null); 
		setVisible(true);

		startbutton.setEnabled(true);
		startbutton.addActionListener(ev -> {
			startbutton.setEnabled(false);
			stopbutton.setEnabled(true);
			controller.started(view);
		});

		stopbutton.setEnabled(false);
		stopbutton.addActionListener(ev -> {
			startbutton.setEnabled(true);
			stopbutton.setEnabled(false);
			controller.stopped(view);
		});




		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
				System.exit(-1);
			}
			public void windowClosed(WindowEvent ev){
				System.exit(-1);
			}
		});
	
	}

	public void display(InitialWordCounter initialWordCounter, Semaphore done){
		SwingUtilities.invokeLater(() -> {
			frequentWordPanel.display(initialWordCounter, done);
			//System.out.println("**************************************************************************");
		});


	}


	public void addListener(InputListener l){
		listeners.add(l);
	}

	public void actionPerformed(ActionEvent ev){
		String cmd = ev.getActionCommand();
		if (cmd.equals("start")){
			notifyStarted();
		} else if (cmd.equals("stop")){
			notifyStopped();
		}
	}

	private void notifyStarted(){
		for (InputListener l: listeners){
			l.started(view);
		}

	}

	private void notifyStopped(){
		for (InputListener l: listeners){
			l.stopped(view);
		}
	}


	public class FrequentwordPanel extends JFrame{

		private CounterAgent counterAgent;
		private Semaphore done;
		private InitialWordCounter initialWordCounter;

		public FrequentwordPanel(int w, int h) {

			setTitle("Word frenquence Count");
			//setSize(900, 650);

			setResizable(false);
			JPanel panel = new JPanel();
			getContentPane().add(panel);
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent ev){
					System.exit(-1);
				}
				public void windowClosed(WindowEvent ev){
					System.exit(-1);
				}
			});
			setLayout(null);
			setVisible(true);

		}
		public void display(InitialWordCounter initialWordCounter, Semaphore done) {
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			try{this.initialWordCounter = initialWordCounter;
				this.done = done;
				AtomicInteger countWords = new AtomicInteger();
				//lbl.setText(lbl.getText() + InitialWordCounter() + " words analysed <br/>");
				InitialWordCounter.getSortedWordCount().forEach((s, i) -> {
					if (countWords.get() % 5 == 0){
						lbl.setText(lbl.getText() + "<br/>");
					}
					lbl.setText(lbl.getText() +  s + " (" + i + " times) &nbsp; &nbsp;");
					countWords.getAndIncrement();
				});}catch (Exception ex){
				ex.printStackTrace();


			}
			this.setVisible(true);

		}
		public void updater(String count){
			lbl.setText(count);
		}


	}

}


	



