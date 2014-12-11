package org.simulation.test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.simulation.w07.MassStateDataPacket;

public class UDPClient {
    public static void main(String args[]) throws Exception {
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//        String sentence = inFromUser.readLine();
        MassStateDataPacket data = new MassStateDataPacket(4,1,10);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(data);
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("127.0.0.1");
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        sendData = out.toByteArray();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,
                8801);
        clientSocket.send(sendPacket);
        System.out.println("Send: " + sendData);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String modifiedSentence = new String(receivePacket.getData());
        System.out.println("FROM SERVER:" + modifiedSentence);
        clientSocket.close();
    }
}
