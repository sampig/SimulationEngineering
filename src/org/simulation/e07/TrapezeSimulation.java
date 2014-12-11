/**
 * TrapezeSimulation.java
 * The trapeze keeps moving and Waiting for the artist position.
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

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.simulation.util.Constant;

public class TrapezeSimulation extends Application implements EventHandler<ActionEvent>,
        Runnable {

    // constants of the rope
    private double g = Constant.GRAVITY_ACCELERATION; // gravity ()
    private double length = 5;// length of the rope (meter).
    private double l = length;

    // initial
    private double init_theta = Math.toRadians(45); // the start angles.
    private double init_theta_v = 0; // the start velocity.

    // time
    private double start_time = 0.0;
    private double end_time = 10.0;
    private double time_step_size = 0.01;
    double t = start_time;
    double th = init_theta;
    double th_v = init_theta_v;

    // dashboard
    private TextField tfPort;
    private TextField tfLength, tfStart, tfEnd, tfStep;
    private Button bStart;
    TextArea taLog;
    Canvas canvas;

    // connection
    // UDP Server
    private DatagramSocket socket;
    private TrapezeThread thread;
    // address
    // private String address = "127.0.0.1";
    private InetAddress IPAddress = null;
    // port
    private int port = 8801;

    // sent data.
    private DataPacketAngle data = new DataPacketAngle();

    @Override
    public void start(Stage stage) throws Exception {
        // Create a UI interface.
        stage.setTitle("Trapeze Simulation");

        tfPort = new TextField(); // port text field
        tfLength = new TextField();
        tfStart = new TextField();
        tfEnd = new TextField();
        tfStep = new TextField();
        bStart = new Button("Start"); // start button
        bStart.setOnAction(this);
        taLog = new TextArea(); // log text
        // taLog.setEditable(false);

        canvas = new Canvas(300, 300);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        this.pendulum(gc);

        // buttons:
        HBox hbox = new HBox();
        hbox.getChildren().add(bStart);
        // input:
        HBox parabox = new HBox();
        parabox.getChildren().add(tfLength);
        parabox.getChildren().add(tfStart);
        parabox.getChildren().add(tfEnd);
        parabox.getChildren().add(tfStep);

        VBox vbox = new VBox();
        vbox.getChildren().add(parabox);
        vbox.getChildren().add(tfPort);
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(taLog);
        vbox.getChildren().add(canvas);

        Group group = new Group();
        group.getChildren().add(vbox);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Draw the line.
     * 
     * @param gc
     */
    public void pendulum(GraphicsContext gc) {
        gc.setFill(Color.ALICEBLUE);
        gc.fillRect(10, 10, 290, 290);
        gc.strokeLine(150, 50, 50, 150);

    }

    /**
     * Draw the line.
     * 
     * @param gc
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void pendulum(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        gc.clearRect(10, 10, 290, 290);
        gc.setFill(Color.ALICEBLUE);
        gc.fillRect(10, 10, 290, 290);
        gc.strokeLine(x1, y1, x2, y2);
    }

    /**
     * Set the parameters.
     */
    protected void setParameters() {
        // set the length
        try {
            String length = tfLength.getText();
            l = (length == null) ? l : Double.parseDouble(length);
        } catch (Exception e) {
            Platform.runLater(new Runnable() {
                public void run() {
                    taLog.appendText("Length must be numerical.\n");
                }
            });
        }
        // set the start time
        try {
            String start = tfStart.getText();
            start_time = (start == null) ? start_time : Double.parseDouble(start);
        } catch (Exception e) {
            Platform.runLater(new Runnable() {
                public void run() {
                    taLog.appendText("Start time must be numerical.\n");
                }
            });
        }
        // set the end time
        try {
            String end = tfEnd.getText();
            end_time = (end == null) ? end_time : Double.parseDouble(end);
        } catch (Exception e) {
            Platform.runLater(new Runnable() {
                public void run() {
                    taLog.appendText("End time must be numerical.\n");
                }
            });
        }
        // set the step size
        try {
            String step = tfStep.getText();
            time_step_size = (step == null) ? time_step_size : Double.parseDouble(step);
        } catch (Exception e) {
            Platform.runLater(new Runnable() {
                public void run() {
                    taLog.appendText("Step size must be numerical.\n");
                }
            });
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

    @Override
    public void run() {
        // set the parameters.
        this.setParameters();
        // set the UDP socket.
        try {
            socket = new DatagramSocket(port);
        } catch (IOException e) {
            Platform.runLater(new Runnable() {
                public void run() {
                    taLog.appendText("Port has been used.\n");
                }
            });
            return;
        }
        // start a thread to accept the change of the artist's position.
        thread = new TrapezeThread(this, socket);
        thread.start();

        // waiting for the artist
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = null;
        ObjectOutputStream os = null;
        DatagramPacket sendPacket = null;
        byte[] sendData;
        for (int i = 0; t <= end_time; i++) {
            // delay
            try {
                Thread.sleep((int) (time_step_size * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // calculate the state
            double th_temp = th;
            th = th + th_v * time_step_size;
            th_v += (-g / l * Math.sin(th_temp)) * time_step_size;
            t += time_step_size;
            // print the state every 0.2 second.
            if (i % ((int) (0.2 / time_step_size)) == 0) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        String str = printState(Math.round(t * 1000) / 1000.0, th, th_v, l);
                        System.out.println(str);
                        taLog.appendText(str + "\n");
                    }
                });
            }
            if (IPAddress == null) {
                continue;
            }
            // send the angle to the Artist.
            data = new DataPacketAngle(th);
            try {
                out = new ByteArrayOutputStream();
                os = new ObjectOutputStream(out);
                os.writeObject(data);
                sendData = out.toByteArray();
                sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                socket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle(ActionEvent e) {
        if (e.getSource() == this.bStart) {
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    public String printState(double time, double angle, double velocity, double l) {
        String str = "";
        str += "Time(" + time + "): ";
        str += "angle(" + angle + "), ";
        str += "velocity(" + velocity + "), ";
        str += "length(" + l + ")";
        return str;
    }

    public void stop() {
        if (thread != null) {
            thread.interrupt();
        }
        if (Thread.currentThread() != null) {
            Thread.currentThread().interrupt();
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

    /**
     * Run a thread to accept the data from Artist.
     * 
     * @author zhuzhu
     *
     */
    public class TrapezeThread extends Thread {
        private TrapezeSimulation simulation;
        private DatagramSocket socket;
        // received data.
        private DataPacketPosition data;

        public TrapezeThread(TrapezeSimulation simulation, DatagramSocket socket) {
            this.simulation = simulation;
            this.socket = socket;
        }

        public void run() {
            Platform.runLater(new Runnable() {
                public void run() {
                    simulation.taLog.appendText("Starting server...\n");
                }
            });
            try {
                byte[] receiveData = new byte[1024];
                for (; socket != null;) {
                    // receive the data by UDP.
                    DatagramPacket receivePacket = null;
                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);
                    ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
                    ObjectInputStream is = new ObjectInputStream(in);
                    Object obj = is.readObject();
                    if (!(obj instanceof DataPacketPosition)) {
                        // if the data is not position, ignore it.
                        continue;
                    }
                    data = (DataPacketPosition) obj;
                    // set the IP address and port.
                    InetAddress address = receivePacket.getAddress();
                    simulation.IPAddress = address;
                    simulation.port = receivePacket.getPort();
                    Platform.runLater(new Runnable() {
                        public void run() {
                            simulation.taLog.appendText("From " + address + ": "
                                    + data.getValues() + ".\n");
                        }
                    });
                    // change the length of the mass in the simulation.
                    simulation.l = simulation.length + data.getValues();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
