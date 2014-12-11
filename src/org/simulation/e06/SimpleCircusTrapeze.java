/**
 * SimpleCircusTrapeze.java
 * Implement the basic Circus Trapeze with EULER.
 * 
 * Author: Chenfeng Zhu
 * Date  : 2014-12-02
 */
package org.simulation.e06;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleCircusTrapeze {

    // public constants:
    public final static int STATUS_STATIC = 0;
    public final static int STATUS_DYNAMIC = 1;
    public final static double GRAVITY_ACCELARATION = 9.8;
    public final static double LEIGHT = 5.0;

    // time
    private double start_time = 0.0;
    private double end_time = 10.0;
    private double time_step_size = 0.01;

    // constants of the acrobat
    private double g = GRAVITY_ACCELARATION; // gravity
    double m = 1; // mass kilogram
    double h = 1.6; // height meter

    // initial
    private double length = 5.0; // length of the string (meter).
    private double init_theta = Math.toRadians(45); // the start angles.
    private double init_theta_v = 0; // the start velocity.

    private List<AcrobatState> state_list = new ArrayList<AcrobatState>(0);
    private AcrobatState state = new AcrobatState();

    private int run_mode = STATUS_STATIC;

    /**
     * Constructs an empty SimpleCircusTrapeze with default values.
     */
    public SimpleCircusTrapeze() {
        start_time = 0.0;
        end_time = 10.0;
    }

    /**
     * Constructs a SimpleCircusTrapeze with the specified initial time.
     * 
     * @param start_time
     * @param end_time
     */
    public SimpleCircusTrapeze(double start_time, double end_time) {
        super();
        this.start_time = start_time;
        this.end_time = end_time;
    }

    /**
     * Constructs a SimpleCircusTrapeze with the specified initial length,
     * angles and velocity.
     * 
     * @param length
     *            the length at start
     * @param theta
     *            the angles at start
     * @param theta_v
     *            the velocity at start
     */
    public SimpleCircusTrapeze(double length, double theta, double theta_v) {
        super();
        this.length = length;
        this.init_theta = Math.toRadians(theta);
        this.init_theta_v = theta_v;
    }

    /**
     * Start the simulation.
     */
    public void start() {

        //
        double t = start_time;
        double th = init_theta;
        double th_v = init_theta_v;
        state = new AcrobatState(t, th, th_v);
        state_list.add(state);

        if (run_mode == STATUS_STATIC) {
            while (t <= end_time) {
                try {
                    Thread.sleep((int) (time_step_size * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double th_temp = th;
                th = th + th_v * time_step_size;
                th_v += (-g / length * Math.sin(th_temp)) * time_step_size;
                t += time_step_size;
                state = new AcrobatState(Math.round(t * 1000) / 1000.0, th, th_v);
                System.out.println(Math.round(t * 1000) / 1000.0 + ": " + th);
                state_list.add(state);
            }
        } else if (run_mode == STATUS_DYNAMIC) {
            Random r = new Random();
            while (t <= end_time) {
                if (r.nextInt(10) > 7) {
                    if (length > LEIGHT) {
                        length -= h / 2;
                    } else if (length < LEIGHT) {
                        length += h / 2;
                    } else {
                        if (r.nextBoolean()) {
                            length -= h / 2;
                        } else {
                            length += h / 2;
                        }
                    }
                }
                double th_temp = th;
                th = th + th_v * time_step_size;
                th_v += (-g / length * Math.sin(th_temp)) * time_step_size;
                t += time_step_size;
                state = new AcrobatState(Math.round(t * 1000) / 1000.0, th, th_v);
                state_list.add(state);
            }
        }

    }

    /**
     * output the results.
     */
    public void outputResult() {
        for (int i = 0; i < state_list.size(); i++) {
            System.out.print("Time: " + state_list.get(i).getTime() + ", ");
            System.out.print("Theta: " + state_list.get(i).getAngle() + ","
                    + Math.toDegrees(state_list.get(i).getAngle()) + ", ");
            System.out.println("Velocity: " + state_list.get(i).getAngle_velocity() + ", ");
        }
        System.out.println("Period: " + this.getPeriod());
    }

    /**
     * Calculate the period.
     * 
     * @return the period
     */
    public double getPeriod() {
        double period = 0;
        double t1 = 0, t2 = 0;
        int i = 0;
        AcrobatState as1, as2;
        for (; i < state_list.size() - 1; i++) {
            as1 = state_list.get(i);
            as2 = state_list.get(i + 1);
            if (as2.getAngle() > as1.getAngle()) {
                t1 = as1.getTime();
                i++;
                break;
            }
        }
        for (; i < state_list.size() - 1; i++) {
            as1 = state_list.get(i);
            as2 = state_list.get(i + 1);
            if (as2.getAngle() < as1.getAngle()) {
                i++;
                break;
            }
        }
        for (; i < state_list.size() - 1; i++) {
            as1 = state_list.get(i);
            as2 = state_list.get(i + 1);
            if (as2.getAngle() > as1.getAngle()) {
                t2 = as1.getTime();
                break;
            }
        }
        period = t2 - t1;
        // System.out.println(t1 + " - " + t2);
        return Math.round(period * 1000) / 1000.0;
    }

    public List<AcrobatState> getState_list() {
        return state_list;
    }

    public void setStart(double start) {
        this.start_time = start;
    }

    public void setEnd(double end) {
        this.end_time = end;
    }

    public void setStepSize(double step_size) {
        this.time_step_size = step_size;
    }

    public void setRunMode(int run_mode) {
        this.run_mode = run_mode;
    }
}
