package org.simulation.w07;

import java.io.Serializable;

public class MassStateDataPacket implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3717813239031961504L;
    
    public final static double MASS = 1;

    private double time;
    private double position;
    private double velocity;
    private double force;

    public MassStateDataPacket() {
    }

    public MassStateDataPacket(double position, double velocity) {
        super();
        this.position = position;
        this.velocity = velocity;
    }

    public MassStateDataPacket(double time, double position, double velocity) {
        super();
        this.time = time;
        this.position = position;
        this.velocity = velocity;
    }

    public MassStateDataPacket(double force) {
        super();
        this.force = force;
    }

    public String toString() {
        String str = ": time(" + this.time + ") position(" + this.position + "), velocity("
                + this.velocity + ").";
        return super.toString() + str;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getForce() {
        return force;
    }

    public void setForce(double force) {
        this.force = force;
    }
}
