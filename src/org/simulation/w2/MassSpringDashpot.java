/**
 * MassSpringDashpot.java
 * 
 * Author: zhuzhu
 * Date  : 2014-10-29
 */
package org.simulation.w2;

import java.util.ArrayList;
import java.util.List;

public strictfp class MassSpringDashpot {

    //
    double start_time = 0.0; // second
    double end_time = 20.0; // second
    double delta_time = 0.1; // second

    //
    double m = 1; // kilogram
    private double k = 1; // spring constant
    private double b = 1; // damping coefficient

    //
    double init_position = 1.0;
    double init_velocity = 0.0;

    //
    private MassState state = new MassState();
    private List<MassState> stateList = new ArrayList<>(0);

    public MassSpringDashpot() {
    }

    public List<MassState> getStateList() {
        return stateList;
    }

    public void start() {
        double t = start_time;
        double x = init_position;
        double v = init_velocity;
        state = new MassState(t, x);
        stateList.add(state);
        for (; t <= end_time;) {
            double x_temp = x;
            x += v * delta_time;
            v -= (k * x_temp + b * v) * delta_time;
            t += delta_time;
            state = new MassState(Math.round(t*100.0)/100.0, x);
            stateList.add(state);
        }
    }

    public void output() {
        for (MassState s : stateList) {
            System.out.print("Time: " + s.getTime());
            System.out.println(", Position: " + s.getPosition());
        }
    }

    public class MassState {
        private double time;
        private double position;
        //private double velocity;

        public MassState() {
        }

        public MassState(double time, double position) {
            super();
            this.time = time;
            this.position = position;
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
    }
}
