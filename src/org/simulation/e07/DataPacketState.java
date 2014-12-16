package org.simulation.e07;

public class DataPacketState {

    private double time;
    private double angle;
    private double length;

    public DataPacketState(double time, double angle, double length) {
        super();
        this.time = time;
        this.angle = angle;
        this.length = length;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

}
