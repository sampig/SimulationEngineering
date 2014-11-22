package org.simulation.e03;

import java.util.Iterator;
import java.util.List;

public class EventDone extends Event {

    public EventDone(double time) {
        this.time = time;
    }

    public void execute(Simulator sim) {

        // get the customer who is in the server queue and earliest to be done.
        List<Customer> server_list = sim.getServer_queue();
        int i = 0;
        Customer c = server_list.get(0); // get the first customer
        double time_temp = c.time_done;
        for (i = 1; i < sim.num_servers && i < server_list.size(); i++) {
            // if other customers get cream earlier, remove him/her.
            if (time_temp > server_list.get(i).time_done) {
                c = server_list.get(i);
                time_temp = c.time_done;
            }
        }
        server_list.remove(c); // !!!it does not work. I don't know why yet.
        // use another way to remove the customer from the server queue.
        Iterator<Customer> iter = server_list.iterator();
        while (iter.hasNext()) {
            Customer cus = iter.next();
            if (cus.getId() == c.getId()) {
                iter.remove();
            }
        }
        c.time_done = this.time;
        System.out.println("Customer_" + c.getId() + " left with " + c.getNum_scoops()
                + " scoops at time " + Event.printTime(c.time_done));

        // if there are customers in the waiting queue
        // and at least one of the server(s) is free,
        // there would be a customer to be served.
        if (sim.getCustomer_queue().get(0).size() > 0
                && sim.getServer_queue().size() < sim.num_servers) {
            EventServing serving = new EventServing(time);
            sim.insert(serving);
        }

    }

}
