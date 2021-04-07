package concurent.view;


//import View.DirectoryChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import concurent.*;
import concurent.Model.CounterAgent;
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
	private ArrayList<InputListener> listeners;
	//private FrequentWordPanel setPanel;

	 // Constructor to setup the GUI components
	public WordCounterFrame(Controller controller, CounterView view) {
		super("Word Occurrences Counter");
		
		//listeners = new ArrayList<InputListener>();

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
		//startbutton.addActionListener(this);
		stopbutton = new JButton("Stop");
		//stopbutton.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(startbutton);
		panel.add(stopbutton);
		panel.setBounds(150,180,140, 40);
		getContentPane().add(panel);

		/*setPanel = new FrequentWordPanel(w,h);
		setPanel.setSize(w,h);*/
	
	    setLayout(null); 
		setVisible(true);

		startbutton.setEnabled(true);
		startbutton.addActionListener(ev -> {
			startbutton.setEnabled(false);
			stopbutton.setEnabled(true);
			try {
				controller.Started(view);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		stopbutton.setEnabled(false);
		stopbutton.addActionListener(ev -> {
			startbutton.setEnabled(true);
			stopbutton.setEnabled(false);
			controller.Stopped(view);
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
	public void showUp() {
		SwingUtilities.invokeLater(() -> {
			setVisible(true);
		});
	}
	public void display(CounterAgent counterAgent, Lock lock){
		viewerFrame.display(counterAgent, lock);
	}


	private ViewerFrame viewerFrame;
	public class ViewerFrame extends JFrame{

		private CounterAgent counterAgent;
		private Lock lock;

		public ViewerFrame(int w, int h) {

			setTitle("Word Occurences Counter");
			setSize(800, 650);

			setResizable(false);
			JPanel panel = new JPanel();
			// panel.setSize(650,650);
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
		public void display(CounterAgent counterAgent, Lock lock) {
			this.counterAgent = counterAgent;
			this.lock = lock;
			repaint();
		}


	}



	/*public void display(Simulation sim, Semaphore done){
		bodyAreaPanel.display(sim, done);
	}*/
	
	
	
	
	/*public void updateImage(int[] image){
		SwingUtilities.invokeLater(() -> {
				setPanel.updateImage(image);
		});
	}*/
	
	/*public void updateText(final String s){
		SwingUtilities.invokeLater(() -> {
				state.setText(s);
		});
	}*/

	/*public void addListener(InputListener l){
		listeners.add(l);
	}*/
	
	/*public void actionPerformed(ActionEvent ev){
		String cmd = ev.getActionCommand(); 
		if (cmd.equals("start")){
			notifyStarted();
		} else if (cmd.equals("stop")){
			notifyStopped();
		}
	}*/

	/*private void notifyStarted(){
		Complex c0 = new Complex(Double.parseDouble(cx.getText()),Double.parseDouble(cy.getText()));
		double d = Double.parseDouble(diam.getText());
		for (InputListener l: listeners){
			l.started(c0, d);
		}
	}*/
	
	/*private void notifyStopped(){
		for (InputListener l: listeners){
			l.stopped();
		}
	}*/
	
	
	
	// methods to define actions to performed

	@Override
	public void actionPerformed(ActionEvent ae) {
	
   //Handle browse button
	
	               	
	
   //Handle start button
	
	
	
	
   //Handle stop button
	
   }


	/*public class FrequentWordPanel extends JPanel {

		private BufferedImage image;

		public FrequentWordPanel(int w, int h){
			this.image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		}

		public void updateImage(int[] rgbData){
			int w = image.getWidth();
			int h = image.getHeight();
			image.setRGB(0, 0, w, h, rgbData, 0, w);
			repaint();
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.drawImage(image, 0, 0, null);
		}
	}*/

    
}


	



