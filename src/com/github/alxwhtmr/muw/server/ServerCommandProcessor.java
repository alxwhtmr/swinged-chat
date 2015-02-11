package com.github.alxwhtmr.muw.server;

/**
 * Created on 17.09.2014.
 */
public class ServerCommandProcessor {
    public String processCommand(String command) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < command.length(); i++) {
            if (i != 0 && i % 100 == 0) {
                while (command.charAt(i) != ' ') {
                    sb.append(command.charAt(i++));
                }
                sb.append(" \n");
            }
            sb.append(command.charAt(i));
        }
        return sb.toString();
    }
}
