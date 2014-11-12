package org.simulation.w03;

public class Generating extends Event {

    public int num;

    public Generating(double time) {
        num = 0;
        this.time = time;
    }

    public void execute(Simulator sim) {
        num++;
        Entity e = new Entity(time, num);
        sim.entity_list.add(e);
        System.out.println("Entity-" + e.id + " is generated at time " + e.time_generated);

        //
        sim.queue_list.add(e);
        if (sim.queue_list.size() == 1) {
            Serving s = new Serving(time);
            sim.insert(s);
        }

        //
        double next_event_time = (int) (Math.random() * 15) + 10;
        time += next_event_time;
        sim.insert(this);
    }

}
