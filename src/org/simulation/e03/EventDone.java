package org.simulation.e03;

import java.util.List;

public class EventDone extends Event {

    public EventDone(double time) {
        this.time = time;
    }

    public void execute(Simulator sim) {
        List<Customer> list = sim.getCustomer_queue().get(0);
        Customer c = list.get(0);
        list.remove(0);
        c.time_done = this.time;
        System.out.println("Customer_" + c.getId() + " left with " + c.getNum_scoops()
                + " scoops at time " + Event.printTime(c.time_done));
        if (list.size() > 0) {
            EventServing serving = new EventServing(time);
            sim.insert(serving);
        }
    }

}
