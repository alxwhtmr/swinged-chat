package com.github.alxwhtmr.muw.client;

import com.github.alxwhtmr.muw.client.gui.Client;

/**
 * Created on 17.09.2014.
 */
public class ClientCommandSender {
    private Client client;

    public ClientCommandSender(Client client) {
        this.client = client;
    }

    public void sendCommand(String msg) {
        client.getConnection().out.println(client.getClientName() + ": \"" + msg + "\"");
    }

    public void sendGlobalMsg(String msg) {
        client.getConnection().out.println(client.getClientName() + ": " +  msg);
    }
}
