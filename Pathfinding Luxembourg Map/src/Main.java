import javax.swing.*;
import java.awt.*;

public class Main {
    public static void visualInterface() {
        JFrame mainFrame = new JFrame("Luxembourg");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(new DrawMap());
        mainFrame.setSize(870, 670);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                visualInterface();
            }
        });
    }
}
