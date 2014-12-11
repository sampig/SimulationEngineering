package org.simulation.e05;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CircusTrapezeMonteCarlo {

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

    public CircusTrapezeMonteCarlo() {
        start_time = 0.0;
        end_time = 10.0;
    }

    public CircusTrapezeMonteCarlo(double start_time, double end_time) {
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
                double th_temp = th;
                th = th + th_v * delta_time;
                th_v += (-g / l * Math.sin(th_temp)) * delta_time;
                t += delta_time;
                state = new AcrobatState(Math.round(t * 1000) / 1000.0, th, th_v);
                state_list.add(state);
            }
        } else if (run_mode == STATUS_DYNAMIC) {
            Random r = new Random();
            while (t <= end_time) {
                if (r.nextInt(10) > 7) {
                    if (l > LEIGHT) {
                        l -= h / 2;
                    } else if (l < LEIGHT) {
                        l += h / 2;
                    } else {
                        if (r.nextBoolean()) {
                            l -= h / 2;
                        } else {
                            l += h / 2;
                        }
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
