package concurent.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class ViewerFrame extends JFrame{

	 private VisualiserPanel panel;
	 
	public ViewerFrame(int w, int h) {
		
		setTitle("Word Occurences Counter");
        setSize(w,h);
        setResizable(false);
        panel = new VisualiserPanel(w,h);
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
	
	public static class VisualiserPanel extends JPanel {
       
        private long dx;
        private long dy;
        
        public VisualiserPanel(int w, int h){
            setSize(w,h);
            dx = w/2 - 20;
            dy = h/2 - 20;
        }

	}
}

