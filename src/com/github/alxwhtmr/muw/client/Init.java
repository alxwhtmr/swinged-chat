package com.github.alxwhtmr.muw.client;

import com.github.alxwhtmr.muw.client.gui.Client;

/**
 * Created on 05.11.2014.
 */
public class Init {
    public static void main(String[] args) throws java.io.UnsupportedEncodingException {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final Client client = new Client();
                client.createAndShowGUI();
                client.connect();
            }
        });
    }
}
