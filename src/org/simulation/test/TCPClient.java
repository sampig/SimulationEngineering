package org.simulation.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient extends Thread {

    private String address = "127.0.0.1";
    private int port = 8802;

    Socket clientSocket = null;
    DataInputStream in = null;
    DataOutputStream out = null;

    public void run() {
        try {
            clientSocket = new Socket(address, port);
        } catch (UnknownHostException e) {
        } catch (IOException e) {
        }
    }
}
