package org.simulation.w03;

public class Done extends Event {

    public Done(double time) {
        this.time = time;
    }

    public void execute(Simulator sim) {
        Entity e = sim.queue_list.get(0);
        sim.queue_list.remove(0);
        e.time_done = time;
        System.out.println("Entity-" + e.id + " is done at time " + e.time_done);

        //
        if (sim.queue_list.size() > 0) {
            Serving s = new Serving(time);
            sim.insert(s);
        }
    }

}
