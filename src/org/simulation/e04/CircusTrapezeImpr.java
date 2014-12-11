package org.simulation.e04;

import java.util.ArrayList;
import java.util.List;

public class CircusTrapezeImpr {

    // time
    double start_time = 0.0;
    double end_time = 10.0;
    double step_time = 0.01;

    // constants
    double g = GRAVITY_ACCELARATION; // gravity
    double m = 1; // mass kilogram
    double h = 1.6; // height meter
    double l = LEIGHT; // the distance from the center to the core (meter).

    StateList sl = new StateList();
    AcrobatStateImpr theta = new AcrobatStateImpr();
    AcrobatStateImpr velocity = new AcrobatStateImpr();

    private List<AcrobatStateImpr> state_list = new ArrayList<AcrobatStateImpr>(0);
    // private AcrobatStateImpr state = new AcrobatStateImpr();

    // other parametes for simulation
    int run_mode; // running mode
    MethodType method; // type of calculation method.
    double e_threshold_ass = 0; // flag for Adaptive Step Size. default: false.

    protected CircusTrapezeImpr() {
        // default values for all parameters.
        start_time = 0.0;
        end_time = 10.0;
        step_time = 0.01;
        theta.init(sl, (new MethodType(MethodType.EULER)));
        velocity.init(sl, (new MethodType(MethodType.EULER)));
        this.run_mode = STATUS_STATIC;
        this.method = new MethodType(MethodType.EULER);
        theta.setValues(Math.toRadians(45));
        velocity.setValues(0);
    }

    public CircusTrapezeImpr(double start_time, double end_time, double step_time,
            int run_mode, MethodType method) {
        super();
        this.start_time = start_time;
        this.end_time = end_time;
        this.step_time = step_time;
        this.run_mode = run_mode;
        this.method = method;
        this.theta.init(this.sl, method);
        this.velocity.init(this.sl, method);
        theta.setValues(Math.toRadians(45));
        velocity.setValues(0);
    }

    /**
     * Start running the simulation.
     */
    public void start() {
        sl.init(step_time);
        theta.setDer(velocity.getValues());
        theta.setTime(sl.getTime());
        velocity.setDer((-g / l * Math.sin(theta.getValues())));
        for (;;) {
            if (sl.getTime() > this.end_time) {
                break;
            }
            sl.integrate();
            sl.setTime();
            theta.setDer(velocity.getValues());
            theta.setTime(sl.getTime());
            velocity.setDer((-g / l * Math.sin(theta.getValues())));
            this.saveData(theta);
        }
    }

    /**
     * Save the the simulation data according to different types of methods.
     * 
     * @param state
     */
    public void saveData(AcrobatStateImpr state) {
        switch (state.getMethod().getMethod()) {
        case MethodType.EULER:
            state_list.add(state.clone());
            break;
        case MethodType.RK2:
            if ((sl.getFrameCount() & 1) == 0) {
                state_list.add(state.clone());
            }
            break;
        case MethodType.RK4:
            if ((sl.getFrameCount() & 3) == 0) {
                state_list.add(state.clone());
            }
            break;
        }
    }

    /**
     * output the results.
     */
    public void outputResult() {
        for (int i = 0; i < state_list.size(); i++) {
            System.out.print("Time: " + state_list.get(i).getTime() + ", ");
            System.out.print("Theta: " + state_list.get(i).getValues() + ", ");
            System.out.println("Der: " + state_list.get(i).getDer() + ".");
        }
        System.out.println(state_list.size());
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
        AcrobatStateImpr as1, as2;
        for (; i < state_list.size() - 1; i++) {
            as1 = state_list.get(i);
            as2 = state_list.get(i + 1);
            if (as2.getValues() > as1.getValues()) {
                t1 = as1.getTime();
                i++;
                break;
            }
        }
        for (; i < state_list.size() - 1; i++) {
            as1 = state_list.get(i);
            as2 = state_list.get(i + 1);
            if (as2.getValues() < as1.getValues()) {
                i++;
                break;
            }
        }
        for (; i < state_list.size() - 1; i++) {
            as1 = state_list.get(i);
            as2 = state_list.get(i + 1);
            if (as2.getValues() > as1.getValues()) {
                t2 = as1.getTime();
                break;
            }
        }
        period = t2 - t1;
        return Math.round(period * 1000) / 1000.0;
    }

    public void setParameters(double l, double angle, double velocity) {
        this.l = l;
        this.theta.setValues(angle);
        this.velocity.setValues(velocity);
    }

    public StateList getStateList() {
        return sl;
    }

    public List<AcrobatStateImpr> getList() {
        return state_list;
    }

    /**
     * Set the error threshold. If the threshold <= 0, the step size won't be
     * adaptive.
     * 
     * @param e_threshold
     */
    public void setErrorThreshold(double e_threshold) {
        this.e_threshold_ass = e_threshold;
    }

    // constants.
    /**
     * status of static: the acrobat won't change the position.
     */
    public final static int STATUS_STATIC = 0;
    /**
     * status of static: the acrobat will change the position.
     */
    public final static int STATUS_DYNAMIC = 1;
    /**
     * 
     */
    public final static double GRAVITY_ACCELARATION = 9.8;
    /**
     * The length of the rope.
     */
    public final static double LEIGHT = 1.0;

}
