package com.github.alxwhtmr.muw.server;

import com.github.alxwhtmr.muw.common.Common;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 02.09.2014.
 */
public class Server {
    private final static int MAX_CONNECTIONS = 10;
    private static int clientCount = 0;
    private static boolean acceptMore = true;
    private ServerCommandProcessor serverCommandProcessor;
    private ArrayList<PrintWriter> out = new ArrayList<PrintWriter>();
    private LinkedList<PrintWriter> out2 = new LinkedList<PrintWriter>();

    public Server(int portNumber) {
        ServerSocket serverSocket = null;
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_CONNECTIONS);
        serverCommandProcessor = new ServerCommandProcessor();

        try {
            serverSocket = new ServerSocket(portNumber);
            while (acceptMore) {
                Socket incoming = serverSocket.accept();
                executorService.submit(new SocketCallable(incoming));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (Exception e) {
            }
            executorService.shutdownNow();
        }
    }

    public class SocketCallable implements Callable {
        private Socket incomingClientSocket;
        private String incomingClientHost;

        public SocketCallable(Socket incomingClientSocket) {
            this.incomingClientSocket = incomingClientSocket;
        }

        @Override
        public Object call() throws Exception {
            InputStreamReader isr = new InputStreamReader(incomingClientSocket.getInputStream(), "Windows-1251");
            BufferedReader in = new BufferedReader(isr);
            OutputStream outStream = incomingClientSocket.getOutputStream();
            PrintWriter pw = new PrintWriter(outStream, true); // ALWAYS SET TRUE!!
            out.add(pw);
            System.out.println("New Client index=" + out.indexOf(pw));
//            out.add(new PrintWriter(outStream, true));
            int incomingClientIndex = clientCount++;
            incomingClientHost = in.readLine(); // BufferedReader

            System.out.println("Clients: " + out.size() + " " + clientCount);
            System.out.println("Incoming connection " + incomingClientHost + " on " + new Date());

            for (int i = 0; i < clientCount; i++) out.get(i).println(incomingClientHost + " connected\n");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Incoming message: " + inputLine);
                if (inputLine.equals(Common.Constants.Service.QUIT)) break;
                for (int i = 0; i < clientCount; i++) {
                    String outputLine = serverCommandProcessor.processCommand(inputLine);
                    out.get(i).println(outputLine);
                }
            }
            closeConnection(incomingClientHost, incomingClientSocket, incomingClientIndex);
            return null;
        }
    }

    private void closeConnection(String incomingClientHost, Socket incoming, int clientIndex) {
        System.out.println("Connection with host " + incomingClientHost + " was closed on " + new Date());
        try {
            incoming.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientCount--;
        out.remove(clientIndex);
//        out2.remove(clientIndex);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java Server <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        System.out.println("Server started " + new Date());
        new Server(portNumber);
    }
}