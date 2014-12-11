/**
 * MonteCarloSimulation.java
 * 
 * Author: Chenfeng Zhu
 * Date  : 2014-12-02
 */
package org.simulation.e05;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.simulation.e04.CircusTrapezeImpr;
import org.simulation.e04.MethodType;

public class MonteCarloSimulation {

    private int times = 1; // Simulation times.

    private List<Double> length_list = new ArrayList<Double>(0);
    private List<Double> theta_list = new ArrayList<Double>(0);
    private List<Double> velocity_list = new ArrayList<Double>(0);
    private List<Double> period_list = new ArrayList<Double>(0);
    // private List<AcrobatState> state_list = new ArrayList<>(0);

    // the parameters for the simulations
    private double start = 0; // start time
    private double end = 15; // end time
    private double step = 0.01; // step size
    private int status = CircusTrapezeImpr.STATUS_STATIC; // useless
    private MethodType method = MethodType.getInstanceRK2(); // method

    public MonteCarloSimulation() {
        super();
    }

    /**
     * Start the simulation.
     */
    public void start() {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        double l = 10; // length
        double r = 1; // the start angle
        double v = 0.3; // the start velocity
        // Do the simulation for a loop.
        for (int i = 0; i < times; i++) {
            // the random variables - normal random.
            l = 9.5 + 1.0 * rand.nextDouble(); // 9.5 - 10.5
            r = 55 + 10 * rand.nextDouble(); // 55 - 65
            r = Math.toRadians(r); // (0.959-1.134)
            v = -(0.25 + 0.1 * rand.nextDouble()); // -(0.25 - 0.35)
            CircusTrapezeImpr ct = new CircusTrapezeImpr(start, end, step, status, method);
            ct.setParameters(l, r, v);
            ct.start();
            // add the data into the list.
            length_list.add(l);
            theta_list.add(r);
            velocity_list.add(v);
            period_list.add(ct.getPeriod());
        }
    }

    /**
     * Set the times for which the simulation runs.
     * 
     * @param times
     */
    public void setSimulationTimes(int times) {
        this.times = times;
    }

    /**
     * Print the results of the simulation.
     */
    public void output() {
        for (int i = 0; i < times; i++) {
            String str = "";
            str += "Simulation-" + i + ": ";
            str += length_list.get(i) + ", ";
            str += Math.toDegrees(theta_list.get(i)) + ", ";
            str += velocity_list.get(i) + ", ";
            str += period_list.get(i);
            System.out.println(str);
        }
    }

    public List<Double> getLengthList() {
        return length_list;
    }

    public List<Double> getThetaList() {
        return theta_list;
    }

    public List<Double> getVelocityList() {
        return velocity_list;
    }

    public List<Double> getPeriodList() {
        return period_list;
    }

    public static void main(String... strings) {
        // This is a test.
        MonteCarloSimulation mcs = new MonteCarloSimulation();
        mcs.setSimulationTimes(10);
        mcs.start();
        mcs.output();
    }

}
