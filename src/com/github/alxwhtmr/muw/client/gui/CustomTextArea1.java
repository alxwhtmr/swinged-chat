package com.github.alxwhtmr.muw.client.gui;

import com.github.alxwhtmr.muw.common.Common;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexbel on 22.09.2014.
 */
public class CustomTextArea1 extends JTextPane {
    public CustomTextArea1(String type, String text) {
//        super(type, text);
        super();
        this.setEditable(false);
        this.setBackground(Color.BLACK);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Fixedsys",Font.PLAIN,18));
        this.setText(Common.Constants.Hints.NAME_HINT);
//        this.setSize(20, 80);
    }
}
