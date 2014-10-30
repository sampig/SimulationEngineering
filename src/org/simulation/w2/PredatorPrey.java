package org.simulation.w2;

import java.util.ArrayList;
import java.util.List;

public class PredatorPrey {

    //
    double start_time = 2014.0;
    double end_time = 2015.0;
    double delta_time = 1.0 / 365.0;

    //
    double init_rabits_num = 200;
    double init_lynxes_num = 10;

    //
    private State_PredatorPrey state = new State_PredatorPrey();
    private List<State_PredatorPrey> state_list = new ArrayList<State_PredatorPrey>(
            0);

    public PredatorPrey() {
        this.start_time = 2014.0;
        this.end_time = 2015.0;
        this.delta_time = 1.0 / 365.0;
    }

    public PredatorPrey(double start_year, double end_year) {
        this.start_time = start_year;
        this.end_time = end_year;
    }

    public PredatorPrey(double start_year, double end_year, double freq_time) {
        this.start_time = start_year;
        this.end_time = end_year;
        this.delta_time = freq_time;
    }

    public List<State_PredatorPrey> getState_list() {
        return state_list;
    }

    public void start() {
        //
        double t = start_time;
        double rn = init_rabits_num;
        double ln = init_lynxes_num;
        state = new State_PredatorPrey(t, rn, ln);
        state_list.add(state);
        for (; t <= end_time;) {
            double rn_temp = rn;
            rn += rn * (Prey.BIRTH_RATE - Predator.PREDATOR_RATE * ln)
                    * this.delta_time;
            ln += ln * (Prey.EFFECT * rn_temp - Predator.MORTALITY_RATE)
                    * this.delta_time;
            t += this.delta_time;
            state = new State_PredatorPrey(t, rn, ln);
            state_list.add(state);
        }
    }

    public void outputResult() {
        for (State_PredatorPrey s : state_list) {
            System.out.print("Time:" + s.getTime());
            System.out.print(", Rabbits: " + s.getRabits_num());
            System.out.println(", Lynxes: " + s.getLynxes_num() + ".");
        }
    }

    public class State_PredatorPrey {
        private double time;
        private double rabits_num;
        private double lynxes_num;

        public State_PredatorPrey() {
        }

        public State_PredatorPrey(double time, double rabits_num,
                double lynxes_num) {
            super();
            this.time = time;
            this.rabits_num = rabits_num;
            this.lynxes_num = lynxes_num;
        }

        public double getTime() {
            return time;
        }

        public void setTime(double time) {
            this.time = time;
        }

        public double getRabits_num() {
            return rabits_num;
        }

        public void setRabits_num(double rabits_num) {
            this.rabits_num = rabits_num;
        }

        public double getLynxes_num() {
            return lynxes_num;
        }

        public void setLynxes_num(double lynxes_num) {
            this.lynxes_num = lynxes_num;
        }
    }

}

class Predator {
    static double PREDATOR_RATE = 0.2;
    static double MORTALITY_RATE = 3.0;
}

class Prey {
    static double BIRTH_RATE = 2.0;
    static double EFFECT = 0.1;
}
