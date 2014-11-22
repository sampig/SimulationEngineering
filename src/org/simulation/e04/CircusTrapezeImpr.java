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
    }

    /**
     * Start running the simulation.
     */
    public void start() {
        theta.setValues(Math.toRadians(45));
        velocity.setValues(0);
        sl.init(step_time);
        for (;;) {
            theta.setDer(velocity.getValues());
            theta.setTime(sl.getTime());
            // System.out.println(theta.getTime() + ": " + theta.getValues());
            velocity.setDer((-g / l * Math.sin(theta.getValues())));
            //
            this.adaptStepSize(sl);
            // Save the data.
            if (this.e_threshold_ass <= 0 || flag) {
                this.saveData(theta);
                flag = false;
            }
            if (sl.getTime() > this.end_time) {
                break;
            }
            sl.integrate();
        }
    }

    /**
     * Save the the simualtion data according to different types of methods.
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

    AcrobatStateImpr[] temp_theta = new AcrobatStateImpr[3];
    AcrobatStateImpr[] temp_velocity = new AcrobatStateImpr[3];
    int temp_framecount[] = { 0, 0, 0 };
    int flag_ass_direction = -1; // -1: halve; 1: double; 0: null.
    boolean flag = true;

    /**
     * Adapt the step size. If there is no threshold, skip this.
     * 
     * @param list
     */
    public void adaptStepSize(StateList list) {
        if (this.e_threshold_ass <= 0) {
            return;
        }
        switch (list.getState().getMethod().getMethod()) {
        case MethodType.EULER:
            testStepSize(list);
            break;
        case MethodType.RK2:
            if ((sl.getFrameCount() & 1) == 0) {
                testStepSize(list);
            }
            break;
        case MethodType.RK4:
            if ((sl.getFrameCount() & 3) == 0) {
                testStepSize(list);
            }
            break;
        }
    }

    public void testStepSize(StateList list) {
        switch (list.stepCount) {
        case 0: // save the current state.
            temp_theta[0] = list.getState().getNext().clone();
            temp_velocity[0] = list.getState().clone();
            temp_framecount[0] = list.getFrameCount();
            break;
        case 1: // save the state after one steps for halve
            if (flag_ass_direction == -1) {
                temp_theta[1] = list.getState().getNext().clone();
                temp_velocity[1] = list.getState().clone();
                temp_framecount[1] = list.getFrameCount();
                // test for a shorter step and return the test point.
                list.halveStepTime();
                list.setFrameCount(temp_framecount[0]);
                list.setState(temp_velocity[0]);
            }
            break;
        case 2: // save the state after two steps for double
            if (flag_ass_direction == 1) {
                temp_theta[1] = list.getState().getNext().clone();
                temp_velocity[1] = list.getState().clone();
                temp_framecount[1] = list.getFrameCount();
                // test for a longer step and return the test point.
                list.doubleStepTime();
                list.setFrameCount(temp_framecount[0]);
                list.setState(temp_velocity[0]);
            }
            break;
        case 3: // save the state after increase or decrease the step size.
            temp_theta[2] = list.getState().getNext().clone();
            temp_velocity[2] = list.getState().clone();
            temp_framecount[2] = list.getFrameCount();
            double error = Math.abs(temp_theta[2].getValues() - temp_theta[1].getValues());
            if (error > e_threshold_ass) {
                // error can not be accepted.
                list.setFrameCount(temp_framecount[0]);
                list.setState(temp_velocity[0]);
            } else {
                // if the error is accepted, then continue.
                if (error / e_threshold_ass < 0.001) {
                    // if the error is too little,
                    // then try use a larger step on next time.
                    list.doubleStepTime();
                }
                flag = true;
                // the step size return to the original. don't mess it.
                if (flag_ass_direction == -1) {
                    list.doubleStepTime();
                }
                if (flag_ass_direction == 1) {
                    list.halveStepTime();
                }
                list.setState(temp_velocity[1]);
                list.setFrameCount(temp_framecount[1]);
            }
            list.stepCount = 0;
            return;
        }
        list.stepCount++;
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
    public final static double LEIGHT = 5.0;

}
