package org.simulation.w04;

import java.util.ArrayList;
import java.util.List;

public class CircusTrapezeASS {

    public final static int STATUS_STATIC = 0;
    public final static int STATUS_DYNAMIC = 1;
    public final static double GRAVITY_ACCELARATION = 9.8;
    public final static double LEIGHT = 5.0;

    // time
    double start_time = 0.0;
    double end_time = 10.0;
    double delta_time = 0.01;

    // constants
    double g = GRAVITY_ACCELARATION; // gravity
    double m = 1; // mass kilogram
    double h = 1.6; // height meter

    // initial
    double l = 5.0; // length of the string (meter).
    double init_theta = Math.toRadians(45); //
    double init_theta_v = 0;

    private List<AcrobatState> state_list = new ArrayList<AcrobatState>(0);
    private AcrobatState state = new AcrobatState();

    //
    double e_threshold_ass = 0.00001;
    double min_num = 0.0001;

    public CircusTrapezeASS() {
        start_time = 0.0;
        end_time = 10.0;
    }

    public CircusTrapezeASS(double start_time, double end_time) {
        super();
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public List<AcrobatState> getState_list() {
        return state_list;
    }

    public void start(int run_mode) {

        //
        double t = start_time;
        double th = init_theta;
        double th_v = init_theta_v;
        state = new AcrobatState(t, th, th_v);
        state_list.add(state);

        if (run_mode == STATUS_STATIC) {
            while (t <= end_time) {
                for (int flag_direction = -1;;) {
                    // flag_direction: -1: halve; 1: double.
                    double[] th_temp = new double[2]; // angles
                    double[] th_v_temp = new double[2]; // velocity
                    double temp;
                    double error = 0;
                    if (flag_direction == -1) { // test half of step size
                        // first step:
                        th_temp[0] = th + th_v * delta_time;
                        th_v_temp[0] = th_v + (-g / l * Math.sin(th)) * delta_time;
                        // second step:
                        th_temp[1] = th + th_v * delta_time / 2;
                        th_v_temp[1] = th_v + (-g / l * Math.sin(th)) * delta_time / 2;
                        temp = th_temp[1];
                        th_temp[1] += th_v_temp[1] * delta_time / 2;
                        th_v_temp[1] += (-g / l * Math.sin(temp)) * delta_time / 2;
                    } else if (flag_direction == 1) {// test double of step size
                        // first step:
                        th_temp[0] = th + th_v * delta_time * 2;
                        th_v_temp[0] = th_v + (-g / l * Math.sin(th)) * delta_time * 2;
                        // second step:
                        th_temp[1] = th + th_v * delta_time;
                        th_v_temp[1] = th_v + (-g / l * Math.sin(th)) * delta_time;
                        temp = th_temp[1];
                        th_temp[1] += th_v_temp[1] * delta_time;
                        th_v_temp[1] += (-g / l * Math.sin(temp)) * delta_time;
                    }
                    error = Math.abs(th_temp[0] - th_temp[1]);
                    if (error > e_threshold_ass) {
                        // if error can not be accepted,
                        // halve the step size.
                        delta_time /= 2;
                        flag_direction = -1;
                    } else {
                        // if the error is accepted, then continue.
                        if (error / e_threshold_ass < min_num) {
                            // if the error is too little,
                            // then try use a larger step on next time.
                            delta_time *= 2;
                        }
                        flag_direction = -1;
                        break;
                    }
                }
                double th_temp = th;
                th = th + th_v * delta_time;
                th_v += (-g / l * Math.sin(th_temp)) * delta_time;
                t += delta_time;
                state = new AcrobatState(Math.round(t * 1000) / 1000.0, th, th_v);
                state_list.add(state);
            }
        }

    }

    public void outputResult() {
        for (int i = 0; i < state_list.size(); i++) {
            System.out.print("Time: " + state_list.get(i).getTime() + ", ");
            System.out.println("Theta: " + state_list.get(i).getAngle() + ","
                    + Math.toDegrees(state_list.get(i).getAngle()) + ".");
        }
    }

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
}
