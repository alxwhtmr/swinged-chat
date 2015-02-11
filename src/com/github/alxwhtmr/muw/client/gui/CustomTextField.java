package com.github.alxwhtmr.muw.client.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

/**
 * Created by alexbel on 02.09.2014.
 */
public class CustomTextField extends JTextField implements ActionListener {
    Client client;
    public CustomTextField(Client client, int columns) {
        super(columns);
        this.client = client;
        this.addActionListener(this);
    }

    public void actionPerformed(ActionEvent evt) {
        String text = null;
        try {
            text = new String(this.getText().getBytes(), "Windows-1251");
//            text = this.getText();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        String text = this.getText();
        this.setText("");
        client.getClientCommandProcessor().processCommand(text);
    }
}
