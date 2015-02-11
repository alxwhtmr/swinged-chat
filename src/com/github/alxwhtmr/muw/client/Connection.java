package com.github.alxwhtmr.muw.client;

import com.github.alxwhtmr.muw.client.gui.Client;
import com.github.alxwhtmr.muw.common.Common;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Scanner;

/**
 * Created on 03.09.2014.
 */
public class Connection  {
    protected PrintWriter out;

    private Socket clientSocket;
    private BufferedReader in;
    private boolean established;
    private Client client;
    private String connectionLine;
    private String clientName;
    private String host;
    private int port;

    public Connection(Client client) {
        this.client = client;
        established = false;
    }

    public void establish(String host, int port) {
        this.host = host;
        try {
            clientSocket = new Socket(host, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream(), "Windows-1251");
            in = new BufferedReader(isr);
            initClientName();
            established = true;
            connectionLine = "Connected to " + host + " " + new Date();
            client.appendText(connectionLine);

            Runnable r1 = new Runnable() {
                public void run() {
                    String incomingMsg;
                    while (true) {
                        try {
                            incomingMsg = in.readLine();
                            client.appendText(incomingMsg);
                        } catch (IOException e) {
//                            e.printStackTrace();
                        }
                    }
                }
            };
            Thread thr1 = new Thread(r1);
            thr1.start();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
            established = false;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + host);
            e.printStackTrace();
            established = false;
        }
    }
    public String getConnectionLine() {
        return connectionLine;
    }

    public boolean isEstablished() {
        return established && !clientSocket.isClosed();
    }

    public void closeConnection() {
        try {
            out.println(Common.Constants.Service.QUIT);
            clientSocket.close();
            established = false;
            if (clientSocket.isClosed()) {
                client.setText(Common.Constants.Service.DISCONNECTED);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initClientName() {
        InetAddress IP = null;
        try {
            IP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        out.println(IP);

        if (client.getClientName() == null) {
            clientName = IP.getHostName();
            client.setClientName(clientName);
        }
    }
}
