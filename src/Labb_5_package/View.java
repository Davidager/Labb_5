package Labb_5_package;

import javax.swing.*;
import java.awt.*;

/**
 * Created by David on 04-Dec-16.
 */
public class View extends JPanel{
    private JTextField textField;

    public View(){
        super(new GridBagLayout());
        createTextField();
    }

    private void createTextField() {
        textField = new JTextField(20);
        //textField.addActionListener(this);
        add(textField);
    }
}
