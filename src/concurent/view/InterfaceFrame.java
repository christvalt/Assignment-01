package concurent.view;

import javax.swing.*;

public class InterfaceFrame {
    private JTextField state;

    public static void updateText(final String s){
        SwingUtilities.invokeLater(() -> {
            //state.setText(s);
        });
    }

    public void addListener(InputListener l) {

    }

    public void setVisible(boolean b) {

    }

    public void updatecount() {

    }
}
