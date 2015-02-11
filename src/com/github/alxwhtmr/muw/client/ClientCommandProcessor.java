package com.github.alxwhtmr.muw.client;

import com.github.alxwhtmr.muw.client.gui.Client;
import com.github.alxwhtmr.muw.common.Common;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;


/**
 * Created on 12.09.2014.
 */
public class ClientCommandProcessor {
    private Client client;
    private Connection connection;
    private ClientCommandSender clientCommandSender;
    private String defaultChannel = "";

    public ClientCommandProcessor(Client client) {
        this.client = client;
        connection = this.client.getConnection();
        clientCommandSender = this.client.getClientCommandSender();
        defaultChannel = Common.convertTo1251(Common.Constants.Channels.GLOBAL);
    }

    public void processCommand(String cmd) {
        if (cmd.length() > 0 && cmd.charAt(0) == Common.Constants.Service.SERVICE_SYMBOL) {
            String serviceCmdDivided[] = splitCommand(cmd.substring(1));
            processServiceCommand(serviceCmdDivided);
        } else if (connection.isEstablished()) {
            cmd = defaultChannel + cmd;
            String cmdDivided[] = splitCommand(cmd);
            System.out.println(cmdDivided[0]);
            if (cmdDivided[0].matches(Common.convertTo1251(Common.Patterns.Channels.GLOBAL))) {
                int begin = cmd.indexOf(Common.Patterns.Symbols.WHITESPACE) + 1;
                CharSequence s = cmd.subSequence(begin, cmd.length());
                clientCommandSender.sendGlobalMsg(s.toString());
            }
        }
    }

    private void processServiceCommand(String[] cmd) {
        System.out.println(Arrays.toString(cmd));
        if (cmd.length == 1) {
            processServiceCommand(cmd[0]);
        } else if (cmd[0].matches(Common.Patterns.Service.CONNECT) && cmd.length == 3) {
            String host = cmd[1];
            int port = Integer.parseInt(cmd[2]);
            if (!connection.isEstablished()) {
                connection.establish(host, port);
            } else {
                client.setText(Common.Constants.Service.ALREADY_CONNECTED);
            }
        } else if (cmd[0].matches(Common.Patterns.Service.SET_NAME) && cmd.length == 2) {
            System.out.println("SET_NAME " + cmd[1]);
            client.setClientName(cmd[1]);
        }
    }

    private void processServiceCommand(String cmd) {

        if (cmd.matches(Common.Patterns.Service.CLEAR)) {
            client.clear();
        } else if(cmd.matches(Common.Patterns.Service.QUIT_PATTERN)) {
            if (connection.isEstablished()) {
                connection.closeConnection();
            }
        }
    }

    public String[] splitCommand(String cmd) {
        return cmd.split(Common.Patterns.Symbols.WHITESPACE);
    }
//    private void processServiceCommand(String cmd) {
//        cmd = cmd.substring(1);
//        String[] cmdSplitted = cmd.split(" ");
//        if ((isConnectionCommandValid(cmdSplitted)) == true) {
//            connection.establish(cmdSplitted);
//        } else if (cmdSplitted[0].equals("clear") && cmdSplitted.length == 1) {
//            client.clear();
//        } else {
//            System.err.println("Usage: #connect <host name> <port number>.");
//        }
//    }

//    private boolean isConnectionCommandValid(String[] cmd) {
//        if (cmd[0].equals("#connect") && cmd.length == 3) {
//            return true;
//        }
//        return false;
//    }
}
