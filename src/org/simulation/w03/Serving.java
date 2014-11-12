package org.simulation.w03;

public class Serving extends Event {

    public Serving(double time) {
        this.time = time;
    }

    public void execute(Simulator sim) {
        Entity e = sim.queue_list.get(0);
        e.time_serviced = time;
        System.out.println("Entity-" + e.id + " is being served at time " + e.time_serviced);

        //
        double next_event_time = (int) (Math.random() * 15) + 10;
        time += next_event_time;
        Done d = new Done(time);
        sim.insert(d);
    }
}
