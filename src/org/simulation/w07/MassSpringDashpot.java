/**
 * MassSpringDashpot.java
 * 
 * Author: zhuzhu
 * Date  : 2014-10-29
 */
package org.simulation.w07;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public strictfp class MassSpringDashpot {

    //
    double start_time = 0.0; // second
    double end_time = 200.0; // second
    double delta_time = 0.01; // second

    //
    double m = MassStateDataPacket.MASS; // kilogram

    //
    double init_position = 1.0;
    double init_velocity = 0.0;

    //
    private MassStateDataPacket state = new MassStateDataPacket();
    private List<MassStateDataPacket> stateList = new ArrayList<>(0);
    private MassStateDataPacket receiveState = new MassStateDataPacket();

    //
    DatagramSocket clientSocket = null;
    String address = "127.0.0.1";
    int port = 8801;
    InetAddress IPAddress = null;

    public MassSpringDashpot() {
    }

    /**
     * Set the connection.
     */
    private boolean setConnection() {
        try {
            clientSocket = new DatagramSocket();
            IPAddress = InetAddress.getByName(address);
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        } catch (UnknownHostException e) {
            if (clientSocket != null) {
                clientSocket.close();
            }
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void start() {
        // start to connect to the server.
        if (!this.setConnection()) {
            System.out.println("Failed to connect.");
            return;
        }
        double t = start_time;
        double x = init_position;
        double v = init_velocity;
        state = new MassStateDataPacket(t, x, v);
        stateList.add(state);
        System.out.println(state.toString());

        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        ObjectOutputStream os = null;
        ObjectInputStream is = null;
        DatagramPacket sendPacket = null, receivePacket = null;
        byte[] sendData;
        byte[] receiveData;

        for (int i = 0; t <= end_time; i++) {
            // send the state to the server.
            try {
                out = new ByteArrayOutputStream();
                os = new ObjectOutputStream(out);
                os.writeObject(state);
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }
            sendData = out.toByteArray();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            try {
                clientSocket.send(sendPacket);
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }

            // receive the data from the server.
            receiveData = new byte[(sendData.length > 1024) ? sendData.length : 1024];
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                clientSocket.receive(receivePacket);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            in = new ByteArrayInputStream(receiveData);
            try {
                is = new ObjectInputStream(in);
                receiveState = (MassStateDataPacket) is.readObject();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            x += v * delta_time;
            v += receiveState.getForce() / this.m * delta_time;
            t += delta_time;
            state = new MassStateDataPacket(Math.round(t * 100.0) / 100.0, x, v);
            stateList.add(state);
            if (i % ((int) (1.0 / delta_time)) == 0) {
                System.out.println(state.toString());
            }
            try {
                Thread.sleep((int) (delta_time * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void output() {
        for (MassStateDataPacket s : stateList) {
            System.out.print("Time: " + s.getTime());
            System.out.println(", Position: " + s.getPosition());
        }
    }

    public List<MassStateDataPacket> getStateList() {
        return stateList;
    }

    public static void main(String... strings) {
        MassSpringDashpot msd = new MassSpringDashpot();
        msd.start();
    }

}
