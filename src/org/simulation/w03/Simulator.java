package org.simulation.w03;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    public double time;
    public double end_time;

    List<Entity> entity_list = new ArrayList<>(0);
    List<Entity> queue_list = new ArrayList<>(0);

    EventList events;

    public double now() {
        return time;
    }

    public void insert(Event e) {
        events.insert(e);
    }

    public Event cancel(Event e) {
        return e;
    }

    public void doAllEvents() {
        Event e = new Event();
        while (events.size() > 0 && time < end_time) {
            e = events.removeFirst();
            time = e.time;
            e.execute(this);
        }
    }

}
