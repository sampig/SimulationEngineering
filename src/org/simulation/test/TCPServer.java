package org.simulation.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class TCPServer extends Thread {

    public static void main(String... strings) {
        TCPServer server = new TCPServer();
        server.start();
    }

    public void run() {
        ServerSocket server = null;
        Socket client = null;
        Hashtable<String, Socket> clients = new Hashtable<>(0);
        int port = 8802;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (server != null) {
            try {
                client = server.accept();
                ServerThread thread = new ServerThread(client, clients);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(port==8801) {
                break;
            }
        }
        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ServerThread extends Thread {
        Socket clientSocket;
        Hashtable<String, Socket> table;
        DataOutputStream os = null;
        DataInputStream is = null;

        public ServerThread(Socket socket, Hashtable<String, Socket> table) {
            this.clientSocket = socket;
            this.table = table;
        }

        public void run() {
            try
            {
                is = new DataInputStream(clientSocket.getInputStream());
                os = new DataOutputStream(clientSocket.getOutputStream());
            } catch(IOException e3) {}
        }
    }
}
