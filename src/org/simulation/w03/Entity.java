package org.simulation.w03;

public class Entity {

    double time_generated;
    double time_serviced;
    double time_done;
    int id;

    public Entity(double time, int id) {
        this.time_generated = time;
        this.id = id;
    }

}
