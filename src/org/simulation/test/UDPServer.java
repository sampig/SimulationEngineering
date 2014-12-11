package org.simulation.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {

    public static void main(String... strings) {
        DatagramSocket serverSocket = null;
        try {
            int p = 8801;
            serverSocket = new DatagramSocket(p);
            System.out.println("Start at " + p);
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            while (serverSocket != null) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData,
                        receiveData.length);
                serverSocket.receive(receivePacket);
                byte[] bytes = receivePacket.getData();
                String sentence = new String(bytes);
                System.out.println("RECEIVED: " + sentence);
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                String capitalizedSentence = sentence.toUpperCase();
                sendData = capitalizedSentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        IPAddress, port);
                serverSocket.send(sendPacket);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

}
