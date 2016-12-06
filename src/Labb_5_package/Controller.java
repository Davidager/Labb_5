package Labb_5_package;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by David on 04-Dec-16.
 */
public class Controller extends JPanel implements ActionListener, HyperlinkListener {
    private Model model;

    private static int X_DIM = 1000;
    private static int Y_DIM = 700;

    private JTextField textField;
    private JEditorPane editorPane;
    private JButton forwardButton;
    private JButton backwardButton;
    private JButton historyButton;

    public Controller() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        //setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(X_DIM,Y_DIM));
        model = new Model();

        textField = new JTextField(20);
        textField.addActionListener(this);
        textField.setMaximumSize(new Dimension(X_DIM,24));
        //textField.setPreferredSize(new Dimension(1000,20));

        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.addHyperlinkListener(this);
        JScrollPane editorScrollPane = new JScrollPane(editorPane);

        forwardButton = new JButton("Framåt");
        backwardButton = new JButton("Bakåt");
        historyButton = new JButton("Historik");

        forwardButton.addActionListener(this);
        backwardButton.addActionListener(this);
        historyButton.addActionListener(this);

        forwardButton.setEnabled(false);
        backwardButton.setEnabled(false);
        historyButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backwardButton);
        buttonPanel.add(forwardButton);
        buttonPanel.add(historyButton);
        buttonPanel.setMaximumSize(new Dimension(X_DIM,20));

        add(buttonPanel);
        add(textField);
        add(editorScrollPane);

    }

    public void setEditorWebPage(URL textURL) {
        //System.out.println(textURL);
        try {
            editorPane.setPage(textURL);

            textField.setText(textURL.toString());

        } catch (IOException ioExc) {
            //System.err.println("Attempted to read a bad URL: " + textURL);
            JOptionPane.showMessageDialog(this, "Attempted to read a bad URL: " + textURL + '\n'
                    + "exception: " + ioExc);
        }

    }

    public void updateFwBwButtons() {
        Boolean[] buttonOnArray = model.getButtonStatus();
        if (buttonOnArray[0]) {
            backwardButton.setEnabled(true);
        } else {
            backwardButton.setEnabled(false);
        }
        if (buttonOnArray[1]) {
            forwardButton.setEnabled(true);
        } else {
            forwardButton.setEnabled(false);
        }
    }

    public void createAndDisplayHistoryList() {
        JFrame listFrame = new JFrame("Historik");
        listFrame.setLayout(new BoxLayout(listFrame.getContentPane(), BoxLayout.PAGE_AXIS));

        URL[] historyArray = model.getHistoryArray();
        JList list = new JList(historyArray);

        JScrollPane listScroller = new JScrollPane(list);
        listFrame.add(listScroller);

        JButton okButton = new JButton("Välj");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (list.getSelectedValue()==null) {
                    JOptionPane.showMessageDialog(listFrame, "Välj ett av alternativen");
                } else {
                    URL newURL = (URL)list.getSelectedValue();
                    setEditorWebPage(newURL);
                    model.moveToNewPage(newURL);
                    updateFwBwButtons();
                }

            }
        });
        listFrame.add(okButton);
        listFrame.pack();
        listFrame.setVisible(true);
    }

    /* från stackoverflow, är beroende av att google.com är uppe, men det är väl den oftast!
    */
    public boolean isConnectedToInternet() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }


    public void actionPerformed(ActionEvent e){
        if(!isConnectedToInternet()) {
            JOptionPane.showMessageDialog(this, "No internet connection");
            return;
        }
        if (e.getSource() == textField) {
            try {
                URL textURL = new URL(textField.getText());
                setEditorWebPage(textURL);
                model.moveToNewPage(textURL);
                updateFwBwButtons();
                historyButton.setEnabled(true);
            } catch (MalformedURLException urlExc) {
                JOptionPane.showMessageDialog(this, "Attempted to read a bad URL: " + textField.getText() + '\n'
                                                + "exception: " + urlExc);

            }

        }

        if (e.getSource() == forwardButton) {
            model.moveForward();
            updateFwBwButtons();
            setEditorWebPage(model.getCurrentURL());
        }

        if (e.getSource() == backwardButton) {
            model.moveBackward();
            updateFwBwButtons();
            setEditorWebPage(model.getCurrentURL());

        }

        if (e.getSource() == historyButton) {
            createAndDisplayHistoryList();
        }

    }

    public void hyperlinkUpdate(HyperlinkEvent e) {
        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if(!isConnectedToInternet()) {
                JOptionPane.showMessageDialog(this,"No internet connection");
                return;
            }
            URL hyperURL = e.getURL();
            setEditorWebPage(hyperURL);
            model.moveToNewPage(hyperURL);
            updateFwBwButtons();
        }
    }

}
