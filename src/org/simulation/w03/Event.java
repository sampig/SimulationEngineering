package org.simulation.w03;

public class Event {

    double time;

    public void execute(Simulator sim) {
        ;
    }

    public boolean lessThan(Event e) {
        return this.time < e.time;
    }

}
