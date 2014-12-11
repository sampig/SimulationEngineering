/**
 * MassSpringDashpotServer.java
 * 
 * Author: zhuzhu
 * Date  : 2014-12-07
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
import java.util.Date;

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

/**
 * @author zhuzhu
 *
 */
public class MassSpringDashpotServer extends Application implements EventHandler<ActionEvent>,
        Runnable {
    // constants:
    private double k = 1; // spring constant
    private double b = 1; // damping coefficient

    // UDP Server
    private DatagramSocket server;
    // the thread for the server.
    ServerThread serverThread;
    // port
    private int port = 8801;

    // dashboard
    private TextField tfPort;
    private Button bStart;
    TextArea taLog;

    //
    MassStateDataPacket receiveState = new MassStateDataPacket();
    MassStateDataPacket sendState = new MassStateDataPacket();

    public MassSpringDashpotServer() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Create a UI interface.
        stage.setTitle("Mass Spring Dashpot Server");

        tfPort = new TextField(); // port text field
        bStart = new Button("Start"); // start button
        bStart.setOnAction(this);
        taLog = new TextArea(); // log text
        // taLog.setEditable(false);

        VBox vbox = new VBox();
        HBox hbox = new HBox();
        hbox.getChildren().add(bStart);
        vbox.getChildren().add(tfPort);
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(taLog);

        Group group = new Group();
        group.getChildren().add(vbox);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
    }

    public boolean start() {
        try {
            Integer p = new Integer(tfPort.getText());
            port = (p == null) ? port : p;
        } catch (Exception e) {
            taLog.appendText("Port must be numerical.\n");
            return false;
        }
        try {
            server = new DatagramSocket(port);
        } catch (IOException e) {
            taLog.appendText("Port has been used.\n");
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        serverThread = new ServerThread(this, server);
        serverThread.start();
    }

    @Override
    public void handle(ActionEvent e) {
        if (e.getSource() == this.bStart) {
            if (!this.start()) {
                return;
            }
            Thread thread = new Thread(this);
            thread.run();
            taLog.appendText("Start server at port " + port + ".\n");
        }
    }

    public void stop() {
        try {
            if (serverThread != null) {
                serverThread.interrupt();
            }
            Thread thread = new Thread(this);
            thread.isInterrupted();
            Thread.currentThread().interrupt();
            super.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String... strings) {
        launch(strings);
    }

    protected class ServerThread extends Thread {

        DatagramSocket server;
        MassSpringDashpotServer msd;

        public ServerThread(MassSpringDashpotServer msd, DatagramSocket server) {
            this.server = server;
            this.msd = msd;
        }

        public void run() {
            try {
                byte[] receiveData = new byte[1024];
                byte[] sendData = new byte[1024];
                for (; server != null;) {
                    DatagramPacket receivePacket = null;
                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    server.receive(receivePacket);
                    // byte[] dataBytes = receivePacket.getData();
                    ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
                    // String receive = new String(dataBytes);
                    ObjectInputStream is = new ObjectInputStream(in);
                    receiveState = (MassStateDataPacket) is.readObject();
                    InetAddress address = receivePacket.getAddress();
                    if ((new Date()).getTime() % 1000 == 0) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                msd.taLog.appendText("From " + address + ": "
                                        + receiveState.toString() + ".\n");
                            }
                        });
                    }
                    double force = -k * receiveState.getPosition() - b
                            * receiveState.getVelocity();
                    receiveState.setForce(force);
                    int port = receivePacket.getPort();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ObjectOutputStream os = new ObjectOutputStream(out);
                    os.writeObject(receiveState);
                    sendData = out.toByteArray();
                    DatagramPacket sendPacket = null;
                    sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
                    server.send(sendPacket);
                    if ((new Date()).getTime() % 1000 == 0) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                msd.taLog.appendText("Sent to " + address + ": "
                                        + receiveState.getForce() + ".\n");
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
