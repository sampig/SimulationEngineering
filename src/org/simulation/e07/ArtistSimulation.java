/**
 * ArtistSimulation.java
 * The artist changes position depending on the angle.
 * 
 * Author: Chenfeng Zhu
 * Date  : 2014-12-09
 */
package org.simulation.e07;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ArtistSimulation extends Application implements EventHandler<ActionEvent>,
        Runnable {

    // constants of the artist
    private double h = 1.6; // the height of the artist (meter).
    private double d = h / 2; // the mass point of the artist (meter).

    // dashboard
    private TextField tfPort;
    private Button bStart;
    TextArea taLog;

    // Connection
    // UDP Socket
    private DatagramSocket socket;
    // address
    private String address = "127.0.0.1";
    private InetAddress IPAddress = null;
    // port
    private int port = 8801;

    // sent data.
    private DatagramPacket sendPacket = null;
    private DatagramPacket receivePacket = null;
    private DataPacketAngle angleData = null;
    private DataPacketPosition positionData = null;

    private int flag = 0;

    @Override
    public void run() {
        // set the parameters.
        this.setParameters();
        // set the UDP socket.
        try {
            socket = new DatagramSocket();
        } catch (IOException e) {
            return;
        }
        byte[] sendData = null;
        byte[] receiveData = new byte[1024];
        ByteArrayOutputStream out = null;
        ObjectOutputStream os = null;
        try {
            // for the first connection
            out = new ByteArrayOutputStream();
            os = new ObjectOutputStream(out);
            positionData = new DataPacketPosition(0);
            os.writeObject(positionData);
            sendData = out.toByteArray();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            socket.send(sendPacket);
            System.out.println("Sent to " + address + ": " + d + ".\n");
            for (; socket != null;) {
                // receive angle from the Trapeze.
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
                ObjectInputStream is = new ObjectInputStream(in);
                Object obj = is.readObject();
                if (!(obj instanceof DataPacketAngle)) {
                    continue;
                }
                angleData = (DataPacketAngle) obj;
                IPAddress = receivePacket.getAddress();
                double angle = angleData.getValues();
                Platform.runLater(new Runnable() {
                    public void run() {
                        taLog.appendText("From " + IPAddress + ": " + angle + ".\n");
                    }
                });
                // changing the position.
                if (System.currentTimeMillis() % 1000 == 0 && flag != 0) {
                    // sometimes artist would stands.
                    d = -h / 2;
                    flag = 0;
                } else {
                    if (angle >= Math.toRadians(40) && flag != 1) {
                        // at the highest point:
                        d = 0;
                        flag = 1;
                    } else if (angle <= -Math.toRadians(40) && flag != 2) {
                        // at the other point:
                        d = 0;
                        flag = 2;
                    } else if (Math.abs(angle) <= Math.toRadians(5) && flag != 3) {
                        // at the lowest point: artist would hang
                        d = h / 2;
                        flag = 3;
                    } else {
                        // if position isn't changed, continue.
                        continue;
                    }
                }
                positionData = new DataPacketPosition(d);
                // send the position to the Trapeze.
                port = receivePacket.getPort();
                out = new ByteArrayOutputStream();
                os = new ObjectOutputStream(out);
                os.writeObject(positionData);
                sendData = out.toByteArray();
                sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                socket.send(sendPacket);
                Platform.runLater(new Runnable() {
                    public void run() {
                        taLog.appendText("Sent to " + address + ": " + d + ".\n");
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(ActionEvent e) {
        if (e.getSource() == this.bStart) {
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Create a UI interface.
        stage.setTitle("Artist Simulation");

        tfPort = new TextField(); // port text field
        bStart = new Button("Start"); // start button
        bStart.setOnAction(this);
        taLog = new TextArea(); // log text

        // buttons:
        HBox hbox = new HBox();
        hbox.getChildren().add(bStart);

        VBox vbox = new VBox();
        vbox.getChildren().add(tfPort);
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(taLog);

        Group group = new Group();
        group.getChildren().add(vbox);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Set the parameters.
     */
    protected void setParameters() {
        // set the address
        try {
            IPAddress = InetAddress.getByName(address);
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        // set the port
        try {
            String p = tfPort.getText();
            port = (p == null) ? port : Integer.parseInt(p);
        } catch (Exception e) {
            Platform.runLater(new Runnable() {
                public void run() {
                    taLog.appendText("Port must be numerical.\n");
                }
            });
        }
    }

    public void stop() {
        if (socket != null) {
            socket.close();
        }
        try {
            super.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String... strings) {
        launch(strings);
    }

}
