package com.github.alxwhtmr.muw.client.gui;

import com.github.alxwhtmr.muw.common.Common;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alexbel on 02.09.2014.
 */
public class CustomTextArea extends JTextArea implements ActionListener {
    public CustomTextArea(int rows, int columns) {
        super(rows, columns);
        this.setEditable(false);
        this.setBackground(Color.BLACK);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Fixedsys",Font.PLAIN,14));
        this.setText(Common.Constants.Hints.NAME_HINT);
    }

    public void actionPerformed(ActionEvent evt) {

    }
}
