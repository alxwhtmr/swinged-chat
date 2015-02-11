package com.github.alxwhtmr.muw.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by alexbel on 17.09.2014.
 */
public class CustomSocket extends Socket implements ActionListener {
    public CustomSocket(String host, int port) throws IOException {
        super(host, port);
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println(evt);
    }
}
