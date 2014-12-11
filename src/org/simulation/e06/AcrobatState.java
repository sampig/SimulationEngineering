/**
 * AcrobatState.java
 * 
 * Author: Chenfeng Zhu
 * Date  : 2014-12-02
 */
package org.simulation.e06;

public class AcrobatState {
    double time;
    double angle; // radians
    double angle_velocity;
    double length;

    public AcrobatState() {
    }

    public AcrobatState(double time, double angle, double angle_velocity) {
        super();
        this.time = time;
        this.angle = angle;
        this.angle_velocity = angle_velocity;
    }

    public AcrobatState(double time, double angle, double angle_velocity, double length) {
        super();
        this.time = time;
        this.angle = angle;
        this.angle_velocity = angle_velocity;
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

    public double getAngle_velocity() {
        return angle_velocity;
    }

    public void setAngle_velocity(double angle_velocity) {
        this.angle_velocity = angle_velocity;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

}
