package Labb_5_package;

import javax.swing.*;

/**
 * Created by David on 04-Dec-16.
 */
public class Labb5_MyFrame extends JFrame{
    public Labb5_MyFrame() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        Controller controller = new Controller();
        add(controller);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new Labb5_MyFrame();
        } catch (Exception e) {
            System.out.println("asd");
        }
    }
}
