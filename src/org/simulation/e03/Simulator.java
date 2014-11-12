package org.simulation.e03;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    private double time; // minutes 0h-24h (0m-1440m)
    private double start_time = 0;
    private double end_time = 1440;
    private List<Customer> customer_list = new ArrayList<>(0);
    // there could be multi queues.
    private List<List<Customer>> customer_queue = new ArrayList<>(0);

    protected EventList events = new EventList();

    public Simulator() {
        this.time = start_time;
    }

    public Simulator(double time) {
        this.time = time;
    }

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
        for (; events.getSize() > 0 || this.time < end_time;) {
            e = events.remove();
            time = e.getTime();
            e.execute(this);
            // if it is the end of the simulation,
            // there won't be a new generation.
            if (this.time > end_time) {
                for (int i = 0; i < events.getSize();) {
                    Event ev = events.getList().get(i);
                    if (EventGenerating.class.isInstance(ev)) {
                        events.getList().remove(i);
                    } else {
                        i++;
                    }
                }
            }
        }

    }

    public List<Customer> getCustomer_list() {
        return customer_list;
    }

    public List<List<Customer>> getCustomer_queue() {
        return customer_queue;
    }

    public EventList getEvents() {
        return events;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getStart_time() {
        return start_time;
    }

    public void setStart_time(double start_time) {
        this.start_time = start_time;
    }

    public double getEnd_time() {
        return end_time;
    }

    public void setEnd_time(double end_time) {
        this.end_time = end_time;
    }

}
